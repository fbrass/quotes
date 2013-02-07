package org.antbear.tododont.backend.service;

import org.antbear.tododont.backend.dao.UserDao;
import org.antbear.tododont.util.MailSender;
import org.antbear.tododont.web.controller.registration.UserRegistration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.xml.bind.DatatypeConverter;
import java.security.SecureRandom;

import static org.antbear.tododont.util.InvariantException.notNull;
import static org.antbear.tododont.util.InvariantException.notNullOrEmpty;

@Service
public class UserRegistrationService {

    private static final Logger log = LoggerFactory.getLogger(UserRegistrationService.class);

    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserDao userDao;

    @Autowired
    private MailSender mailSender;

    @Autowired
    private SimpleMailMessage mailTemplateMessage;

    private final SecureRandom secureRandom = new SecureRandom();

    @Autowired
    public void setPasswordEncoder(final PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public String register(final UserRegistration userRegistration) throws UserRegistrationException {
        log.info("Registration attempt for {}", userRegistration);
        notNull(userRegistration, "UserRegistration");
        notNullOrEmpty(userRegistration.getEmail(), "UserRegistration.email");
        notNullOrEmpty(userRegistration.getPassword(), "UserRegistration.password");

        if (this.userDao.exists(userRegistration.getEmail())) {
            throw new UserRegistrationException("User is already registered", userRegistration);
        }


        final String registrationToken = createRegistrationToken(userRegistration);
        this.userDao.createInactiveUser(userRegistration.getEmail(),
                this.passwordEncoder.encode(userRegistration.getPassword()),
                registrationToken);

        // TODO make URL configurable
        final String activationUrl = "http://localhost:8080/activate-user?" + registrationToken;

        final SimpleMailMessage msg = new SimpleMailMessage(this.mailTemplateMessage);
        msg.setTo(userRegistration.getEmail());
        // TODO make text a customizable template
        msg.setText(
                "Dear " + userRegistration.getEmail() + ",\n"
                + "thank you for registering. Please activate your login by following this link,\n"
                + "or by copying to your browsers address bar.\n"
                + "URL: " + activationUrl + "\n"
                + "Cheers"
        );

        try {
            log.debug("Sending registration email to " + userRegistration.getEmail());
            this.mailSender.send(msg);
        } catch (MailException me) {
            log.error("Failed sending activation mail", me);
            throw new UserRegistrationException("Failed sending verifcation mail", me, userRegistration);
        }

        log.info("User successfully registered {}", userRegistration);

        // TODO send email with initial-registration-random-value link which is somehow processed by a servlet
        //      and verified by comparing it according to the DB
        // TODO If user has been successfully verified, set account flag active = 1 and initial-registration-random-value = NULL
        // TODO maintain a counter for user account as long as initial-registration-random-value is not null
        // TODO if count > 3 purge account

        return registrationToken;
    }

    /**
     * Creates a user registration token to be stored and send via mail.
     */
    private String createRegistrationToken(final UserRegistration userRegistration) {
        final byte[] randomBytes = new byte[128];
        this.secureRandom.nextBytes(randomBytes);
        final String randomBase64 = DatatypeConverter.printBase64Binary(randomBytes);
        // Truncate to 128 characters
        return randomBase64.substring(0, 127);
    }
}
