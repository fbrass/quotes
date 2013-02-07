package org.antbear.tododont.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.SimpleMailMessage;

public class TestMailSender implements MailSender {

    private static final Logger log = LoggerFactory.getLogger(TestMailSender.class);

    @Override
    public void send(final SimpleMailMessage message) {
        log.debug("Sending mail message {}", message);
    }
}
