package org.antbear.tododont.backend.security.service;

import org.antbear.tododont.backend.security.util.LocalizableException;

public class PasswordChangeException extends LocalizableException {

    public PasswordChangeException(final String messageKey) {
        super(messageKey);
    }

    public PasswordChangeException(final String message, final String messageKey) {
        super(message, messageKey);
    }

    public PasswordChangeException(final String message, final Throwable cause, final String messageKey) {
        super(message, cause, messageKey);
    }

    public PasswordChangeException(final Throwable cause, final String messageKey) {
        super(cause, messageKey);
    }
}
