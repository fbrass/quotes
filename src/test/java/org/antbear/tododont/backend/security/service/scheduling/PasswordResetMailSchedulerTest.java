package org.antbear.tododont.backend.security.service.scheduling;

import org.antbear.tododont.backend.security.beans.PasswordResetAttempt;
import org.antbear.tododont.backend.security.dao.PasswordResetMailScheduleDao;
import org.antbear.tododont.backend.security.entity.SecurityTokenMailSchedule;
import org.antbear.tododont.backend.security.service.PasswordResetService;
import org.antbear.tododont.web.controller.security.PasswordResetController;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@Transactional
@Ignore
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:/test-context.xml")
public class PasswordResetMailSchedulerTest {

    private static final Logger log = LoggerFactory.getLogger(PasswordResetMailSchedulerTest.class);

    @Autowired
    private PasswordResetService passwordResetService;

    @Autowired
    private PasswordResetController passwordResetController;

    @Autowired
    private PasswordResetMailScheduleDao passwordResetMailScheduleDao;

    @Autowired
    private PasswordResetMailScheduler passwordResetMailScheduler;

    @Test
    @DirtiesContext
    public void onSchedule() throws Exception {
        // TODO Wire failing user registration mail sender into user registration service
//        final SecurityMailSenderFailingTestSupport mailSenderFailing = new SecurityMailSenderFailingTestSupport();
//        this.passwordResetService.setSecurityMailSender(mailSenderFailing); // @DirtiesContext

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
