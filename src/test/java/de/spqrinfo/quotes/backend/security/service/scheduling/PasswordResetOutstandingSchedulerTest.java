package de.spqrinfo.quotes.backend.security.service.scheduling;

import de.spqrinfo.quotes.backend.security.dao.CustomUserDetailsService;
import de.spqrinfo.quotes.backend.security.entity.CustomUserDetails;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.GregorianCalendar;

import static org.junit.Assert.*;

@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/test-base-context.xml", "classpath:/test-mail-null-context.xml"})
public class PasswordResetOutstandingSchedulerTest {

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Autowired
    private PasswordResetOutstandingScheduler passwordResetOutstandingScheduler;

    @Test
    public void onScheduleTest() throws Exception {
        final GregorianCalendar past = new GregorianCalendar();
        past.add(Calendar.HOUR_OF_DAY, -6);

        final CustomUserDetails user = new CustomUserDetails("ignored@nowhere.tld", "ignored-token");
        assertFalse(user.isEnabled());
        user.setPassword("secretPassword@IGNORED");
        user.setRegisteredSince(past.getTime());
        this.userDetailsService.createUser(user);
        this.userDetailsService.enableUser(user.getUsername());
        this.userDetailsService.updatePasswordResetToken(user.getUsername(), "a-password-reset-token", past.getTime());

        this.passwordResetOutstandingScheduler.onSchedule();

        final UserDetails userDetails = this.userDetailsService.loadUserByUsername(user.getUsername());
        final CustomUserDetails customUserDetails = (CustomUserDetails) userDetails;

        assertNull(customUserDetails.getPasswordResetToken());
    }

    @Test
    public void onScheduleDoNotClearTest() throws Exception {
        final CustomUserDetails user = new CustomUserDetails("ignored@nowhere.tld", "ignored-token");
        assertFalse(user.isEnabled());
        user.setPassword("secretPassword@IGNORED");
        this.userDetailsService.createUser(user);
        this.userDetailsService.enableUser(user.getUsername());
        this.userDetailsService.updatePasswordResetToken(user.getUsername(), "a-password-reset-token");

        this.passwordResetOutstandingScheduler.onSchedule();

        final UserDetails userDetails = this.userDetailsService.loadUserByUsername(user.getUsername());
        final CustomUserDetails customUserDetails = (CustomUserDetails) userDetails;

        assertNotNull(customUserDetails.getPasswordResetToken());
    }
}
