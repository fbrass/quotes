package org.antbear.tododont.backend.service.userregistration;

import org.antbear.tododont.util.MailSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;


public class UserRegistrationMailSenderImpl implements UserRegistrationMailSender {

    @Autowired
    private MailSender mailSender;

    @Autowired
    private SimpleMailMessage mailTemplateMessage;

    @Override
    public void sendRegistration(final UserRegistrationMail userRegistrationMail) {
        final SimpleMailMessage msg = new SimpleMailMessage(this.mailTemplateMessage);
        throw new RuntimeException("not implemented"); // TODO missing logic to send mail
    }
}
