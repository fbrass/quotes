package org.antbear.tododont.backend.service.userregistration;

import org.antbear.tododont.backend.dao.UserDao;
import org.antbear.tododont.web.beans.UserRegistration;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:/test-context.xml")
public class UserRegistrationServiceTest {

    @Autowired
    private UserDao userDao;

    @Autowired
    private UserRegistrationService userRegistrationService;

    private UriComponents userActivationUriComponents = UriComponentsBuilder.fromUriString(
            "http://localhost:8080/register/activate-user/{activationToken}").build();

    @Test(expected = UserRegistrationException.class)
    public void testRegisterExistingUser() throws Exception {

        this.userRegistrationService.register(new UserRegistration("alice@nowhere.tld", "secR3Tlf993"), userActivationUriComponents);
    }

    @Test
    public void testRegisterNewUser() throws Exception {
        final String email = "newuser134@nowhere.tld";
        final String expectedToken = this.userRegistrationService.register(new UserRegistration(email, "secR3Tlf993"), userActivationUriComponents);
        final String actualToken = this.userDao.findRegistrationTokenByUser(email);
        assertEquals(expectedToken, actualToken);
        assertFalse(this.userDao.getActiveStateByUser(email));
    }
}
