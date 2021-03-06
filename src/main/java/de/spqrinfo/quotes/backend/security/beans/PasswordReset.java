package de.spqrinfo.quotes.backend.security.beans;

import de.spqrinfo.quotes.backend.security.beans.validation.Email;

public class PasswordReset extends PasswordsBase {

    @Email
    private String email;

    private String passwordResetToken;

    public String getEmail() {
        return email;
    }

    public void setEmail(final String email) {
        this.email = email;
    }

    public String getPasswordResetToken() {
        return passwordResetToken;
    }

    public void setPasswordResetToken(final String passwordResetToken) {
        this.passwordResetToken = passwordResetToken;
    }

    @Override
    public String toString() {
        return "PasswordReset{" +
                "email='" + email + '\'' +
                ", passwordResetToken='" + passwordResetToken + '\'' +
                "} " + super.toString();
    }
}
