package org.antbear.tododont.backend.dao;

import com.google.common.collect.ImmutableMap;
import org.antbear.tododont.backend.entity.ScheduledRegistrationMail;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

@Transactional
@Repository
public class ScheduledRegistrationMailDao extends GenericDao<ScheduledRegistrationMail, Long> {

    public void update(final ScheduledRegistrationMail srm) {
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
    protected String getTableName() {
        return "scheduled_registration_mail";
    }

    @Override
    protected RowMapper<ScheduledRegistrationMail> newRowMapper() {
        return new RowMapper<ScheduledRegistrationMail>() {
            @Override
            public ScheduledRegistrationMail mapRow(final ResultSet rs, final int rowNum) throws SQLException {
                final Long pk = rs.getLong(1);
                final String email = rs.getString(2);
                final String activationUrl = rs.getString(3);
                final int attempts = rs.getInt(4);
                final Date lastAttempt = rs.getDate(5);

                final ScheduledRegistrationMail scheduledRegistrationMail = new ScheduledRegistrationMail();
                scheduledRegistrationMail.setPK(pk);
                scheduledRegistrationMail.setEmail(email);
                scheduledRegistrationMail.setActivationUrl(activationUrl);
                scheduledRegistrationMail.setAttempts(attempts);
                scheduledRegistrationMail.setLastAttempt(lastAttempt);
                return scheduledRegistrationMail;
            }
        };
    }
}
