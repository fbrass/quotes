package org.antbear.tododont.backend.service.userregistration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component("prototype")
public class UserRegistrationMail {

    @Autowired
    private SimpleMailMessage mailTemplateMessage;

    @Autowired
    private MessageSource messageSource;

    private String email;
    private String activationUrl;

    public UserRegistrationMail() {
    }

    public UserRegistrationMail(final String email, final String activationUrl) {
        this.email = email;
        this.activationUrl = activationUrl;
    }

    public String getEmail() {
        return email;
    }

    public String getActivationUrl() {
        return activationUrl;
    }

    public void setEmail(final String email) {
        this.email = email;
    }

    public void setActivationUrl(final String activationUrl) {
        this.activationUrl = activationUrl;
    }

    SimpleMailMessage getMailMessage() {
        final SimpleMailMessage msg = new SimpleMailMessage(this.mailTemplateMessage);
        msg.setTo(getEmail());
        msg.setSubject(getMessageSubject());
        msg.setText(getMessageBody());
        return msg;
    }

    public String getMessageSubject() {
        return messageSource.getMessage("userRegistration.registration.mail.subject", null, Locale.getDefault());
    }

    public String getMessageBody() {
        return messageSource.getMessage("userRegistration.registration.mail.body",
                new Object[] { this.email, this.activationUrl }, Locale.getDefault());
    }

    @Override
    public String toString() {
        return "UserRegistrationMail{" +
                "mailTemplateMessage=" + mailTemplateMessage +
                ", email='" + email + '\'' +
                ", activationUrl='" + activationUrl + '\'' +
                '}';
    }
}
