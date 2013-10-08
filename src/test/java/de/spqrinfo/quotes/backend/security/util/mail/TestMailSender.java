package de.spqrinfo.quotes.backend.security.util.mail;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.SimpleMailMessage;

public class TestMailSender implements MailSender {

    private static final Logger log = LoggerFactory.getLogger(TestMailSender.class);

    private SimpleMailMessage message;

    @Override
    public void send(final SimpleMailMessage message) {
        log.debug("Sending mail message {}", message);
        this.message = message;
    }

    public SimpleMailMessage getMessage() {
        return message;
    }
}
