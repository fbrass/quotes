package org.antbear.tododont.backend.service.userregistration;

import org.antbear.tododont.backend.dao.UserDao;
import org.antbear.tododont.backend.dao.UserRegistrationMailScheduleDao;
import org.antbear.tododont.backend.entity.UserRegistrationMailSchedule;
import org.antbear.tododont.web.beans.UserRegistration;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:/test-context.xml")
public class UserRegistrationMailScheduleServiceTest {

    private static final Logger log = LoggerFactory.getLogger(UserRegistrationMailScheduleServiceTest.class);

    @Autowired
    private UserDao userDao;

    @Autowired
    private UserRegistrationService userRegistrationService;

    @Autowired
    private UserRegistrationMailScheduleDao userRegistrationMailScheduleDao;

    @Autowired
    private UserRegistrationMailScheduleService userRegistrationMailScheduleService;

    private UriComponents userActivationUriComponents = UriComponentsBuilder.fromUriString(
            "http://localhost:8080/register/activate-user/{activationToken}").build();

    public static final String EMAIL = "some-new-unkn0wn-user@nowhere.tld";

    @Test
    @DirtiesContext
    public void testProcessSchedule() throws Exception {
        // Wire failing user registration mail sender into user registration service
        final UserRegistrationMailSenderFailTestSupport senderFailTestSupport = new UserRegistrationMailSenderFailTestSupport();
        this.userRegistrationService.setUserRegistrationMailSender(senderFailTestSupport);

        this.userRegistrationService.register(new UserRegistration(EMAIL, "secR3Tlf993"), userActivationUriComponents);
        final List<UserRegistrationMailSchedule> userRegistrationMailSchedules = this.userRegistrationMailScheduleDao.findAll();
        assertThat(userRegistrationMailSchedules.size(), equalTo(1));

        for (int n = 1; n < userRegistrationMailScheduleService.getMaxAttempts(); n++) {
            userRegistrationMailScheduleService.processSchedule();
        }

        assertThat(userRegistrationMailScheduleDao.findAll().size(), is(equalTo(0)));
    }
}
