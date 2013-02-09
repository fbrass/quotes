package org.antbear.tododont.backend.service.userregistration;

import org.antbear.tododont.web.beans.UserRegistration;

public class UserRegistrationRegistrationException extends UserRegistrationException {

    private final UserRegistration userRegistration;

    public UserRegistrationRegistrationException(final String message, final UserRegistration userRegistration) {
        super(message);
        this.userRegistration = userRegistration;
    }

    public UserRegistrationRegistrationException(final String message, final Throwable cause, final UserRegistration userRegistration) {
        super(message, cause);
        this.userRegistration = userRegistration;
    }

    public UserRegistrationRegistrationException(final Throwable cause, final UserRegistration userRegistration) {
        super(cause);
        this.userRegistration = userRegistration;
    }

    public UserRegistration getUserRegistration() {
        return userRegistration;
    }
}
