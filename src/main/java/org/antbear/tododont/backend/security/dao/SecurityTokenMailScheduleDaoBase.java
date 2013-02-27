package org.antbear.tododont.backend.security.dao;

import com.google.common.collect.ImmutableMap;
import org.antbear.tododont.backend.dataaccess.GenericDao;
import org.antbear.tododont.backend.security.entity.SecurityTokenMailSchedule;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.NotNull;
import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public abstract class SecurityTokenMailScheduleDaoBase extends GenericDao<SecurityTokenMailSchedule, Long> {

    protected SecurityTokenMailScheduleDaoBase(@NotNull final String pkColumnName, @NotNull final String tableName) {
        super(pkColumnName, tableName);
    }

    public void update(final SecurityTokenMailSchedule securityTokenMailSchedule) {
        getJdbcTemplate().update(
                "UPDATE " + getTableName() + " SET attempts = :attempts, lastAttempt = :lastAttempt WHERE id = :id",
                ImmutableMap.of("attempts", securityTokenMailSchedule.getAttempts(),
                        "lastAttempt", securityTokenMailSchedule.getLastAttempt(),
                        "id", securityTokenMailSchedule.getPK()));
    }

    public void createSchedule(final String email, final String url) {
        getJdbcTemplate().update("INSERT INTO " + getTableName() + " (email, url) VALUES (:email, :url)",
                ImmutableMap.of("email", email,
                        "url", url));
    }

    @Override
    protected RowMapper<SecurityTokenMailSchedule> newRowMapper() {
        return new RowMapper<SecurityTokenMailSchedule>() {
            @Override
            public SecurityTokenMailSchedule mapRow(final ResultSet rs, final int rowNum) throws SQLException {
                final SecurityTokenMailSchedule sched = new SecurityTokenMailSchedule();
                sched.setPK(rs.getLong(1));
                sched.setEmail(rs.getString(2));
                sched.setUrl(rs.getString(3));
                sched.setAttempts(rs.getInt(4));
                sched.setFirstAttempt(rs.getTimestamp(5));
                sched.setLastAttempt(rs.getTimestamp(6));
                return sched;
            }
        };
    }
}
