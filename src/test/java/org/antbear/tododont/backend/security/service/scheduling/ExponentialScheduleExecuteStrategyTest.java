package org.antbear.tododont.backend.security.service.scheduling;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Calendar;
import java.util.GregorianCalendar;

import static org.antbear.tododont.backend.security.service.scheduling.ExponentialScheduleExecuteStrategy.isExponentiallyAfter;
import static org.antbear.tododont.backend.security.service.scheduling.ExponentialScheduleExecuteStrategy.pow;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

@Ignore
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:/test-context.xml")
public class ExponentialScheduleExecuteStrategyTest {

    @Test
    public void powTest() throws Exception {
        assertThat(pow(2, 0), is(1));
        assertThat(pow(2, 1), is(2));
        assertThat(pow(2, 2), is(4));
        assertThat(pow(2, 3), is(8));
        assertThat(pow(2, 4), is(16));
    }

    public static Calendar january(final int hour, final int minute) {
        return new GregorianCalendar(2013, 1, 1, hour, minute, 0);
    }

    @Test
    public void isExponentiallyBeforeTest() throws Exception {
        // 12:02
        assertThat(isExponentiallyAfter(1, january(12, 0), january(12, 1)), is(false));
        assertThat(isExponentiallyAfter(1, january(12, 0), january(12, 3)), is(true));

        // 12:06
        assertThat(isExponentiallyAfter(2, january(12, 2), january(12, 4)), is(false));
        assertThat(isExponentiallyAfter(2, january(12, 2), january(12, 7)), is(true));

        // 12:14
        assertThat(isExponentiallyAfter(3, january(12, 6), january(12, 13)), is(false));
        assertThat(isExponentiallyAfter(3, january(12, 6), january(12, 15)), is(true));

        // 12:30
        assertThat(isExponentiallyAfter(4, january(12, 14), january(12, 29)), is(false));
        assertThat(isExponentiallyAfter(4, january(12, 14), january(12, 31)), is(true));

        // 13:02
        assertThat(isExponentiallyAfter(5, january(12, 30), january(13, 1)), is(false));
        assertThat(isExponentiallyAfter(5, january(12, 30), january(13, 3)), is(true));

        // 14:06
        assertThat(isExponentiallyAfter(6, january(13, 2), january(14, 5)), is(false));
        assertThat(isExponentiallyAfter(6, january(13, 2), january(14, 7)), is(true));

        // 16:14
        assertThat(isExponentiallyAfter(7, january(14, 6), january(16, 3)), is(false));
        assertThat(isExponentiallyAfter(7, january(14, 6), january(16, 15)), is(true));
    }
}
