package org.antbear.tododont.web.controller.registration;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.validation.BindingResult;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:/test-context.xml")
public class UserRegistrationControllerTest {

    @Autowired
    private UserRegistrationController userRegistrationController;



    // mocked
    private BindingResult bindingResult;

    @Before
    public void setup() {
        this.bindingResult = createMock(BindingResult.class);
    }

    @Test
    public void testPerformResitration() throws Exception {

        final UserRegistration userRegistration = new UserRegistration();
        userRegistration.setEmail("new-test-user@nowhere.tld");
        userRegistration.setPassword("pr3TtYS3c0R3");

        // create mock for springs BindingResult
        expect(this.bindingResult.hasErrors()).andReturn(false);
        // activate mock
        replay(this.bindingResult);

        // test user registration from controller
        final String ignored = this.userRegistrationController.performResitration(userRegistration, this.bindingResult);



    }
}
