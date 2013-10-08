package de.spqrinfo.quotes.backend.security.beans;

public class PasswordChange extends PasswordsBase {

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
                "currentPassword='" + getLoggablePassword(currentPassword) + '\'' +
                "} " + super.toString();
    }
}
