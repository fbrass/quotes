package de.spqrinfo.quotes.backend.security.service;

import org.springframework.mail.SimpleMailMessage;

public interface SecurityMail {

    String getEmail();
    String getUrl();
    SimpleMailMessage getMailMessage();
}
