package org.antbear.tododont.backend.service.userregistration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserRegistrationMailSenderTestSupport implements UserRegistrationMailSender {

    private static final Logger log = LoggerFactory.getLogger(UserRegistrationMailSenderTestSupport.class);

    private UserRegistrationMail userRegistrationMail;

    @Override
    public void sendRegistration(final UserRegistrationMail userRegistrationMail) {
        log.debug("sendRegistration " + userRegistrationMail);
        this.userRegistrationMail = userRegistrationMail;
    }

    public UserRegistrationMail getUserRegistrationMail() {
        return userRegistrationMail;
    }
}
