package org.antbear.tododont.web.beans;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class UserRegistration {

    @NotNull
    @Size(max = 128, message = "{org.antbear.tododont.web.beans.UserRegistration.email.Size}")
    @Pattern(regexp = "\\S+@\\S+", message = "{org.antbear.tododont.web.beans.UserRegistration.email.Pattern}")
    private String email;

    /* Simple password complexity restrictions:
     *
     * http://stackoverflow.com/questions/3721843/here-is-a-regular-expression-for-password-complexity-can-someone-show-me-how-to
     *
     * (?=^.{10,}$)(?=.*[a-z])(?=.*[A-Z])(?=.*[\W_])(?=^.*[^\s].*$).*$
     * ^          ^          ^          ^            ^
     * |          |          |          |            L--does not contain a whitespace
     * |          |          |          L--at least one non word character(a-zA-Z0-9_) or _ or 0-9
     * |          |          L--at least one upper case letter
     * |          L--at least one lowercase Letter
     * L--Number of charaters
     */
    @NotNull
    @Size(max = 64, message = "{org.antbear.tododont.web.beans.UserRegistration.password.Size}")
    @Pattern(regexp = "(?=^.{10,}$)(?=.*[a-z])(?=.*[A-Z])(?=.*[\\W_])(?=^.*[^\\s].*$).*$",
            message = "{org.antbear.tododont.web.beans.UserRegistration.password.Pattern}")
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
