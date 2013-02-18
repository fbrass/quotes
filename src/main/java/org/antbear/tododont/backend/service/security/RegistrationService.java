package org.antbear.tododont.backend.service.security;

import org.antbear.tododont.backend.dao.RegistrationMailScheduleDao;
import org.antbear.tododont.backend.dao.UserDao;
import org.antbear.tododont.web.beans.security.Registration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponents;

import static org.antbear.tododont.util.InvariantException.notNull;
import static org.antbear.tododont.util.InvariantException.notNullOrEmpty;

@Service
public class RegistrationService extends SecurityTokenServiceBase {

    private static final Logger log = LoggerFactory.getLogger(RegistrationService.class);

    public static final String REGISTRATION_USER_ALREADY_REGISTERED = "registration.userAlreadyRegistered";
    public static final String REGISTRATION_ACTIVATION_USER_NOT_REGISTERED = "registration.activation.userNotRegistered";
    public static final String REGISTRATION_ACTIVATION_USER_ALREADY_ACTIVATED = "registration.activation.userAlreadyActivated";
    public static final String REGISTRATION_ACTIVATION_INVALID_TOKEN = "registration.activation.invalidToken";

    @Autowired
    private UserDao userDao;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RegistrationMail registrationMail;

    private SecurityMailSender mailSender;

    @Autowired
    private RegistrationMailScheduleDao mailScheduleDao;

    @Autowired
    protected void setSecurityMailSender(final SecurityMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public String register(final Registration registration, final UriComponents userActivationUriComponents)
            throws RegistrationException {
        log.info("Registration attempt for {} and base URI {}", registration, userActivationUriComponents);
        notNull(registration, "Registration");
        notNullOrEmpty(registration.getEmail(), "Registration.email");
        notNullOrEmpty(registration.getPassword(), "Registration.password");

        if (this.userDao.exists(registration.getEmail())) {
            throw new RegistrationException(REGISTRATION_USER_ALREADY_REGISTERED,
                    registration);
        }

        final String registrationToken = createSecurityToken();
        this.userDao.createInactiveUser(registration.getEmail(),
                this.passwordEncoder.encode(registration.getPassword()),
                registrationToken);

        final String activationUrl = userActivationUriComponents.expand(registration.getEmail(),
                registrationToken).encode().toUriString();

        this.registrationMail.setEmail(registration.getEmail());
        this.registrationMail.setUrl(activationUrl);

        try {
            log.debug("Sending registration mail to " + registration.getEmail());
            this.mailSender.send(this.registrationMail);
            log.info("User successfully registered {}", registration);
        } catch (MailException mex) {
            log.warn("Failed sending registration mail, scheduling for deferred sending", mex);
            this.mailScheduleDao.createSchedule(registration.getEmail(), activationUrl);
        }

        return registrationToken;
    }

    public void activate(final String email, final String activationToken) throws RegistrationActivationException {
        log.info("Activation attempt for {} with token {}", email, activationToken);
        notNullOrEmpty(email, "email");
        notNullOrEmpty(activationToken, "activationToken");

        if (!this.userDao.exists(email)) {
            throw new RegistrationActivationException(REGISTRATION_ACTIVATION_USER_NOT_REGISTERED,
                    email, activationToken);
        }

        if (this.userDao.getActiveStateByUser(email)) {
            throw new RegistrationActivationException(REGISTRATION_ACTIVATION_USER_ALREADY_ACTIVATED,
                    email, activationToken);
        }

        final String savedRegistrationToken = this.userDao.findRegistrationTokenByUser(email);
        if (!savedRegistrationToken.equals(activationToken)) {
            throw new RegistrationActivationException(REGISTRATION_ACTIVATION_INVALID_TOKEN,
                    email, activationToken);
        } else {
            log.info("User {} will be activated", email);
            this.userDao.activateUser(email);
        }
    }
}
