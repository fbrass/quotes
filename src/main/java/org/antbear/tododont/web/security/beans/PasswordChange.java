package org.antbear.tododont.web.security.beans;

import org.antbear.tododont.web.security.validation.Password;

public class PasswordChange extends PasswordsBase {

    @Password
    private String currentPassword;

    public String getCurrentPassword() {
        return currentPassword;
    }

    public void setCurrentPassword(final String currentPassword) {
        this.currentPassword = currentPassword;
    }

    @Override
    public String toString() {
        return "PasswordChange{" +
                "currentPassword='" + currentPassword + '\'' +
                "} " + super.toString();
    }
}
