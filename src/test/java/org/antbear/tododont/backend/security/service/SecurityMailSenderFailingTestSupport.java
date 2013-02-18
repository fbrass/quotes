package org.antbear.tododont.backend.security.service;

import org.springframework.mail.MailSendException;

/**
 * A MailSender that always fails with a MailSendexception.
 */
public class SecurityMailSenderFailingTestSupport implements SecurityMailSender {

    @Override
    public void send(final SecurityMail securityMail) {
        throw new MailSendException("Failing to send security mail " + securityMail);
    }
}
