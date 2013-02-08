package org.antbear.tododont.backend.service.userregistration;

public interface UserRegistrationMailSender {

    void sendRegistration(final UserRegistrationMail userRegistrationMail);
}
