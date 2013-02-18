package org.antbear.tododont.backend.security.service;

import org.antbear.tododont.backend.security.dao.UserDao;
import org.antbear.tododont.backend.security.service.RegistrationException;
import org.antbear.tododont.backend.security.service.RegistrationService;
import org.antbear.tododont.web.security.beans.Registration;
import org.antbear.tododont.web.security.controller.RegistrationController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:/test-context.xml")
public class RegistrationServiceTest {

    @Autowired
    private RegistrationController registrationController;

    @Autowired
    private UserDao userDao;

    @Autowired
    private RegistrationService registrationService;

    @Test(expected = RegistrationException.class)
    public void testRegisterExistingUser() throws Exception {
        this.registrationService.register(new Registration("alice@nowhere.tld", "secR3Tlf993"),
                this.registrationController.getActivationUriComponents());
    }

    @Test
    public void testRegisterNewUser() throws Exception {
        final String email = "newuser134@nowhere.tld";
        final String expectedToken = this.registrationService.register(new Registration(email, "secR3Tlf993"),
                this.registrationController.getActivationUriComponents());
        final String actualToken = this.userDao.findRegistrationTokenByUser(email);
        assertEquals(expectedToken, actualToken);
        assertFalse(this.userDao.getActiveStateByUser(email));
    }
}
