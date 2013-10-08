package de.spqrinfo.quotes.backend.security.dao;

import com.google.common.collect.ImmutableMap;
import de.spqrinfo.quotes.backend.dao.GenericDao;
import de.spqrinfo.quotes.backend.security.entity.SecurityTokenMailSchedule;
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
                ImmutableMap.of("email", email.toLowerCase(),
                        "url", url));
    }

    @Override
    protected RowMapper<SecurityTokenMailSchedule> newRowMapper() {
        return new RowMapper<SecurityTokenMailSchedule>() {
            @Override
            public SecurityTokenMailSchedule mapRow(final ResultSet rs, final int rowNum) throws SQLException {
                final SecurityTokenMailSchedule schedule = new SecurityTokenMailSchedule();
                schedule.setPK(rs.getLong(1));
                schedule.setEmail(rs.getString(2));
                schedule.setUrl(rs.getString(3));
                schedule.setAttempts(rs.getInt(4));
                schedule.setFirstAttempt(rs.getTimestamp(5));
                schedule.setLastAttempt(rs.getTimestamp(6));
                return schedule;
            }
        };
    }
}
