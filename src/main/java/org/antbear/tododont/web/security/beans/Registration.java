package org.antbear.tododont.web.security.beans;

import org.antbear.tododont.web.security.validation.Email;

public class Registration extends PasswordsBase {

    @Email
    private String email;

    public Registration() {
    }

    public Registration(final String email, final String password) {
        super(password);
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
        return "Registration{" +
                "email='" + email + '\'' +
                "} " + super.toString();
    }
}
