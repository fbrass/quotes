package org.antbear.tododont.backend.security.scheduling;

import org.antbear.tododont.backend.security.dao.CustomUserDetailsService;
import org.antbear.tododont.backend.security.entity.CustomUserDetails;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Calendar;
import java.util.GregorianCalendar;

import static org.junit.Assert.assertFalse;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:/test-context.xml")
public class ActivationOutstandingSchedulerTest {

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Autowired
    private ActivationOutstandingScheduler activationOutstandingScheduler;

    @Test(expected = UsernameNotFoundException.class)
    public void testOnSchedule() throws Exception {
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
}
