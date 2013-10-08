package de.spqrinfo.quotes.backend.security.service;

import de.spqrinfo.quotes.backend.security.beans.PasswordChange;
import de.spqrinfo.quotes.backend.security.beans.Registration;
import de.spqrinfo.quotes.backend.security.dao.CustomUserDetailsService;
import de.spqrinfo.quotes.web.controller.security.RegistrationController;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/test-base-context.xml", "classpath:/test-mail-null-context.xml"})
public class PasswordChangeServiceTest {

    //private static final Logger log = LoggerFactory.getLogger(PasswordChangeServiceTest.class);

    private static final String EMAIL = "newUser@nowhere.tld";

    private static final String PASSWORD = "pr3TtYS3c0R3";

    private static final String PASSWORD_NEW = "secret@0815LOLCats";

    @Autowired
    private RegistrationService registrationService;

    @Autowired
    private RegistrationController registrationController;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Autowired
    private PasswordChangeService passwordChangeService;

    @Autowired
    @Qualifier("authenticationManager")
    private AuthenticationManager authenticationManager;

    @Before
    public void setUp() throws RegistrationException, RegistrationActivationException {
        final Registration registration = new Registration(EMAIL, PASSWORD);

        final String activationToken = this.registrationService.register(registration, this.registrationController.getActivationUriComponents());
        assertNotNull(activationToken);

        this.registrationService.activate(activationToken);
        assertTrue(this.userDetailsService.loadUserByUsername(EMAIL).isEnabled());
    }

    @Test
    public void passwordChangeTest() throws PasswordChangeException {
        final PasswordChange passwordChange = new PasswordChange();
        passwordChange.setCurrentPassword(PASSWORD);
        passwordChange.setPassword(PASSWORD_NEW);
        passwordChange.setPassword2(PASSWORD_NEW);

        this.passwordChangeService.passwordChange(EMAIL, passwordChange);

        final UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(EMAIL, PASSWORD_NEW);
        authenticationManager.authenticate(authenticationToken); // No exception: OK, authenticated
    }
}
