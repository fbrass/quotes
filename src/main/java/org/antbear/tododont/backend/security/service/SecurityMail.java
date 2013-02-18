package org.antbear.tododont.backend.security.service;

import org.springframework.mail.SimpleMailMessage;

public interface SecurityMail {

    String getEmail();
    String getUrl();
    SimpleMailMessage getMailMessage();
}
