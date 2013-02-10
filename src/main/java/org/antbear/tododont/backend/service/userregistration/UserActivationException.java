package org.antbear.tododont.backend.service.userregistration;

import org.antbear.tododont.util.LocalizableException;

public class UserActivationException extends LocalizableException {

    private final String email;
    private final String activationToken;

    public UserActivationException(final String messageKey, final String email, final String activationToken) {
        super(messageKey);
        this.email = email;
        this.activationToken = activationToken;
    }

    public UserActivationException(final String message, final String messageKey, final String email, final String activationToken) {
        super(message, messageKey);
        this.email = email;
        this.activationToken = activationToken;
    }

    public UserActivationException(final String message, final Throwable cause, final String messageKey, final String email, final String activationToken) {
        super(message, cause, messageKey);
        this.email = email;
        this.activationToken = activationToken;
    }

    public UserActivationException(final Throwable cause, final String messageKey, final String email, final String activationToken) {
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
