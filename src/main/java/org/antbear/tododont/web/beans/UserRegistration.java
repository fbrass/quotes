package org.antbear.tododont.web.beans;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class UserRegistration {

    @NotNull
    @Size(min = 1, max = 128)
    @Pattern(regexp = "\\S+@\\S+", message = "your email address must contain at least an @")
    private String email;

    /* Simple password complexity restrictions:
     *
     * http://stackoverflow.com/questions/3721843/here-is-a-regular-expression-for-password-complexity-can-someone-show-me-how-to
     *
     * (?=^.{8,}$)(?=.*[a-z])(?=.*[A-Z])(?=.*[\W_])(?=^.*[^\s].*$).*$
     * ^          ^          ^          ^            ^
     * |          |          |          |            L--does not contain a whitespace
     * |          |          |          L--at least one non word character(a-zA-Z0-9_) or _ or 0-9
     * |          |          L--at least one upper case letter
     * |          L--at least one lowercase Letter
     * L--Number of charaters
     */
    @NotNull
    @Size(min = 8, max = 64, message = "your password must be at least 8 letters long, up to 64 letters")
    @Pattern(regexp = "(?=^.{10,}$)(?=.*[a-z])(?=.*[A-Z])(?=.*[\\W_])(?=^.*[^\\s].*$).*$",
            message = "your password must at least contain 10 letters, one lower case letter, at least one UPPER case letter, at least one special letter and no whitespaces")
    private String password;

    public UserRegistration() {
    }

    public UserRegistration(final String email, final String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(final String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(final String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "UserRegistration{" +
                "email='" + email + '\'' +
                ", password='xxxxxxxxxx'" +
                '}';
    }
}
