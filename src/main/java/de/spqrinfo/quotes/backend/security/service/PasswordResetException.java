package de.spqrinfo.quotes.backend.security.service;

import de.spqrinfo.quotes.backend.security.beans.PasswordResetAttempt;
import de.spqrinfo.quotes.backend.security.util.LocalizableException;

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
