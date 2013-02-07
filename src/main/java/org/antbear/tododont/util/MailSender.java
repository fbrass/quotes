package org.antbear.tododont.util;

import org.springframework.mail.SimpleMailMessage;

public interface MailSender {
    void send(SimpleMailMessage message);
}
