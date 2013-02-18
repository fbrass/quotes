package org.antbear.tododont.backend.security.service;

import org.antbear.tododont.backend.security.util.LocalizableException;

public class RegistrationActivationException extends LocalizableException {

    private final String email;
    private final String activationToken;

    public RegistrationActivationException(final String messageKey, final String email, final String activationToken) {
        super(messageKey);
        this.email = email;
        this.activationToken = activationToken;
    }

    public RegistrationActivationException(final String message, final String messageKey, final String email, final String activationToken) {
        super(message, messageKey);
        this.email = email;
        this.activationToken = activationToken;
    }

    public RegistrationActivationException(final String message, final Throwable cause, final String messageKey, final String email, final String activationToken) {
        super(message, cause, messageKey);
        this.email = email;
        this.activationToken = activationToken;
    }

    public RegistrationActivationException(final Throwable cause, final String messageKey, final String email, final String activationToken) {
        super(cause, messageKey);
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
