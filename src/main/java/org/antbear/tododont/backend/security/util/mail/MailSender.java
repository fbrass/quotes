package org.antbear.tododont.backend.security.util.mail;

import org.springframework.mail.SimpleMailMessage;

public interface MailSender {
    void send(SimpleMailMessage message);
}
