package org.antbear.tododont.backend.service.security;

import org.antbear.tododont.backend.dao.PasswordResetMailScheduleDao;
import org.antbear.tododont.backend.dao.UserDao;
import org.antbear.tododont.web.beans.security.PasswordReset;
import org.antbear.tododont.web.beans.security.PasswordResetAttempt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponents;

import javax.validation.Valid;

import static org.antbear.tododont.util.InvariantException.notNull;
import static org.antbear.tododont.util.InvariantException.notNullOrEmpty;

@Service
public class PasswordResetService extends SecurityTokenServiceBase {

    private static final Logger log = LoggerFactory.getLogger(PasswordResetService.class);

    public static final String PASSWORD_RESET_USER_UNKNOWN = "passwordReset.userUnknown";

    @Autowired
    private UserDao userDao;

    @Autowired
    private PasswordResetMail passwordResetMail;

    private SecurityMailSender mailSender;

    @Autowired
    private PasswordResetMailScheduleDao mailScheduleDao;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    protected void setSecurityMailSender(final SecurityMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void validateInitialRequest(final PasswordResetAttempt passwordReset) throws PasswordResetException {
        log.debug("Validating initial password reset request for {}", passwordReset);

        notNull(passwordReset, "passwordReset");
        notNullOrEmpty(passwordReset.getEmail(), "passwordReset.email");

        if (!this.userDao.exists(passwordReset.getEmail())) {
            throw new PasswordResetException(PASSWORD_RESET_USER_UNKNOWN, passwordReset);
        }

        if (!this.userDao.getActiveStateByUser(passwordReset.getEmail())) {
            throw new PasswordResetException(PASSWORD_RESET_USER_UNKNOWN, passwordReset);
        }
    }

    public String passwordResetAttempt(final PasswordResetAttempt passwordResetAttempt,
                                       final UriComponents passwordResetUriComponents) throws PasswordResetException {
        log.info("Password reset for {} and base URI {}", passwordResetAttempt, passwordResetUriComponents);

        notNull(passwordResetAttempt, "passwordResetAttempt");
        notNullOrEmpty(passwordResetAttempt.getEmail(), "passwordResetAttempt.email");

        if (!this.userDao.exists(passwordResetAttempt.getEmail())) {
            // Dont show any details for security/obscurity
            throw new PasswordResetException(PASSWORD_RESET_USER_UNKNOWN, passwordResetAttempt);
        }

        if (!this.userDao.getActiveStateByUser(passwordResetAttempt.getEmail())) {
            // Dont show any details for security/obscurity
            throw new PasswordResetException(PASSWORD_RESET_USER_UNKNOWN, passwordResetAttempt);
        }

        final String changePasswordToken = createSecurityToken();
        this.userDao.saveChangePasswordToken(passwordResetAttempt.getEmail(), changePasswordToken);

        final String changePasswordUrl = passwordResetUriComponents.expand(passwordResetAttempt.getEmail(),
                changePasswordToken).encode().toUriString();

        this.passwordResetMail.setEmail(passwordResetAttempt.getEmail());
        this.passwordResetMail.setUrl(changePasswordUrl);

        try {
            log.debug("Sending passwort reset mail to {}", passwordResetAttempt.getEmail());
            this.mailSender.send(this.passwordResetMail);
        } catch (MailException mex) {
            log.warn("Failed sending password reset mail, scheduling for deferred sending", mex);
            this.mailScheduleDao.createSchedule(passwordResetAttempt.getEmail(), changePasswordUrl);
        }

        return changePasswordToken;
    }

    public void validateChangeAttempt(final String email, final String passwordResetToken) throws PasswordResetException {
        log.info("Validating password change attempt for {} with token {}", email, passwordResetToken);
        notNullOrEmpty(email, "email");
        notNullOrEmpty(passwordResetToken, "passwordResetToken");

        if (!this.userDao.exists(email)) {
            // Dont show any details for security/obscurity
            throw new PasswordResetException(PASSWORD_RESET_USER_UNKNOWN, null);
        }

        if (!this.userDao.getActiveStateByUser(email)) {
            // Dont show any details for security/obscurity
            throw new PasswordResetException(PASSWORD_RESET_USER_UNKNOWN, null);
        }

        final String requiredChangePasswordToken = this.userDao.getChangePasswordToken(email);
        if (!requiredChangePasswordToken.equals(passwordResetToken)) {
            // Dont show any details for security/obscurity
            throw new PasswordResetException(PASSWORD_RESET_USER_UNKNOWN, null);
        }
    }

    public void passwordChange(@Valid final PasswordReset passwordReset) throws PasswordResetException {
        log.info("Password change requested {}", passwordReset);
        notNull(passwordReset, "passwordReset");
        notNullOrEmpty(passwordReset.getEmail(), "passwordRequest.email");
        notNullOrEmpty(passwordReset.getPassword(), "passwordRequest.password");
        notNullOrEmpty(passwordReset.getPasswordResetToken(), "passwordRequest.passwordResetToken");

        this.validateChangeAttempt(passwordReset.getEmail(), passwordReset.getPasswordResetToken());

        log.info("Updating password for {}", passwordReset.getEmail());
        this.userDao.updatePassword(passwordReset.getEmail(),
                this.passwordEncoder.encode(passwordReset.getPassword()));
    }
}
