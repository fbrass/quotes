package org.antbear.tododont.backend.security.service;

import org.antbear.tododont.backend.security.dao.CustomUserDetailsService;
import org.antbear.tododont.backend.security.entity.CustomUserDetails;
import org.antbear.tododont.web.security.beans.Registration;
import org.antbear.tododont.web.security.controller.RegistrationController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;

@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:/test-context.xml")
public class RegistrationServiceTest {

    private static final String EMAIL = "newUser@nowhere.tld";

    private static final String PASSWORD = "secR3Tlf993";

    @Autowired
    private RegistrationService registrationService;

    @Autowired
    private RegistrationController registrationController;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Test(expected = RegistrationException.class)
    public void testRegisterExistingUser() throws Exception {
        final String email = "alice@nowhere.tld";
        final String password = "secR3Tlf993";

        assertTrue(((CustomUserDetails) this.userDetailsService.loadUserByUsername(email)).isEnabled());

        this.registrationService.register(new Registration(email, password),
                this.registrationController.getActivationUriComponents());
    }

    @Test
    public void testRegisterNewUser() throws Exception {
        final String expectedToken = this.registrationService.register(new Registration(EMAIL, PASSWORD),
                this.registrationController.getActivationUriComponents());
        final String actualToken = ((CustomUserDetails) this.userDetailsService.loadUserByUsername(EMAIL)).getRegistrationToken();
        assertEquals(expectedToken, actualToken);
        assertFalse(((CustomUserDetails) this.userDetailsService.loadUserByUsername(EMAIL)).isEnabled());

        this.userDetailsService.deleteUser(EMAIL);
    }

    @Test
    public void activateTest() throws RegistrationException, RegistrationActivationException {
        final Registration registration = new Registration(EMAIL, PASSWORD);

        final String activationToken = this.registrationService.register(registration, this.registrationController.getActivationUriComponents());
        assertNotNull(activationToken);

        this.registrationService.activate(activationToken);
        assertTrue(((CustomUserDetails) this.userDetailsService.loadUserByUsername(EMAIL)).isEnabled());

        this.userDetailsService.deleteUser(EMAIL);
    }
}
