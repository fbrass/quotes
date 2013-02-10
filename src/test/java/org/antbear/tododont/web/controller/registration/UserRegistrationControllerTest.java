package org.antbear.tododont.web.controller.registration;

import org.antbear.tododont.backend.dao.UserDao;
import org.antbear.tododont.backend.service.userregistration.UserRegistrationMail;
import org.antbear.tododont.backend.service.userregistration.UserRegistrationMailSenderTestSupport;
import org.antbear.tododont.web.beans.UserRegistration;
import org.antbear.tododont.web.controller.UserRegistrationController;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.validation.BindingResult;

import java.net.URLDecoder;

import static org.easymock.EasyMock.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:/test-context.xml")
public class UserRegistrationControllerTest {

    @Autowired
    private UserRegistrationController userRegistrationController;

    @Autowired
    private UserRegistrationMailSenderTestSupport userRegistrationMailSender;

    @Autowired
    private UserDao userDao;

    // mocked
    private BindingResult bindingResult;

    @Before
    public void setup() {
        this.bindingResult = createMock(BindingResult.class);
    }

    @Test
    public void testPerformRegistration() throws Exception {
        final String email = "new-test-user@nowhere.tld";
        final UserRegistration userRegistration = new UserRegistration();
        userRegistration.setEmail(email);
        userRegistration.setPassword("pr3TtYS3c0R3");

        // create mock for springs BindingResult
        expect(this.bindingResult.hasErrors()).andReturn(false);
        // activate mock
        replay(this.bindingResult);

        // test user registration from controller
        this.userRegistrationController.performRegistration(userRegistration, this.bindingResult);

        // A mail should have been sent
        final UserRegistrationMail userRegistrationMail = this.userRegistrationMailSender.getUserRegistrationMail();
        assertNotNull(userRegistrationMail);
        assertEquals(email, userRegistrationMail.getEmail());
        final String activationUrl = userRegistrationMail.getActivationUrl();
        assertNotNull(activationUrl);

        final String decodedUrl = URLDecoder.decode(activationUrl, "utf-8");
        final String emailAndToken = decodedUrl.replaceFirst("^http://.*" + UserRegistrationController.ACTIVATION_PATH + "(.*)", "$1");
        final String[] strings = emailAndToken.split("/", 2);
        final String activationEmail = strings[0], activationToken = strings[1];

        // Perform the activation
        this.userRegistrationController.performActivation(activationEmail, activationToken);
        final boolean activeStateByUser = this.userDao.getActiveStateByUser(email);
        assertTrue(activeStateByUser);
    }
}
