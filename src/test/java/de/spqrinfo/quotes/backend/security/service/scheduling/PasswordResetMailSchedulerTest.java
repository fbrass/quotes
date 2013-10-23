package de.spqrinfo.quotes.backend.security.service.scheduling;

import de.spqrinfo.quotes.backend.security.beans.PasswordResetAttempt;
import de.spqrinfo.quotes.backend.security.dao.PasswordResetMailScheduleDao;
import de.spqrinfo.quotes.backend.security.entity.SecurityTokenMailSchedule;
import de.spqrinfo.quotes.backend.security.service.PasswordResetService;
import de.spqrinfo.quotes.web.controller.security.PasswordResetController;
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
public class PasswordResetMailSchedulerTest {

    //private static final Logger log = LoggerFactory.getLogger(PasswordResetMailSchedulerTest.class);

    @Autowired
    private PasswordResetService passwordResetService;

    @Autowired
    private PasswordResetController passwordResetController;

    @Autowired
    private PasswordResetMailScheduleDao passwordResetMailScheduleDao;

    @Autowired
    private PasswordResetMailScheduler passwordResetMailScheduler;

    @Test
    public void onSchedule() throws Exception {
        this.passwordResetService.passwordResetAttempt(new PasswordResetAttempt("alice@nowhere.tld"),
                this.passwordResetController.getPasswordResetUriComponents());
        final List<SecurityTokenMailSchedule> passwordResetMailSchedules = this.passwordResetMailScheduleDao.findAll();
        assertThat(passwordResetMailSchedules.size(), equalTo(1));

        for (int n = 1; n < this.passwordResetMailScheduler.getMaxAttempts(); n++) {
            this.passwordResetMailScheduler.onSchedule();
        }

        assertThat(this.passwordResetMailScheduleDao.findAll().size(), is(equalTo(0)));
    }
}
