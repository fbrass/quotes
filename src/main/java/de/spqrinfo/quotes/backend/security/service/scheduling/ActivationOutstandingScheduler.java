package de.spqrinfo.quotes.backend.security.service.scheduling;

import de.spqrinfo.quotes.backend.security.entity.CustomUserDetails;
import de.spqrinfo.quotes.backend.security.dao.CustomUserDetailsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Deletes user accounts which have not been activated for too long.
 */
@Service
public class ActivationOutstandingScheduler {

    private static final Logger log = LoggerFactory.getLogger(ActivationOutstandingScheduler.class);

    private static final int ACTIVATION_TIMEOUT_HOURS = 3;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Transactional
    @Scheduled(fixedDelay = 15 * 60 * 1000) // Every 15 minutes
    public void onSchedule() {
        final GregorianCalendar now = new GregorianCalendar();

        for (final UserDetails userDetails : this.userDetailsService.loadUsersWithMissingActivation()) {
            final CustomUserDetails user = (CustomUserDetails) userDetails;
            if (!user.isEnabled() && null != user.getRegistrationToken()) {
                final GregorianCalendar registeredSince = new GregorianCalendar();
                registeredSince.setTime(user.getRegisteredSince());

                registeredSince.add(Calendar.HOUR_OF_DAY, ACTIVATION_TIMEOUT_HOURS);
                if (registeredSince.compareTo(now) < 0) {
                    log.info("User {} hasn't been activated for too long {} h, deleting user",
                            user.getUsername(), ACTIVATION_TIMEOUT_HOURS);
                    this.userDetailsService.deleteUser(user.getUsername());
                }
            }
        }
    }
}
