package org.antbear.tododont.web.security.controller;

import org.antbear.tododont.backend.security.dao.UserDao;
import org.antbear.tododont.backend.security.service.SecurityMail;
import org.antbear.tododont.backend.security.service.SecurityMailSenderTestSupport;
import org.antbear.tododont.web.security.beans.Registration;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.validation.BindingResult;

import java.net.URLDecoder;

import static org.easymock.EasyMock.*;
import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:/test-context.xml")
public class RegistrationControllerTest {

    @Autowired
    private RegistrationController registrationController;

    @Autowired
    private SecurityMailSenderTestSupport userRegistrationMailSender;

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
        final Registration registration = new Registration();
        registration.setEmail(email);
        registration.setPassword("pr3TtYS3c0R3");

        // createSchedule mock for springs BindingResult
        expect(this.bindingResult.hasErrors()).andReturn(false);
        // activate mock
        replay(this.bindingResult);

        // test user registration from controller
        this.registrationController.performRegistration(registration, this.bindingResult);

        // A mail should have been sent
        final SecurityMail registrationMail = this.userRegistrationMailSender.getSecurityMail();
        assertNotNull(registrationMail);
        assertEquals(email, registrationMail.getEmail());
        final String activationUrl = registrationMail.getUrl();
        assertNotNull(activationUrl);

        final String decodedUrl = URLDecoder.decode(activationUrl, "utf-8");
        final String emailAndToken = decodedUrl.replaceFirst("^http://.*" + RegistrationController.ACTIVATION_PATH + "(.*)", "$1");
        final String[] strings = emailAndToken.split("/", 2);
        final String activationEmail = strings[0], activationToken = strings[1];

        // Perform the activation
        this.registrationController.performActivation(activationEmail, activationToken);
        final boolean activeStateByUser = this.userDao.getActiveStateByUser(email);
        assertTrue(activeStateByUser);
    }
}
