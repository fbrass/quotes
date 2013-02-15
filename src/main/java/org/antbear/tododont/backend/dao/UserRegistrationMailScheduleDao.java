package org.antbear.tododont.backend.dao;

import com.google.common.collect.ImmutableMap;
import org.antbear.tododont.backend.entity.UserRegistrationMailSchedule;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.ResultSet;
import java.sql.SQLException;

@Transactional
@Repository
public class UserRegistrationMailScheduleDao extends GenericDao<UserRegistrationMailSchedule, Long> {

    public UserRegistrationMailScheduleDao() {
        super("id", "scheduled_registration_mail");
    }

    public void update(final UserRegistrationMailSchedule srm) {
        getJdbcTemplate().update("UPDATE " + getTableName() + " SET attempts = :attempts, lastAttempt = :lastAttempt WHERE id = :id",
                ImmutableMap.of("attempts", srm.getAttempts(),
                        "lastAttempt", srm.getLastAttempt(),
                        "id", srm.getPK()));
    }

    public void create(final String email, final String activationUrl) {
        getJdbcTemplate().update("INSERT INTO " + getTableName() + " (email, activationurl) VALUES (:email, :activationurl)",
                ImmutableMap.of("email", email,
                        "activationurl", activationUrl));
    }

    @Override
    protected RowMapper<UserRegistrationMailSchedule> newRowMapper() {
        return new RowMapper<UserRegistrationMailSchedule>() {
            @Override
            public UserRegistrationMailSchedule mapRow(final ResultSet rs, final int rowNum) throws SQLException {
                final UserRegistrationMailSchedule sched = new UserRegistrationMailSchedule();
                sched.setPK(rs.getLong(1));
                sched.setEmail(rs.getString(2));
                sched.setActivationUrl(rs.getString(3));
                sched.setAttempts(rs.getInt(4));
                sched.setFirstAttempt(rs.getTimestamp(5));
                sched.setLastAttempt(rs.getTimestamp(6));
                return sched;
            }
        };
    }
}
