package org.antbear.tododont.backend.service.userregistration;

import org.springframework.mail.MailSendException;

/**
 * A UserRegistrationMailSender that fails with an exception.
 */
public class UserRegistrationMailSenderFailTestSupport implements UserRegistrationMailSender {

    @Override
    public void sendRegistration(final UserRegistrationMail userRegistrationMail) {
        throw new MailSendException("Failing to send user registration mail " + userRegistrationMail);
    }
}
