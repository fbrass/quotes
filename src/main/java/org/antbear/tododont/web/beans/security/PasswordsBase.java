package org.antbear.tododont.web.beans.security;

import org.antbear.tododont.web.beans.security.validation.Password;

public abstract class PasswordsBase {

    @Password
    private String password;

    private String password2;

    protected PasswordsBase() {
    }

    protected PasswordsBase(final String password) {
        this.password = password;
        this.password2 = password;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(final String password) {
        this.password = password;
    }

    public String getPassword2() {
        return password2;
    }

    public void setPassword2(final String password2) {
        this.password2 = password2;
    }

    public static String getLoggablePassword(final String s) {
        return new String(new char[s.length()]).replace("\0", "x");
    }

    @Override
    public String toString() {
        return "PasswordsBase{" +
                ", password='" + getLoggablePassword(this.password) + '\'' +
                ", password2='" + getLoggablePassword(this.password2) + '\'' +
                '}';
    }
}
