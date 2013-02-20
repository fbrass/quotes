package org.antbear.tododont.backend.security.service;

import org.antbear.tododont.backend.security.dao.UserDao;
import org.antbear.tododont.web.security.beans.PasswordChange;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class PasswordChangeService {

    private static final Logger log = LoggerFactory.getLogger(PasswordChangeService.class);

    public static final String PASSWORD_CHANGE_CURRENT_PASSWORD_INVALID = "passwordChange.currentPassword.invalid";

    @Autowired
    @Qualifier("authenticationManager")
    private AuthenticationManager authenticationManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserDao userDao;

    private void validatePasswordChangeAttempt(final String email, final String currentPassword) throws PasswordChangeException {
        final UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(email, currentPassword);

        try {
            authenticationManager.authenticate(authenticationToken); // No exception: OK, authenticated
        } catch (AuthenticationException ae) {
            log.debug("Password change attempt cannot be validated for {}", email);
            throw new PasswordChangeException(ae, PASSWORD_CHANGE_CURRENT_PASSWORD_INVALID);
        }
    }

    public void passwordChange(final String email, final PasswordChange passwordChange) throws PasswordChangeException {
        log.debug("Password change request of {} with {}", email, passwordChange);
        validatePasswordChangeAttempt(email, passwordChange.getCurrentPassword());
        final String encodedPassword = this.passwordEncoder.encode(passwordChange.getPassword());
        this.userDao.updatePassword(email, encodedPassword);
        log.info("Updated password of {}", email);
    }
}
