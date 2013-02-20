package org.antbear.tododont.web.security.beans.validation;

import org.antbear.tododont.web.security.beans.PasswordChange;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.security.Principal;

@Component
public class PasswordChangeValidator implements Validator {

    private static final Logger log = LoggerFactory.getLogger(PasswordChangeValidator.class);

    @Autowired
    @Qualifier("authenticationManager")
    private AuthenticationManager authenticationManager;

    @Override
    public boolean supports(final Class<?> clazz) {
        return PasswordChange.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(final Object target, final Errors errors) {
        final Principal principal = SecurityContextHolder.getContext().getAuthentication();

        final PasswordChange passwordChange = (PasswordChange) target;

        final UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(principal.getName(),
                        passwordChange.getCurrentPassword());

        try {
            authenticationManager.authenticate(authenticationToken);
            log.debug("Current user password is valid");
        } catch (AuthenticationException e) {
            log.info("Current user password is invalid", e);
            errors.rejectValue("currentPassword", "passwordChange.currentPassword.invalid");
        }
    }
}
