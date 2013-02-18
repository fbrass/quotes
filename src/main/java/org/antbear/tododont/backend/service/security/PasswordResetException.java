package org.antbear.tododont.backend.service.security;

import org.antbear.tododont.util.LocalizableException;
import org.antbear.tododont.web.beans.security.PasswordResetAttempt;

public class PasswordResetException extends LocalizableException {

    private final PasswordResetAttempt passwordResetAttempt;

    public PasswordResetException(final String messageKey, final PasswordResetAttempt passwordResetAttempt) {
        super(messageKey);
        this.passwordResetAttempt = passwordResetAttempt;
    }

    public PasswordResetException(final String message, final String messageKey, final PasswordResetAttempt passwordResetAttempt) {
        super(message, messageKey);
        this.passwordResetAttempt = passwordResetAttempt;
    }

    public PasswordResetException(final String message, final Throwable cause, final String messageKey, final PasswordResetAttempt passwordResetAttempt) {
        super(message, cause, messageKey);
        this.passwordResetAttempt = passwordResetAttempt;
    }

    public PasswordResetException(final Throwable cause, final String messageKey, final PasswordResetAttempt passwordResetAttempt) {
        super(cause, messageKey);
        this.passwordResetAttempt = passwordResetAttempt;
    }

    public PasswordResetAttempt getPasswordResetAttempt() {
        return passwordResetAttempt;
    }
}
