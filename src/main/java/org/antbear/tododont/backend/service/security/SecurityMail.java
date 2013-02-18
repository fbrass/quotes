package org.antbear.tododont.backend.service.security;

import org.springframework.mail.SimpleMailMessage;

public interface SecurityMail {

    String getEmail();
    String getUrl();
    SimpleMailMessage getMailMessage();
}
