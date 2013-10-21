package de.spqrinfo.quotes.backend.security.entity;

import de.spqrinfo.quotes.backend.base.DomainObject;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Date;

public class SecurityTokenMailSchedule implements DomainObject<Long> {

    @NotNull
    private Long id;

    @NotNull
    private String email;

    @NotNull
    private String url;

    @Min(1)
    private int attempts = 1;

    @NotNull
    private Date firstAttempt = new Date();

    @NotNull
    private Date lastAttempt = new Date();

    @Override
    public Long getPK() {
        return this.id;
    }

    @Override
    public void setPK(@NotNull final Long pk) {
        this.id = pk;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(final String email) {
        this.email = email;
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(final String url) {
        this.url = url;
    }

    public int getAttempts() {
        return this.attempts;
    }

    public void setAttempts(final int attempts) {
        this.attempts = attempts;
    }

    public Date getFirstAttempt() {
        return this.firstAttempt;
    }

    public void setFirstAttempt(final Date firstAttempt) {
        this.firstAttempt = firstAttempt;
    }

    public Date getLastAttempt() {
        return this.lastAttempt;
    }

    public void setLastAttempt(final Date lastAttempt) {
        this.lastAttempt = lastAttempt;
    }

    @Override
    public String toString() {
        return "UserRegistrationMailSchedule{" +
                "id=" + this.id +
                ", email='" + this.email + '\'' +
                ", url='" + this.url + '\'' +
                ", attempts=" + this.attempts +
                ", firstAttempt=" + this.firstAttempt +
                ", lastAttempt=" + this.lastAttempt +
                '}';
    }
}
