package org.antbear.tododont.backend.security.service;

import org.antbear.tododont.backend.security.dao.CustomUserDetailsService;
import org.antbear.tododont.backend.security.dao.RegistrationMailScheduleDao;
import org.antbear.tododont.backend.security.entity.CustomUserDetails;
import org.antbear.tododont.web.security.beans.Registration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.security.authentication.dao.SaltSource;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponents;

import static org.antbear.tododont.backend.security.util.InvariantException.notNull;
import static org.antbear.tododont.backend.security.util.InvariantException.notNullOrEmpty;

@Service
public class RegistrationService extends SecurityTokenServiceBase {

    private static final Logger log = LoggerFactory.getLogger(RegistrationService.class);

    public static final String REGISTRATION_USER_ALREADY_REGISTERED = "registration.userAlreadyRegistered";
    public static final String REGISTRATION_ACTIVATION_USER_NOT_REGISTERED = "registration.activation.userNotRegistered";
    public static final String REGISTRATION_ACTIVATION_USER_ALREADY_ACTIVATED = "registration.activation.userAlreadyActivated";
    public static final String REGISTRATION_ACTIVATION_INVALID_TOKEN = "registration.activation.invalidToken";

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Autowired
    private SaltSource saltSource;

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
        log.info("Registration attempt for {} and activation URI components {}", registration, userActivationUriComponents);
        notNull(registration, "Registration");
        notNullOrEmpty(registration.getEmail(), "Registration.email");
        notNullOrEmpty(registration.getPassword(), "Registration.password");

        if (this.userDetailsService.isExistingUser(registration.getEmail())) {
            throw new RegistrationException(REGISTRATION_USER_ALREADY_REGISTERED, registration);
        }

        final CustomUserDetails user = new CustomUserDetails(registration.getEmail(),
                createSecurityToken());

        user.setPassword(this.passwordEncoder.encodePassword(registration.getPassword(),
                this.saltSource.getSalt(user)));

        this.userDetailsService.createUser(user);

        final String activationUrl = userActivationUriComponents.expand(user.getRegistrationToken()).encode().toUriString();

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

        return user.getRegistrationToken();
    }

    public void activate(final String activationToken) throws RegistrationActivationException {
        log.info("Activation attempt with token {}", activationToken);
        notNullOrEmpty(activationToken, "activationToken");

        final CustomUserDetails user = (CustomUserDetails) this.userDetailsService.loadUserByRegistrationToken(activationToken);
        if (null == user) {
            throw new RegistrationActivationException(REGISTRATION_ACTIVATION_USER_NOT_REGISTERED, activationToken);
        }

        if (user.isEnabled()) {
            throw new RegistrationActivationException(REGISTRATION_ACTIVATION_USER_ALREADY_ACTIVATED, activationToken);
        }

        if (!user.getRegistrationToken().equals(activationToken)) {
            throw new RegistrationActivationException(REGISTRATION_ACTIVATION_INVALID_TOKEN, activationToken);
        } else {
            log.info("User {} will be activated", user.getUsername());
            this.userDetailsService.enableUser(user.getUsername());
        }
    }
}
