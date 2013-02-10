package org.antbear.tododont.backend.service.userregistration;

import org.antbear.tododont.backend.dao.UserDao;
import org.antbear.tododont.web.beans.UserRegistration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponents;

import javax.xml.bind.DatatypeConverter;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
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
    private UserRegistrationMail concreteMail;

    @Autowired
    private UserRegistrationMailSender mailSender;

    private final SecureRandom secureRandom = new SecureRandom();

    @Autowired
    public void setPasswordEncoder(final PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public String register(final UserRegistration userRegistration, final UriComponents userActivationUriComponents)
            throws UserRegistrationException {
        log.info("Registration attempt for {} and base URI {}", userRegistration, userActivationUriComponents);
        notNull(userRegistration, "UserRegistration");
        notNullOrEmpty(userRegistration.getEmail(), "UserRegistration.email");
        notNullOrEmpty(userRegistration.getPassword(), "UserRegistration.password");

        if (this.userDao.exists(userRegistration.getEmail())) {
            throw new UserRegistrationException("userRegistration.registration.userAlreadyRegistered",
                    userRegistration);
        }

        final String registrationToken = createRegistrationToken(userRegistration);
        this.userDao.createInactiveUser(userRegistration.getEmail(),
                this.passwordEncoder.encode(userRegistration.getPassword()),
                registrationToken);

        final String activationUrl = userActivationUriComponents.expand(userRegistration.getEmail(),
                registrationToken).encode().toUriString();

        this.concreteMail.setEmail(userRegistration.getEmail());
        this.concreteMail.setActivationUrl(activationUrl);

        // TODO we need to rollback the DB insert if sending the email fails
        // TODO check with invalid email address, where the MTA bounces the message: it seems no error is thrown.
        try {
            log.debug("Sending registration email to " + userRegistration.getEmail());
            this.mailSender.sendRegistration(this.concreteMail);
        } catch (MailException mex) {
            log.error("Failed sending activation mail", mex);
            throw new UserRegistrationException(mex, "userRegistration.registration.failedSendingVerificationMail",
                    userRegistration);
        }

        log.info("User successfully registered {}", userRegistration);
        return registrationToken;
    }

    /**
     * Creates a user registration token to be stored in DB and send via mail.
     */
    private String createRegistrationToken(final UserRegistration userRegistration) {
        final byte[] randomBytes = new byte[128];
        this.secureRandom.nextBytes(randomBytes);
        final String randomBase64 = DatatypeConverter.printBase64Binary(randomBytes);
        final String urlEncoded = urlEncode(randomBase64);
        // Truncate to 128 characters
        return urlEncoded.substring(0, 127);
    }

    private static String urlEncode(final String s) {
        try {
            return URLEncoder.encode(s, "utf-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    public void activate(final String email, final String activationToken) throws UserActivationException {
        log.info("Activation attempt for {} with token {}", email, activationToken);
        notNullOrEmpty(email, "email");
        notNullOrEmpty(activationToken, "activationToken");

        if (!this.userDao.exists(email)) {
            throw new UserActivationException("userRegistration.activation.userNotRegistered",
                    email, activationToken);
        }

        if (this.userDao.getActiveStateByUser(email)) {
            throw new UserActivationException("userRegistration.activation.userAlreadyActivated",
                    email, activationToken);
        }

        final String savedRegistrationToken = this.userDao.findRegistrationTokenByUser(email);
        if (!savedRegistrationToken.equals(activationToken)) {
            throw new UserActivationException("userRegistration.activation.invalidToken",
                    email, activationToken);
        } else {
            log.info("User {} will be activated", email);
            this.userDao.activateUser(email);
        }
    }
}
