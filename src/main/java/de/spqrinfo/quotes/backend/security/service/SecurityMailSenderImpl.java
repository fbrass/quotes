package de.spqrinfo.quotes.backend.security.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;

public class SecurityMailSenderImpl implements SecurityMailSender {

    private static final Logger log = LoggerFactory.getLogger(SecurityMailSenderImpl.class);

    @Autowired
    private org.springframework.mail.MailSender mailSender;

    @Override
    public void send(final SecurityMail securityMail) {
        log.info("Sending security mail via {} for mail {}", this.mailSender, securityMail);
        final SimpleMailMessage mailMessage = securityMail.getMailMessage();
        this.mailSender.send(mailMessage);
    }
}
