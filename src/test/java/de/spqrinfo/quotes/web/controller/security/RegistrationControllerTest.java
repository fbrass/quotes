package de.spqrinfo.quotes.web.controller.security;

import de.spqrinfo.quotes.backend.security.beans.Registration;
import de.spqrinfo.quotes.backend.security.dao.CustomUserDetailsService;
import de.spqrinfo.quotes.backend.security.service.SecurityMail;
import de.spqrinfo.quotes.backend.security.service.SecurityMailSenderNullTestSupport;
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
        assertEquals(email.toLowerCase(), registrationMail.getEmail());
        final String activationUrl = registrationMail.getUrl();
        assertNotNull(activationUrl);

        final String decodedUrl = URLDecoder.decode(activationUrl, "utf-8");
        final String activationToken = decodedUrl.replaceFirst("^http://.*"
                + RegistrationController.getActivationUriPath() + "(.*)", "$1");

        // Perform the activation
        this.registrationController.performActivation(activationToken);
        final boolean activeStateByUser = this.userDetailsService.loadUserByUsername(email).isEnabled();
        assertTrue(activeStateByUser);
    }
}
