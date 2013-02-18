package org.antbear.tododont.web.security.beans;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:/test-context.xml")
public class RegistrationTest {

    public static final String EMAIL = "alice@nowhere.tld";
    public static final String PASSWORD = "secRET@0815";

    private Validator validator;

    @Before
    public void setUp() throws Exception {
        this.validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Test
    public void passwordTest() {
        final Registration registration = new Registration();
        registration.setEmail(EMAIL);
        registration.setPassword(PASSWORD);
        registration.setPassword2(PASSWORD);

        final Set<ConstraintViolation<Registration>> violations = validator.validate(registration);
        assertThat(violations.size(), is(0));
    }

    @Test
    public void passwordMismatchTest() {
        final Registration registration = new Registration();
        registration.setEmail(EMAIL);
        registration.setPassword(PASSWORD);
        registration.setPassword2("justAnother@0815PASS");

        final Set<ConstraintViolation<Registration>> violations = validator.validate(registration);
        assertThat(violations.size(), is(1));
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
