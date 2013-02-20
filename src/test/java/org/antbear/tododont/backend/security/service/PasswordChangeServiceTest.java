package org.antbear.tododont.backend.security.service;

import org.antbear.tododont.backend.security.dao.UserDao;
import org.antbear.tododont.web.security.beans.PasswordChange;
import org.antbear.tododont.web.security.beans.Registration;
import org.antbear.tododont.web.security.controller.RegistrationController;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:/test-context.xml")
public class PasswordChangeServiceTest {

    private static final Logger log = LoggerFactory.getLogger(PasswordChangeServiceTest.class);

    private static final String EMAIL = "new-test-user@nowhere.tld";

    private static final String PASSWORD = "pr3TtYS3c0R3";

    private static final String PASSWORD_NEW = "secret@0815LOLCats";

    @Autowired
    private RegistrationService registrationService;

    @Autowired
    private RegistrationController registrationController;

    @Autowired
    private UserDao userDao;

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

        this.registrationService.activate(EMAIL, activationToken);
        assertTrue(this.userDao.getActiveStateByUser(EMAIL));
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