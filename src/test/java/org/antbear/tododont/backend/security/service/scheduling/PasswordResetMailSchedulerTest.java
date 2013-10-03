package org.antbear.tododont.backend.security.service.scheduling;

import org.antbear.tododont.backend.security.beans.PasswordResetAttempt;
import org.antbear.tododont.backend.security.dao.PasswordResetMailScheduleDao;
import org.antbear.tododont.backend.security.entity.SecurityTokenMailSchedule;
import org.antbear.tododont.backend.security.service.PasswordResetService;
import org.antbear.tododont.web.controller.security.PasswordResetController;
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

        for (int n = 1; n < passwordResetMailScheduler.getMaxAttempts(); n++) {
            passwordResetMailScheduler.onSchedule();
        }

        assertThat(passwordResetMailScheduleDao.findAll().size(), is(equalTo(0)));
    }
}
