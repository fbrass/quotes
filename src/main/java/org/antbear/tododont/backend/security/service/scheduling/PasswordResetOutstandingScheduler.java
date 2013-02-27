package org.antbear.tododont.backend.security.service.scheduling;

import org.antbear.tododont.backend.security.dao.CustomUserDetailsService;
import org.antbear.tododont.backend.security.entity.CustomUserDetails;
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
 * Clears user password reset token if the have not been used for too long.
 */
@Service
public class PasswordResetOutstandingScheduler {

    private static final Logger log = LoggerFactory.getLogger(PasswordResetOutstandingScheduler.class);

    private static final int PASSWORD_RESET_OUTSTANDING_HOURS = 4;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Transactional
    @Scheduled(fixedDelay = 60 * 60 * 1000) // every 60 minutes
    public void onSchedule() {
        final GregorianCalendar now = new GregorianCalendar();

        for (UserDetails userDetails : this.userDetailsService.loadUsersWithMissingPasswordReset()) {
            CustomUserDetails user = (CustomUserDetails) userDetails;
            if (user.isEnabled() && null != user.getPasswordResetToken()) {
                final GregorianCalendar requestedAt = new GregorianCalendar();
                requestedAt.setTime(user.getPasswordResetRequestedAt());

                requestedAt.add(Calendar.HOUR_OF_DAY, PASSWORD_RESET_OUTSTANDING_HOURS);
                if (requestedAt.compareTo(now) < 0) {
                    log.info("User {} has unused password reset token for too long {} h, clearing password reset token",
                            user.getUsername(), PASSWORD_RESET_OUTSTANDING_HOURS);
                    this.userDetailsService.clearPasswordResetToken(user.getUsername());
                }
            }
        }
    }
}
