package org.antbear.tododont.backend.service.userregistration;

import org.antbear.tododont.web.beans.UserRegistration;

public class UserRegistrationRegistrationException extends UserRegistrationException {

    private final UserRegistration userRegistration;

    public UserRegistrationRegistrationException(final String message, final String userMessage, final UserRegistration userRegistration) {
        super(message, userMessage);
        this.userRegistration = userRegistration;
    }

    public UserRegistrationRegistrationException(final String message, final Throwable cause, final String userMessage, final UserRegistration userRegistration) {
        super(message, cause, userMessage);
        this.userRegistration = userRegistration;
    }

    public UserRegistrationRegistrationException(final Throwable cause, final String userMessage, final UserRegistration userRegistration) {
        super(cause, userMessage);
        this.userRegistration = userRegistration;
    }

    public UserRegistration getUserRegistration() {
        return userRegistration;
    }
}
