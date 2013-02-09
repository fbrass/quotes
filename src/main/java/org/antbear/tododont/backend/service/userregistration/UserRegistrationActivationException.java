package org.antbear.tododont.backend.service.userregistration;

public class UserRegistrationActivationException extends UserRegistrationException {

    private final String email;
    private final String activationToken;

    public UserRegistrationActivationException(final String message, final String userMessage, final String email, final String activationToken) {
        super(message, userMessage);
        this.email = email;
        this.activationToken = activationToken;
    }

    public UserRegistrationActivationException(final String message, final Throwable cause, final String userMessage, final String email, final String activationToken) {
        super(message, cause, userMessage);
        this.email = email;
        this.activationToken = activationToken;
    }

    public UserRegistrationActivationException(final Throwable cause, final String userMessage, final String email, final String activationToken) {
        super(cause, userMessage);
        this.email = email;
        this.activationToken = activationToken;
    }

    public String getEmail() {
        return email;
    }

    public String getActivationToken() {
        return activationToken;
    }
}
