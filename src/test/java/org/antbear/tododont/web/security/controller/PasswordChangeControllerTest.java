package org.antbear.tododont.web.security.controller;

import org.antbear.tododont.backend.security.dao.CustomUserDetailsService;
import org.antbear.tododont.backend.security.service.RegistrationService;
import org.antbear.tododont.web.security.beans.PasswordChange;
import org.antbear.tododont.web.security.beans.Registration;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.ModelAndView;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:/test-context.xml")
public class PasswordChangeControllerTest {

    private static final String EMAIL = "new-test-user@nowhere.tld";

    private static final String PASSWORD = "pr3TtYS3c0R3";

    private static final String PASSWORD_NEW = "secret@0815LOLCats";

    @Autowired
    private RegistrationService registrationService;

    @Autowired
    private RegistrationController registrationController;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Autowired
    private PasswordChangeController passwordChangeController;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Before
    public void setUp() throws Exception {
        final Registration registration = new Registration(EMAIL, PASSWORD);

        final String activationToken = this.registrationService.register(registration, this.registrationController.getActivationUriComponents());
        assertNotNull(activationToken);

        this.registrationService.activate(EMAIL, activationToken);
        assertTrue(this.userDetailsService.loadUserByUsername(EMAIL).isEnabled());
    }

    @Test
    public void completePasswordChangeWorkflowTest() throws Exception {
        final ModelAndView startPasswordChangeModelAndView = this.passwordChangeController.startChangePassword();
        assertThat(startPasswordChangeModelAndView.getViewName(), is("security/password-change/start"));
        final PasswordChange passwordChange = (PasswordChange) startPasswordChangeModelAndView.getModel().get("passwordChange");
        passwordChange.setCurrentPassword(PASSWORD);
        passwordChange.setPassword(PASSWORD_NEW);
        passwordChange.setPassword2(PASSWORD_NEW);

        final UsernamePasswordAuthenticationToken principal = new UsernamePasswordAuthenticationToken(EMAIL, "ignored");
        // set the principal in the security context hold, used indirectly in the PasswordResetService
        SecurityContextHolder.getContext().setAuthentication(principal);
        final BindingResult bindingResult = new BeanPropertyBindingResult(passwordChange, "passwordChange");
        final String viewName = this.passwordChangeController.performChangePassword(principal, passwordChange, bindingResult);
        assertThat(viewName, is("/home"));

        final UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(EMAIL, PASSWORD_NEW);
        authenticationManager.authenticate(authenticationToken); // No exception: OK, authenticated
    }
}
