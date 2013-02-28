package org.antbear.tododont.backend.security.service;

import org.antbear.tododont.backend.security.dao.CustomUserDetailsService;
import org.antbear.tododont.backend.security.entity.CustomUserDetails;
import org.antbear.tododont.web.security.annotation.RoleUser;
import org.antbear.tododont.web.security.beans.PasswordChange;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.SaltSource;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RoleUser
@Service
public class PasswordChangeService {

    private static final Logger log = LoggerFactory.getLogger(PasswordChangeService.class);

    public static final String PASSWORD_CHANGE_CURRENT_PASSWORD_INVALID = "passwordChange.currentPassword.invalid";

    @Autowired
    @Qualifier("authenticationManager")
    private AuthenticationManager authenticationManager;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Autowired
    private SaltSource saltSource;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private void validatePasswordChangeAttempt(final String email, final String currentPassword) throws PasswordChangeException {
        final UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(email, currentPassword);
        try {
            // TODO this may fail, check result of unit test: if it fails use saltSource and passwordEncoder.is...
            authenticationManager.authenticate(authenticationToken); // No exception: OK, authenticated
        } catch (AuthenticationException ae) {
            log.debug("Password change attempt cannot be validated for {}", email);
            throw new PasswordChangeException(ae, PASSWORD_CHANGE_CURRENT_PASSWORD_INVALID);
        }
    }

    @Transactional
    public void passwordChange(final String email, final PasswordChange passwordChange) throws PasswordChangeException {
        log.debug("Password change request of {} with {}", email, passwordChange);
        validatePasswordChangeAttempt(email, passwordChange.getCurrentPassword());

        final CustomUserDetails user = (CustomUserDetails) this.userDetailsService.loadUserByUsername(email);
        user.setPassword(this.passwordEncoder.encodePassword(passwordChange.getPassword(),
                this.saltSource.getSalt(user)));

        this.userDetailsService.updatePassword(user);
        log.info("Updated password of {}", email);
    }
}
