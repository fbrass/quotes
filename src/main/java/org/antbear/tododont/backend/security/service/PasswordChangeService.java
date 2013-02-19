package org.antbear.tododont.backend.security.service;

import org.antbear.tododont.web.security.beans.PasswordChange;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

// TODO missing test
@Service
public class PasswordChangeService {

    private static final Logger log = LoggerFactory.getLogger(PasswordChangeService.class);

    public void passwordChange(final String email, final PasswordChange passwordChange) throws PasswordChangeException {
        log.debug("Password change request of {} with {}", email, passwordChange);
        throw new PasswordChangeException("Not implemented"); // TODO missing impl
    }
}
