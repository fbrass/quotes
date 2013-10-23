package de.spqrinfo.quotes.backend.security.service.scheduling;

import de.spqrinfo.quotes.backend.security.beans.Registration;
import de.spqrinfo.quotes.backend.security.dao.RegistrationMailScheduleDao;
import de.spqrinfo.quotes.backend.security.entity.SecurityTokenMailSchedule;
import de.spqrinfo.quotes.backend.security.service.RegistrationService;
import de.spqrinfo.quotes.web.controller.security.RegistrationController;
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

        for (int n = 1; n < this.registrationMailScheduler.getMaxAttempts(); n++) {
            this.registrationMailScheduler.onSchedule();
        }

        assertThat(this.registrationMailScheduleDao.findAll().size(), is(equalTo(0)));
    }
}
