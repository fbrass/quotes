package org.antbear.tododont.backend.service.userregistration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Component;

@Component("prototype")
public class UserRegistrationMail {

    @Autowired
    private SimpleMailMessage mailTemplateMessage;

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
        msg.setTo(this.email);
        msg.setText(getMessageBody());
        return msg;
    }

    public String getMessageBody() {
        return "Dear " + this.email + ",\n"
                + "thank you for registering. Please activate your login by following this link,\n"
                + "or by copying to your browsers address bar.\n"
                + "URL: " + this.activationUrl + "\n"
                + "Cheers";
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
