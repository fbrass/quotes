package org.antbear.tododont.backend.entity;

import javax.validation.constraints.NotNull;
import java.util.Date;

public class ScheduledRegistrationMail implements DomainObject<Long> {

    @NotNull
    private Long id;

    @NotNull
    private String email;

    @NotNull
    private String activationUrl;

    private int attempts = 1;

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
        return email;
    }

    public void setEmail(final String email) {
        this.email = email;
    }

    public String getActivationUrl() {
        return activationUrl;
    }

    public void setActivationUrl(final String activationUrl) {
        this.activationUrl = activationUrl;
    }

    public int getAttempts() {
        return attempts;
    }

    public void setAttempts(final int attempts) {
        this.attempts = attempts;
    }

    public Date getLastAttempt() {
        return lastAttempt;
    }

    public void setLastAttempt(final Date lastAttempt) {
        this.lastAttempt = lastAttempt;
    }

    @Override
    public String toString() {
        return "ScheduledRegistrationMail{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", activationUrl='" + activationUrl + '\'' +
                ", attempts=" + attempts +
                ", lastAttempt=" + lastAttempt +
                '}';
    }
}
