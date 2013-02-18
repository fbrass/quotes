package org.antbear.tododont.web.controller.security;

import org.antbear.tododont.web.beans.security.Registration;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class RegistrationTest {

    private static Validator validator;

    @BeforeClass
    public static void setup() {
        final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void testInvalidPassword() throws Exception {
        final Registration registration = new Registration("foo@bar.tld", "invalid");
        final Set<ConstraintViolation<Registration>> cs = validator.validate(registration);
        assertTrue(cs.size() > 0);
    }

    @Test
    public void testValidPassword() throws Exception {
        final Registration registration = new Registration("foo@bar.tld", "abc123_XZY");
        final Set<ConstraintViolation<Registration>> cs = validator.validate(registration);
        assertEquals(0, cs.size());
    }
}
