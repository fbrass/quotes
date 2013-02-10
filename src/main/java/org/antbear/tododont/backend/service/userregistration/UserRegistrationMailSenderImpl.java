package org.antbear.tododont.backend.service.userregistration;

import org.antbear.tododont.util.MailSender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;

public class UserRegistrationMailSenderImpl implements UserRegistrationMailSender {

    private static final Logger log = LoggerFactory.getLogger(UserRegistrationMailSenderImpl.class);

    @Autowired
    private MailSender mailSender;

    @Override
    public void sendRegistration(final UserRegistrationMail userRegistrationMail) {
        log.info("Sending registration email via {} for mail {}", this.mailSender, userRegistrationMail);
        final SimpleMailMessage mailMessage = userRegistrationMail.getMailMessage();
        mailSender.send(mailMessage);
    }
}
