package de.spqrinfo.quotes.backend.security.service.scheduling;

import de.spqrinfo.quotes.backend.security.entity.CustomUserDetails;
import de.spqrinfo.quotes.backend.security.dao.CustomUserDetailsService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.GregorianCalendar;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/test-base-context.xml", "classpath:/test-mail-null-context.xml"})
public class ActivationOutstandingSchedulerTest {

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Autowired
    private ActivationOutstandingScheduler activationOutstandingScheduler;

    @Test(expected = UsernameNotFoundException.class)
    public void onScheduleTest() throws Exception {
        final GregorianCalendar past = new GregorianCalendar();
        past.add(Calendar.HOUR_OF_DAY, -6);

        CustomUserDetails user = new CustomUserDetails("ignored@nowhere.tld", "ignored-token");
        assertFalse(user.isEnabled());
        user.setPassword("secretPassword@IGNORED");
        user.setRegisteredSince(past.getTime());
        this.userDetailsService.createUser(user);

        activationOutstandingScheduler.onSchedule();

        // Throws UsernameNotFound because user has been deleted
        this.userDetailsService.loadUserByUsername(user.getUsername());
    }

    @Test
    public void onScheduleDoNotDeleteTest() throws Exception {
        CustomUserDetails user = new CustomUserDetails("ignored@nowhere.tld", "ignored-token");
        assertFalse(user.isEnabled());
        user.setPassword("secretPassword@IGNORED");
        this.userDetailsService.createUser(user);

        activationOutstandingScheduler.onSchedule();

        final UserDetails userDetails = this.userDetailsService.loadUserByUsername(user.getUsername());
        assertNotNull(userDetails);
        assertThat(userDetails.getUsername(), is(user.getUsername()));
    }
}
