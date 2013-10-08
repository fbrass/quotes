package de.spqrinfo.quotes.backend.admin.entity;

import de.spqrinfo.quotes.backend.dao.DomainObject;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Size;
import java.util.Date;

public class CustomUser implements DomainObject<String> {

    @NotNull
    @Size(max = 128)
    private String email;

    @NotNull
    private Boolean enabled;

    @NotNull
    private Boolean hasRegistrationToken;

    @Null
    private Date registeredSince;

    @NotNull
    private Boolean hasPasswordResetToken;

    @Null
    private Date passwordResetRequestedAt;

    @Override
    public String getPK() {
        return this.email;
    }

    @Override
    public void setPK(@NotNull final String s) {
        this.email = s;
    }

    public Boolean getEnabled() {
        return this.enabled;
    }

    public void setEnabled(final Boolean enabled) {
        this.enabled = enabled;
    }

    public Boolean getHasRegistrationToken() {
        return this.hasRegistrationToken;
    }

    public void setHasRegistrationToken(final Boolean hasRegistrationToken) {
        this.hasRegistrationToken = hasRegistrationToken;
    }

    public Date getRegisteredSince() {
        return this.registeredSince;
    }

    public void setRegisteredSince(final Date registeredSince) {
        this.registeredSince = registeredSince;
    }

    public Boolean getHasPasswordResetToken() {
        return this.hasPasswordResetToken;
    }

    public void setHasPasswordResetToken(final Boolean hasPasswordResetToken) {
        this.hasPasswordResetToken = hasPasswordResetToken;
    }

    public Date getPasswordResetRequestedAt() {
        return this.passwordResetRequestedAt;
    }

    public void setPasswordResetRequestedAt(final Date passwordResetRequestedAt) {
        this.passwordResetRequestedAt = passwordResetRequestedAt;
    }
}
