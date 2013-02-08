package org.antbear.tododont.backend.service.userregistration;

public class UserRegistrationException extends Exception {

    public UserRegistrationException() {
    }

    public UserRegistrationException(final String message) {
        super(message);
    }

    public UserRegistrationException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public UserRegistrationException(final Throwable cause) {
        super(cause);
    }
}
