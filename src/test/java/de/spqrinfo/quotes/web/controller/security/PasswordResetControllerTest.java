package de.spqrinfo.quotes.web.controller.security;

import de.spqrinfo.quotes.backend.security.beans.PasswordResetAttempt;
import de.spqrinfo.quotes.backend.security.service.SecurityMail;
import de.spqrinfo.quotes.backend.security.service.SecurityMailSenderNullTestSupport;
import de.spqrinfo.quotes.backend.security.beans.PasswordReset;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.ModelAndView;

import java.net.URLDecoder;
import java.util.Map;
import java.util.regex.Pattern;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/test-base-context.xml", "classpath:/test-mail-null-context.xml"})
public class PasswordResetControllerTest {

    private static final String EMAIL = "alice@nowhere.tld";
    private static final Pattern HTTP_PATTERN = Pattern.compile("^https?://");
    private static final Pattern CONTROLLER_PREFIX = Pattern.compile("^s/pwr/c/");

    @Value("${web.app.base.uri}")
    private String applicationBaseUri;

    @Autowired
    private PasswordResetController passwordResetController;

    @Autowired
    private SecurityMailSenderNullTestSupport securityMailSenderTestSupport;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Test
    public void completePasswordResetWorkflowTest() throws Exception {
        final ModelAndView showPasswordResetModelAndView = this.passwordResetController.showPasswordReset();
        assertThat(showPasswordResetModelAndView.getViewName(), is("security/password-reset/start"));
        final Map<String, Object> model = showPasswordResetModelAndView.getModel();
        final PasswordResetAttempt passwordResetAttempt = (PasswordResetAttempt) model.get("passwordResetAttempt");
        passwordResetAttempt.setEmail(EMAIL);

        final BindingResult bindingResult = new BeanPropertyBindingResult(passwordResetAttempt, "passwordResetAttempt");
        final ModelAndView passwordResetAttemptModelAndView = this.passwordResetController.passwordReset(passwordResetAttempt, bindingResult);
        assertThat(passwordResetAttemptModelAndView.getViewName(), is("security/password-reset/done"));

        // A mail should have been sent
        final SecurityMail passwordResetMail = this.securityMailSenderTestSupport.getSecurityMail();
        assertNotNull(passwordResetMail);
        assertEquals(EMAIL, passwordResetMail.getEmail());
        final String passwordResetUrl = passwordResetMail.getUrl();
        assertNotNull(passwordResetUrl);

        final String webAppUrlWithoutSchema = HTTP_PATTERN.matcher(this.applicationBaseUri).replaceFirst("");
        final String givenUrlWithoutSchema = HTTP_PATTERN.matcher(passwordResetUrl).replaceFirst("");
        final String givenUrlWithoutPrefix = givenUrlWithoutSchema.replaceFirst('^' + Pattern.quote(webAppUrlWithoutSchema), "");
        final String dataPart = CONTROLLER_PREFIX.matcher(givenUrlWithoutPrefix).replaceFirst("");
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

        final Authentication authenticationToken = new UsernamePasswordAuthenticationToken(EMAIL, passwordReset.getPassword());
        this.authenticationManager.authenticate(authenticationToken); // No exception: OK, authenticated
    }
}
