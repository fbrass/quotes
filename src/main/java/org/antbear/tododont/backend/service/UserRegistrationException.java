package org.antbear.tododont.backend.service;

import org.antbear.tododont.web.controller.registration.UserRegistration;

public class UserRegistrationException extends Exception {

    private final UserRegistration userRegistration;

    public UserRegistrationException(final String message, final UserRegistration userRegistration) {
        super(message);
        this.userRegistration = userRegistration;
    }

    public UserRegistrationException(final String message, final Throwable cause, final UserRegistration userRegistration) {
        super(message, cause);
        this.userRegistration = userRegistration;
    }

    public UserRegistrationException(final Throwable cause, final UserRegistration userRegistration) {
        super(cause);
        this.userRegistration = userRegistration;
    }

    public UserRegistration getUserRegistration() {
        return userRegistration;
    }
}
