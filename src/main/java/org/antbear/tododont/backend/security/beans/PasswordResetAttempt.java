package org.antbear.tododont.backend.security.beans;

import org.antbear.tododont.backend.security.beans.validation.Email;

public class PasswordResetAttempt {

    @Email
    private String email;

    public PasswordResetAttempt() {
    }

    public PasswordResetAttempt(final String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(final String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "PasswordResetAttempt{" +
                "email='" + email + '\'' +
                '}';
    }
}
