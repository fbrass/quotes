package org.antbear.tododont.backend.dao;

import com.google.common.collect.ImmutableMap;
import org.antbear.tododont.backend.entity.UserRegistrationMailSchedule;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
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
                final Long pk = rs.getLong(1);
                final String email = rs.getString(2);
                final String activationUrl = rs.getString(3);
                final int attempts = rs.getInt(4);
                final Date firstAttempt = rs.getDate(5);
                final Date lastAttempt = rs.getDate(6);

                final UserRegistrationMailSchedule userRegistrationMailSchedule = new UserRegistrationMailSchedule();
                userRegistrationMailSchedule.setPK(pk);
                userRegistrationMailSchedule.setEmail(email);
                userRegistrationMailSchedule.setActivationUrl(activationUrl);
                userRegistrationMailSchedule.setAttempts(attempts);
                userRegistrationMailSchedule.setFirstAttempt(firstAttempt);
                userRegistrationMailSchedule.setLastAttempt(lastAttempt);
                return userRegistrationMailSchedule;
            }
        };
    }
}
