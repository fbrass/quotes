package org.antbear.tododont.backend.service.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Scope;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
@Scope("prototype")
public class RegistrationMail implements SecurityMail {

    @Autowired
    private SimpleMailMessage mailTemplateMessage;

    @Autowired
    private MessageSource messageSource;

    private String email;
    private String url;

    public RegistrationMail() {
    }

    public RegistrationMail(final String email, final String url) {
        this.email = email;
        this.url = url;
    }

    public String getEmail() {
        return email;
    }

    public String getUrl() {
        return url;
    }

    public void setEmail(final String email) {
        this.email = email;
    }

    public void setUrl(final String url) {
        this.url = url;
    }

    public SimpleMailMessage getMailMessage() {
        final SimpleMailMessage msg = new SimpleMailMessage(this.mailTemplateMessage);
        msg.setTo(getEmail());
        msg.setSubject(getMessageSubject());
        msg.setText(getMessageBody());
        return msg;
    }

    public String getMessageSubject() {
        return messageSource.getMessage("registration.mail.subject", null, Locale.getDefault());
    }

    public String getMessageBody() {
        return messageSource.getMessage("registration.mail.body",
                new Object[] { this.email, this.url}, Locale.getDefault());
    }

    @Override
    public String toString() {
        return "UserRegistrationMail{" +
                "mailTemplateMessage=" + mailTemplateMessage +
                ", email='" + email + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}