package org.antbear.tododont.backend.security.service.scheduling;

import org.antbear.tododont.backend.security.beans.Registration;
import org.antbear.tododont.backend.security.dao.RegistrationMailScheduleDao;
import org.antbear.tododont.backend.security.entity.SecurityTokenMailSchedule;
import org.antbear.tododont.backend.security.service.RegistrationService;
import org.antbear.tododont.web.controller.security.RegistrationController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/test-base-context.xml", "classpath:/test-mail-fail-context.xml"})
public class RegistrationMailSchedulerTest {

    //private static final Logger log = LoggerFactory.getLogger(RegistrationMailSchedulerTest.class);

    @Autowired
    private RegistrationService registrationService;

    @Autowired
    private RegistrationMailScheduleDao registrationMailScheduleDao;

    @Autowired
    private RegistrationMailScheduler registrationMailScheduler;

    @Autowired
    private RegistrationController registrationController;

    public static final String EMAIL = "newUser@nowhere.tld";

    @Test
    public void testProcessSchedule() throws Exception {
        this.registrationService.register(new Registration(EMAIL, "secR3Tlf993"),
                this.registrationController.getActivationUriComponents());
        final List<SecurityTokenMailSchedule> userRegistrationMailSchedules = this.registrationMailScheduleDao.findAll();
        assertThat(userRegistrationMailSchedules.size(), equalTo(1));

        for (int n = 1; n < registrationMailScheduler.getMaxAttempts(); n++) {
            registrationMailScheduler.onSchedule();
        }

        assertThat(registrationMailScheduleDao.findAll().size(), is(equalTo(0)));
    }
}
