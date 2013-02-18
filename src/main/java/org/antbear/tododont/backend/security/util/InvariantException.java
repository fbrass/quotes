package org.antbear.tododont.backend.security.util;

public class InvariantException extends RuntimeException {

    public InvariantException() {
    }

    public InvariantException(final String message) {
        super(message);
    }

    public InvariantException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public InvariantException(final Throwable cause) {
        super(cause);
    }

    public static void notNull(final Object value, final String name) {
        if (value == null) {
            throw new InvariantException(name + " may not be null");
        }
    }

    public static void notNullOrEmpty(final String value, final String name) {
        if (value == null) {
            throw new InvariantException(name + " may not be null");
        } else if (value.isEmpty()) {
            throw new InvariantException(name + " may not be empty");
        }
    }
}
