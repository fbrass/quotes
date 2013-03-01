package org.antbear.tododont.backend.security.service;

import org.antbear.tododont.backend.security.dao.CustomUserDetailsService;
import org.antbear.tododont.backend.security.dao.PasswordResetMailScheduleDao;
import org.antbear.tododont.backend.security.entity.CustomUserDetails;
import org.antbear.tododont.backend.security.beans.PasswordReset;
import org.antbear.tododont.backend.security.beans.PasswordResetAttempt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.security.authentication.dao.SaltSource;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.util.UriComponents;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;

import static org.antbear.tododont.backend.security.util.InvariantException.notNull;
import static org.antbear.tododont.backend.security.util.InvariantException.notNullOrEmpty;

@RolesAllowed("IS_AUTHENTICATED_ANONYMOUSLY")
@Service
public class PasswordResetService extends SecurityTokenServiceBase {

    private static final Logger log = LoggerFactory.getLogger(PasswordResetService.class);

    public static final String PASSWORD_RESET_USER_UNKNOWN = "passwordReset.userUnknown";

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Autowired
    private PasswordResetMail passwordResetMail;

    private SecurityMailSender mailSender;

    @Autowired
    private PasswordResetMailScheduleDao mailScheduleDao;

    @Autowired
    private SaltSource saltSource;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    public void setSecurityMailSender(final SecurityMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Transactional(readOnly = true)
    public CustomUserDetails validateInitialRequest(final PasswordResetAttempt passwordReset) throws PasswordResetException {
        log.debug("Validating initial password reset request for {}", passwordReset);

        notNull(passwordReset, "passwordReset");
        notNullOrEmpty(passwordReset.getEmail(), "passwordReset.email");

        if (!this.userDetailsService.isExistingUser(passwordReset.getEmail())) {
            throw new PasswordResetException(PASSWORD_RESET_USER_UNKNOWN, passwordReset);
        }

        final CustomUserDetails user = (CustomUserDetails) this.userDetailsService.loadUserByUsername(passwordReset.getEmail());
        if (!user.isEnabled()) {
            throw new PasswordResetException(PASSWORD_RESET_USER_UNKNOWN, passwordReset);
        }

        return user;
    }

    @Transactional
    public String passwordResetAttempt(final PasswordResetAttempt passwordResetAttempt,
                                       final UriComponents passwordResetUriComponents) throws PasswordResetException {
        log.info("Password reset for {} and base URI {}", passwordResetAttempt, passwordResetUriComponents);

        notNull(passwordResetAttempt, "passwordResetAttempt");
        notNullOrEmpty(passwordResetAttempt.getEmail(), "passwordResetAttempt.email");

        if (!this.userDetailsService.isExistingUser(passwordResetAttempt.getEmail())) {
            // Don't show any details for security/obscurity
            throw new PasswordResetException(PASSWORD_RESET_USER_UNKNOWN, passwordResetAttempt);
        }

        final CustomUserDetails user = (CustomUserDetails) this.userDetailsService.loadUserByUsername(passwordResetAttempt.getEmail());
        if (!user.isEnabled()) {
            // Don't show any details for security/obscurity
            throw new PasswordResetException(PASSWORD_RESET_USER_UNKNOWN, passwordResetAttempt);
        }

        final String passwordResetToken = createSecurityToken();
        user.setPasswordResetToken(passwordResetToken);
        this.userDetailsService.updatePasswordResetToken(passwordResetAttempt.getEmail(), passwordResetToken);

        final String passwordResetUrl = passwordResetUriComponents.expand(passwordResetAttempt.getEmail(),
                passwordResetToken).encode().toUriString();

        this.passwordResetMail.setEmail(passwordResetAttempt.getEmail());
        this.passwordResetMail.setUrl(passwordResetUrl);

        try {
            log.debug("Sending password reset mail to {}", passwordResetAttempt.getEmail());
            this.mailSender.send(this.passwordResetMail);
        } catch (MailException mex) {
            log.warn("Failed sending password reset mail, scheduling for deferred sending", mex);
            this.mailScheduleDao.createSchedule(passwordResetAttempt.getEmail(), passwordResetUrl);
        }

        return passwordResetToken;
    }

    @Transactional(readOnly = true )
    public CustomUserDetails validateChangeAttempt(final String email, final String passwordResetToken) throws PasswordResetException {
        log.info("Validating password change attempt for {} with token {}", email, passwordResetToken);
        notNullOrEmpty(email, "email");
        notNullOrEmpty(passwordResetToken, "passwordResetToken");

        if (!this.userDetailsService.isExistingUser(email)) {
            // Don't show any details for security/obscurity
            throw new PasswordResetException(PASSWORD_RESET_USER_UNKNOWN, null);
        }

        final CustomUserDetails user = (CustomUserDetails) this.userDetailsService.loadUserByUsername(email);
        if (!user.isEnabled()) {
            // Don't show any details for security/obscurity
            throw new PasswordResetException(PASSWORD_RESET_USER_UNKNOWN, null);
        }

        if (!user.getPasswordResetToken().equals(passwordResetToken)) {
            // Don't show any details for security/obscurity
            throw new PasswordResetException(PASSWORD_RESET_USER_UNKNOWN, null);
        }

        return user;
    }

    @Transactional
    public void passwordChange(@Valid final PasswordReset passwordReset) throws PasswordResetException {
        log.info("Password change requested {}", passwordReset);
        notNull(passwordReset, "passwordReset");
        notNullOrEmpty(passwordReset.getEmail(), "passwordRequest.email");
        notNullOrEmpty(passwordReset.getPassword(), "passwordRequest.password");
        notNullOrEmpty(passwordReset.getPasswordResetToken(), "passwordRequest.passwordResetToken");

        final CustomUserDetails user = this.validateChangeAttempt(passwordReset.getEmail(), passwordReset.getPasswordResetToken());

        log.info("Updating password for {}", passwordReset.getEmail());
        user.setPasswordResetToken(null);
        user.setPassword(this.passwordEncoder.encodePassword(passwordReset.getPassword(),
                this.saltSource.getSalt(user)));

        this.userDetailsService.updatePassword(user);
    }
}
