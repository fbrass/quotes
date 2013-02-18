package org.antbear.tododont.backend.security.util.mail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;

public class MailSenderImpl implements MailSender {

    @Autowired
    private org.springframework.mail.MailSender delegate;

    @Override
    public void send(final SimpleMailMessage message) {
        this.delegate.send(message);
    }

    public org.springframework.mail.MailSender getDelegate() {
        return delegate;
    }

    public void setDelegate(final org.springframework.mail.MailSender delegate) {
        this.delegate = delegate;
    }
}
