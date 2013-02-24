package org.antbear.tododont.web.security.controller;

import org.antbear.tododont.backend.security.service.SecurityMail;
import org.antbear.tododont.backend.security.service.SecurityMailSenderTestSupport;
import org.antbear.tododont.web.security.beans.PasswordReset;
import org.antbear.tododont.web.security.beans.PasswordResetAttempt;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.ModelAndView;

import java.net.URLDecoder;
import java.util.Map;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:/test-context.xml")
public class PasswordResetControllerTest {

    private static final String EMAIL = "alice@nowhere.tld";

    @Autowired
    private PasswordResetController passwordResetController;

    @Autowired
    private SecurityMailSenderTestSupport securityMailSenderTestSupport;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Test
    public void completePasswordResetWorkflowTest() throws Exception {
        final ModelAndView showPasswordResetModelAndView = this.passwordResetController.showPasswordReset();
        assertThat(showPasswordResetModelAndView.getViewName(), is("security/password-reset/start"));
        final Map<String, Object> model = showPasswordResetModelAndView.getModel();
        final PasswordResetAttempt passwordResetAttempt = (PasswordResetAttempt) model.get("passwordResetAttempt");
        passwordResetAttempt.setEmail(EMAIL);

        BindingResult bindingResult = new BeanPropertyBindingResult(passwordResetAttempt, "passwordResetAttempt");
        final ModelAndView passwordResetAttemptModelAndView = this.passwordResetController.passwordReset(passwordResetAttempt, bindingResult);
        assertThat(passwordResetAttemptModelAndView.getViewName(), is("security/password-reset/done"));

        // A mail should have been sent
        final SecurityMail passwordResetMail = this.securityMailSenderTestSupport.getSecurityMail();
        assertNotNull(passwordResetMail);
        assertEquals(EMAIL, passwordResetMail.getEmail());
        final String passwordResetUrl = passwordResetMail.getUrl();
        assertNotNull(passwordResetUrl);

        final String dataPart = passwordResetUrl.replaceFirst("https?://[^/]+/s/pwr/c/", "");
        final String[] emailAndToken = dataPart.split("/", 2);
        assertNotNull(emailAndToken);
        assertThat(emailAndToken.length, is(2));
        assertNotNull(emailAndToken[0]);
        assertNotNull(emailAndToken[1]);
        assertThat(emailAndToken[0], is(EMAIL));
        final String token = URLDecoder.decode(emailAndToken[1], "utf-8");

        final ModelAndView passwordResetModelAndView = this.passwordResetController.passwordReset(EMAIL, token);
        assertThat(passwordResetModelAndView.getViewName(), is("security/password-reset/change/start"));
        final PasswordReset passwordReset = (PasswordReset) passwordResetModelAndView.getModelMap().get("passwordReset");
        assertNotNull(passwordReset);
        assertThat(passwordReset.getPasswordResetToken(), is(token));
        assertThat(passwordReset.getEmail(), is(EMAIL));

        passwordReset.setPassword("newPassword@0815Sec");
        passwordReset.setPassword2(passwordReset.getPassword());
        final BindingResult performPasswordResetBindingResult= new BeanPropertyBindingResult(passwordReset, "passwordReset");
        final ModelAndView performPasswordResetModelAndView = this.passwordResetController.performPasswordReset(passwordReset, performPasswordResetBindingResult);
        assertThat(performPasswordResetModelAndView.getViewName(), is("security/password-reset/change/done"));

        final UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(EMAIL, passwordReset.getPassword());
        authenticationManager.authenticate(authenticationToken); // No exception: OK, authenticated
    }
}
