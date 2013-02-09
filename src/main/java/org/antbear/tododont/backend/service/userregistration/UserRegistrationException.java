package org.antbear.tododont.backend.service.userregistration;

public class UserRegistrationException extends Exception {

    private final String userMessage;

    public UserRegistrationException(final String userMessage) {
        this.userMessage = userMessage;
    }

    public UserRegistrationException(final String message, final String userMessage) {
        super(message);
        this.userMessage = userMessage;
    }

    public UserRegistrationException(final String message, final Throwable cause, final String userMessage) {
        super(message, cause);
        this.userMessage = userMessage;
    }

    public UserRegistrationException(final Throwable cause, final String userMessage) {
        super(cause);
        this.userMessage = userMessage;
    }

    public String getUserMessage() {
        return this.userMessage;
    }
}
