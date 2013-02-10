package org.antbear.tododont.backend.service.userregistration;

import org.antbear.tododont.util.LocalizableException;
import org.antbear.tododont.web.beans.UserRegistration;

public class UserRegistrationException extends LocalizableException {

    private final UserRegistration userRegistration;

    public UserRegistrationException(final String messageKey, final UserRegistration userRegistration) {
        super(messageKey);
        this.userRegistration = userRegistration;
    }

    public UserRegistrationException(final String message, final String messageKey, final UserRegistration userRegistration) {
        super(message, messageKey);
        this.userRegistration = userRegistration;
    }

    public UserRegistrationException(final String message, final Throwable cause, final String messageKey, final UserRegistration userRegistration) {
        super(message, cause, messageKey);
        this.userRegistration = userRegistration;
    }

    public UserRegistrationException(final Throwable cause, final String messageKey, final UserRegistration userRegistration) {
        super(cause, messageKey);
        this.userRegistration = userRegistration;
    }

    public UserRegistration getUserRegistration() {
        return userRegistration;
    }
}
