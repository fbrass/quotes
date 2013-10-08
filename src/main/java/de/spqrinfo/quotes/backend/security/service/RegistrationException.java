package de.spqrinfo.quotes.backend.security.service;

import de.spqrinfo.quotes.backend.security.util.LocalizableException;
import de.spqrinfo.quotes.backend.security.beans.Registration;

public class RegistrationException extends LocalizableException {

    private final Registration registration;

    public RegistrationException(final String messageKey, final Registration registration) {
        super(messageKey);
        this.registration = registration;
    }

    public RegistrationException(final String message, final String messageKey, final Registration registration) {
        super(message, messageKey);
        this.registration = registration;
    }

    public RegistrationException(final String message, final Throwable cause, final String messageKey, final Registration registration) {
        super(message, cause, messageKey);
        this.registration = registration;
    }

    public RegistrationException(final Throwable cause, final String messageKey, final Registration registration) {
        super(cause, messageKey);
        this.registration = registration;
    }

    public Registration getRegistration() {
        return registration;
    }
}
