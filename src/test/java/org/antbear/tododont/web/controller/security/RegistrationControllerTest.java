package org.antbear.tododont.web.controller.security;

import org.antbear.tododont.backend.security.beans.Registration;
import org.antbear.tododont.backend.security.dao.CustomUserDetailsService;
import org.antbear.tododont.backend.security.entity.CustomUserDetails;
import org.antbear.tododont.backend.security.service.SecurityMail;
import org.antbear.tododont.backend.security.service.SecurityMailSenderNullTestSupport;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;

import java.net.URLDecoder;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/test-base-context.xml", "classpath:/test-mail-null-context.xml"})
public class RegistrationControllerTest {

    @Autowired
    private RegistrationController registrationController;

    @Autowired
    private SecurityMailSenderNullTestSupport securityMailSenderTestSupport;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Test
    public void registrationAndActivationTest() throws Exception {
        final String email = "newUser1234@nowhere.tld";
        final Registration registration = new Registration();
        registration.setEmail(email);
        registration.setPassword("pr3TtYS3c0R3");

        // test user registration from controller
        final BindingResult bindingResult = new BeanPropertyBindingResult(registration, "registration");
        this.registrationController.performRegistration(registration, bindingResult);

        // A mail should have been sent
        final SecurityMail registrationMail = this.securityMailSenderTestSupport.getSecurityMail();
        assertNotNull(registrationMail);
        assertEquals(email, registrationMail.getEmail());
        final String activationUrl = registrationMail.getUrl();
        assertNotNull(activationUrl);

        final String decodedUrl = URLDecoder.decode(activationUrl, "utf-8");
        final String activationToken = decodedUrl.replaceFirst("^http://.*"
                + RegistrationController.getActivationUriPath() + "(.*)", "$1");

        // Perform the activation
        this.registrationController.performActivation(activationToken);
        final boolean activeStateByUser = ((CustomUserDetails) this.userDetailsService.loadUserByUsername(email)).isEnabled();
        assertTrue(activeStateByUser);
    }
}
