package org.antbear.tododont.web.beans.security.validation;

import org.antbear.tododont.web.beans.security.PasswordsBase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class PasswordsBaseValidator implements Validator {

    private static final Logger log = LoggerFactory.getLogger(PasswordsBaseValidator.class);

    @Override
    public boolean supports(final Class<?> clazz) {
        return PasswordsBase.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(final Object target, final Errors errors) {
        ValidationUtils.rejectIfEmpty(errors, "password", "password.empty");
        ValidationUtils.rejectIfEmpty(errors, "password2", "password2.empty");

        final Object password = errors.getFieldValue("password");
        final Object password2 = errors.getFieldValue("password2");
        if (password != null && !password.equals(password2)) {
            final String errorCode = "password2.notEqual";
            log.debug("Passwords don't match");
            errors.rejectValue("password2", "password2.notEqual");
        }
    }
}
