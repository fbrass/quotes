package de.spqrinfo.quotes.backend.security.service.scheduling;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;

public class ExponentialScheduleExecuteStrategy implements ScheduleExecuteStrategy {

    private static final Logger log = LoggerFactory.getLogger(ExponentialScheduleExecuteStrategy.class);

    // See corresponding test to get an idea how it works
    @Override
    public boolean isSchedulingAttemptDesired(final int attempt, final Date lastAttempt) {
        if (attempt < 1) {
            throw new IllegalArgumentException("Attempt must be > 0");
        }

        final Calendar now = Calendar.getInstance();
        final Calendar ts = Calendar.getInstance();
        ts.setTime(lastAttempt);

        return isExponentiallyAfter(attempt, ts, now);
    }

    public static boolean isExponentiallyAfter(final int n, final Calendar pointInTime, final Calendar compare) {
        if (n < 1) {
            throw new IllegalArgumentException("Attempt must be > 0");
        }
        final int delta = pow(2, n);
        final Calendar ts = (Calendar) pointInTime.clone();
        ts.add(Calendar.MINUTE, delta);

        if (log.isDebugEnabled()) {
            final DateFormat df = DateFormat.getDateTimeInstance();
            log.debug("Comparing n={} point {} => {} - with {}", n,
                    df.format(pointInTime.getTime()), df.format(ts.getTime()), df.format(compare.getTime()));
        }
        return compare.after(ts);
    }

    public static int pow(int base, int exp) {
        int result = 1;
        while (exp != 0) {
            if ((exp & 1) != 0)
                result *= base;
            exp >>= 1;
            base *= base;
        }
        return result;
    }
}
