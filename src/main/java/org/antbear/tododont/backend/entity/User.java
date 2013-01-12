package org.antbear.tododont.backend.entity;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class User implements DomainObject {

    @NotNull
    private Long id;

    @NotNull
    @Size(min = 1, max = 128)
    private String email;

    @NotNull
    private String cryptedPassword;

    @Override
    public Long getId() {
        return this.id;
    }

    @Override
    public void setId(@NotNull final Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(final String email) {
        this.email = email;
    }

    public String getCryptedPassword() {
        return cryptedPassword;
    }

    public void setCryptedPassword(final String cryptedPassword) {
        this.cryptedPassword = cryptedPassword;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", cryptedPassword='" + cryptedPassword + '\'' +
                '}';
    }
}
