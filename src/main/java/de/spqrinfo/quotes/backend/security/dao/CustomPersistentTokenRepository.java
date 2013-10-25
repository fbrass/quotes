package de.spqrinfo.quotes.backend.security.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.security.web.authentication.rememberme.PersistentRememberMeToken;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

/**
 * Custom variant of JdbcTokenRepositoryImpl which uses a 'persistent_login' table instead of 'persistent_logins'.
 */
public class CustomPersistentTokenRepository extends JdbcDaoSupport implements PersistentTokenRepository {

    private static final Logger log = LoggerFactory.getLogger(CustomPersistentTokenRepository.class);

    @Override
    public void createNewToken(final PersistentRememberMeToken token) {
        getJdbcTemplate().update("INSERT INTO persistent_login (user_id, series, token, last_used) VALUES(?,?,?,?)",
                token.getUsername(), token.getSeries(),
                token.getTokenValue(), token.getDate());
    }

    @Override
    public void updateToken(final String series, final String tokenValue, final Date lastUsed) {
        getJdbcTemplate().update("UPDATE persistent_login SET token = ?, last_used = ? WHERE series = ?",
                tokenValue, new Date(), series);
    }

    @Override
    public PersistentRememberMeToken getTokenForSeries(final String seriesId) {
        try {
            return getJdbcTemplate().queryForObject("SELECT user_id,series,token,last_used FROM persistent_login WHERE series = ?",
                    new RowMapper<PersistentRememberMeToken>() {
                        public PersistentRememberMeToken mapRow(final ResultSet rs, final int rowNum) throws SQLException {
                            return new PersistentRememberMeToken(rs.getString(1), rs.getString(2), rs.getString(3), rs.getTimestamp(4));
                        }
                    }, seriesId);
        } catch (EmptyResultDataAccessException zeroResults) {
            if (log.isInfoEnabled()) {
                log.info("Querying token for series '" + seriesId + "' returned no results.", zeroResults);
            }
        } catch (IncorrectResultSizeDataAccessException moreThanOne) {
            log.error("Querying token for series '" + seriesId + "' returned more than one value. Series" +
                    " should be unique");
        } catch (DataAccessException e) {
            log.error("Failed to load token for series " + seriesId, e);
        }

        return null;
    }

    @Override
    public void removeUserTokens(final String username) {
        getJdbcTemplate().update("DELETE FROM persistent_login WHERE user_id = ?", username);
    }
}
