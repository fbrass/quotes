package org.antbear.tododont.backend.security.util;

public class LocalizableException extends Exception {

    private final String messageKey;

    public LocalizableException(final String messageKey) {
        this.messageKey = messageKey;
    }

    public LocalizableException(final String message, final String messageKey) {
        super(message);
        this.messageKey = messageKey;
    }

    public LocalizableException(final String message, final Throwable cause, final String messageKey) {
        super(message, cause);
        this.messageKey = messageKey;
    }

    public LocalizableException(final Throwable cause, final String messageKey) {
        super(cause);
        this.messageKey = messageKey;
    }

    public String getMessageKey() {
        return messageKey;
    }

    @Override
    public String toString() {
        return "LocalizableException{" +
                "messageKey='" + messageKey + '\'' +
                "} " + super.toString();
    }
}
