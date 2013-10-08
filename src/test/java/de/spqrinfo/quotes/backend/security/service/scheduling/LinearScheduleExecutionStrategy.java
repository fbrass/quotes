package de.spqrinfo.quotes.backend.security.service.scheduling;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

/**
* Linear scheduling execution strategy that always executes the schedule independent of any conditions.
*/
public class LinearScheduleExecutionStrategy implements ScheduleExecuteStrategy {

    private static final Logger log = LoggerFactory.getLogger(LinearScheduleExecutionStrategy.class);

    @Override
    public boolean isSchedulingAttemptDesired(final int attempt, final Date lastAttempt) {
        log.debug("Scheduling attempt will be granted");
        return true;
    }
}
