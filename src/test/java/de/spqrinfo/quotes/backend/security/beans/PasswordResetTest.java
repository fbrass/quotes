package de.spqrinfo.quotes.backend.security.beans;

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
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/test-base-context.xml", "classpath:/test-mail-null-context.xml"})
public class PasswordResetTest {

    public static final String EMAIL = "alice@nowhere.tld";
    public static final String PASSWORD = "secRET@0815";

    private Validator validator;

    @Before
    public void setUp() throws Exception {
        this.validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Test
    public void passwordTest() throws Exception {
        final PasswordReset passwordReset = new PasswordReset();
        passwordReset.setEmail(EMAIL);
        passwordReset.setPassword(PASSWORD);
        passwordReset.setPassword2(PASSWORD);

        final Set<ConstraintViolation<PasswordReset>> violations = validator.validate(passwordReset);
        assertThat(violations.size(), is(0));
    }

    @Test
    public void passwordMismatchTest() throws Exception {
        final PasswordReset passwordReset = new PasswordReset();
        passwordReset.setEmail(EMAIL);
        passwordReset.setPassword(PASSWORD);
        passwordReset.setPassword2("justAnother@0815PASS");

        final Set<ConstraintViolation<PasswordReset>> violations = validator.validate(passwordReset);
        assertThat(violations.size(), is(1));
    }
}
