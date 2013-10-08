package de.spqrinfo.quotes.backend.security.service;

import de.spqrinfo.quotes.backend.security.dao.RegistrationMailScheduleDao;
import de.spqrinfo.quotes.backend.security.entity.CustomUserDetails;
import de.spqrinfo.quotes.backend.security.beans.Registration;
import de.spqrinfo.quotes.backend.security.dao.CustomUserDetailsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.dao.SaltSource;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.util.UriComponents;

import static de.spqrinfo.quotes.backend.security.util.InvariantException.notNull;
import static de.spqrinfo.quotes.backend.security.util.InvariantException.notNullOrEmpty;

@PreAuthorize("permitAll")
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

    @Autowired
    private SecurityMailSender securityMailSender;

    @Autowired
    private RegistrationMailScheduleDao mailScheduleDao;

    @Transactional
    public String register(final Registration registration, final UriComponents userActivationUriComponents)
            throws RegistrationException {
        log.info("Registration attempt for {} and activation URI components {}", registration, userActivationUriComponents);
        notNull(registration, "Registration");
        notNullOrEmpty(registration.getEmail(), "Registration.email");
        notNullOrEmpty(registration.getPassword(), "Registration.password");

        if (this.userDetailsService.isExistingUser(registration.getEmail().toLowerCase())) {
            throw new RegistrationException(REGISTRATION_USER_ALREADY_REGISTERED, registration);
        }

        final CustomUserDetails user = new CustomUserDetails(registration.getEmail().toLowerCase(),
                createSecurityToken());

        user.setPassword(this.passwordEncoder.encodePassword(registration.getPassword(),
                this.saltSource.getSalt(user)));

        this.userDetailsService.createUser(user);

        final String activationUrl = userActivationUriComponents.expand(user.getRegistrationToken()).encode().toUriString();

        this.registrationMail.setEmail(registration.getEmail().toLowerCase());
        this.registrationMail.setUrl(activationUrl);

        try {
            log.debug("Sending registration mail to " + registration.getEmail().toLowerCase());
            this.securityMailSender.send(this.registrationMail);
            log.info("User successfully registered {}", registration);
        } catch (MailException mex) {
            log.warn("Failed sending registration mail, scheduling for deferred sending", mex);
            this.mailScheduleDao.createSchedule(registration.getEmail().toLowerCase(), activationUrl);
        }

        return user.getRegistrationToken();
    }

    @Transactional
    public void activate(final String activationToken) throws RegistrationActivationException {
        log.info("Activation attempt with token {}", activationToken);
        notNullOrEmpty(activationToken, "activationToken");

        final CustomUserDetails user = this.userDetailsService.loadUserByRegistrationToken(activationToken);
        if (null == user) {
            throw new RegistrationActivationException(REGISTRATION_ACTIVATION_USER_NOT_REGISTERED, activationToken);
        }

        if (user.isEnabled()) {
            throw new RegistrationActivationException(REGISTRATION_ACTIVATION_USER_ALREADY_ACTIVATED, activationToken);
        }

        if (user.getRegistrationToken().equals(activationToken)) {
            log.info("User {} will be activated", user.getUsername());
            this.userDetailsService.enableUser(user.getUsername());
        } else {
            throw new RegistrationActivationException(REGISTRATION_ACTIVATION_INVALID_TOKEN, activationToken);
        }
    }
}
