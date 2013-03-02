package org.antbear.tododont.backend.security.service.scheduling;

import java.util.Date;

/**
 * Determines if the time has reached to perform a scheduled task.
 */
public interface ScheduleExecuteStrategy {

    /**
     * Predicate that checks based upon the current number of the attempt and the last time an attempt was made,
     * if the scheduled task should be performed.
     *
     * @param attempt the number indicating how often the attempt has been made
     * @param lastAttempt the date and time when the last attempt was made
     * @return true if the scheduled task should be performed, otherwise false
     */
    boolean isSchedulingAttemptDesired(int attempt, Date lastAttempt);
}
