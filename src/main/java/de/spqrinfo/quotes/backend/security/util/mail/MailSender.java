package de.spqrinfo.quotes.backend.security.util.mail;

import org.springframework.mail.SimpleMailMessage;

public interface MailSender {
    void send(SimpleMailMessage message);
}
