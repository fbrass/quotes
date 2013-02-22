package org.antbear.tododont.backend.security.entity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.CredentialsContainer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class CustomUserDetails implements UserDetails, CredentialsContainer {

    private static final Logger log = LoggerFactory.getLogger(CustomUserDetails.class);

    private final String email;
    private String password;
    private final boolean enabled;
    private final Set<GrantedAuthority> authorities;
    private String registrationToken;
    private final Date registeredSince;
    private String passwordResetToken;
    private final boolean accountNonExpired;
    private final boolean accountNonLocked;
    private final boolean credentialsNonExpired;

    /**
     * Initializes an inactive user.
     */
    public CustomUserDetails(final String email,
                             final String registrationToken) {

        this(email, null, false, new HashSet<GrantedAuthority>(), registrationToken, new Date(), null, true, true, true);
    }

    public CustomUserDetails(final String email,
                             final String password,
                             final boolean enabled,
                             final Set<GrantedAuthority> authorities,
                             final String registrationToken,
                             final Date registeredSince,
                             final String passwordResetToken,
                             final boolean accountNonExpired,
                             final boolean accountNonLocked,
                             final boolean credentialsNonExpired) {
        this.email = email;
        this.password = password;
        this.enabled = enabled;
        this.authorities = authorities;
        this.registrationToken = registrationToken;
        this.registeredSince = registeredSince;
        this.passwordResetToken = passwordResetToken;
        this.accountNonExpired = accountNonExpired;
        this.accountNonLocked = accountNonLocked;
        this.credentialsNonExpired = credentialsNonExpired;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    public void setPassword(final String password) {
        this.password = password;
    }

    @Override
    public boolean isEnabled() {
        return this.enabled;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    public String getRegistrationToken() {
        return registrationToken;
    }

    public void setRegistrationToken(final String registrationToken) {
        this.registrationToken = registrationToken;
    }

    public Date getRegisteredSince() {
        return registeredSince;
    }

    public String getPasswordResetToken() {
        return passwordResetToken;
    }

    public void setPasswordResetToken(final String passwordResetToken) {
        this.passwordResetToken = passwordResetToken;
    }

    @Override
    public boolean isAccountNonExpired() {
        return this.accountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return this.credentialsNonExpired;
    }

    @Override
    public void eraseCredentials() {
        log.debug("eraseCredentials {}", this.email);
        this.password = null;
    }
}
