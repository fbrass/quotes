package de.spqrinfo.quotes.backend.security.dao;

import de.spqrinfo.quotes.backend.security.entity.CustomUserDetails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.userdetails.jdbc.JdbcDaoImpl;
import org.springframework.transaction.annotation.Transactional;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Transactional(readOnly = true)
public class CustomUserDetailsServiceImpl extends JdbcDaoImpl implements CustomUserDetailsService {

    private static final Logger log = LoggerFactory.getLogger(CustomUserDetailsServiceImpl.class);

    public CustomUserDetailsServiceImpl() {
        super.setAuthoritiesByUsernameQuery("SELECT user_id, authority FROM authority WHERE user_id = ?");
        super.setEnableAuthorities(true);
        super.setUsernameBasedPrimaryKey(true);
    }

    // --- Custom methods


    @Override
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
        final String sql = "SELECT * FROM user WHERE user_id = ?";
        try {
            return getJdbcTemplate().queryForObject(sql, new CustomUserDetailsRowMapper(), username.toLowerCase());
        } catch (EmptyResultDataAccessException ignored) {
            // Not logging because it is no error and will irritate unit tests (it looks like an error).
            //log.warn("User not found {}", username, ignored);
            throw new UsernameNotFoundException("User not found");
        }
    }

    @Override
    public boolean isExistingUser(final String userId) {
        final String sql = "SELECT COUNT(*) FROM user WHERE user_id = ?";
        final Integer count = getJdbcTemplate().queryForObject(sql, Integer.class, userId.toLowerCase());
        return count != null && count > 0;
    }

    @Override
    @Transactional(readOnly = false)
    public void createUser(final CustomUserDetails user) {
        getJdbcTemplate().update(
                "INSERT INTO user (user_id,password,enabled,registrationtoken,registered_since) VALUES (?,?,?,?,?)",
                user.getUsername().toLowerCase(), user.getPassword(),
                user.isEnabled(), user.getRegistrationToken(),
                user.getRegisteredSince());

        getJdbcTemplate().update(
                "INSERT INTO authority (user_id, authority) VALUES (?, ?)",
                user.getUsername().toLowerCase(), "ROLE_USER");
    }

    @Override
    public CustomUserDetails loadUserByRegistrationToken(final String registrationToken) {
        log.debug("loadUserByRegistrationToken {}", registrationToken);
        final String query = "SELECT user_id,password,enabled,registrationtoken,registered_since,passwordresettoken,passwordreset_requested_at"
                + " FROM user WHERE registrationtoken = ?";

        final List<UserDetails> userDetailsList = super.getJdbcTemplate().query(query,
                new CustomUserDetailsRowMapper(), registrationToken);

        if (null == userDetailsList || userDetailsList.isEmpty()) {
            return null;
        }
        return (CustomUserDetails) userDetailsList.get(0);
    }

    @Override
    public List<UserDetails> loadUsersWithMissingActivation() {
        return super.getJdbcTemplate().query("SELECT"
                + " user_id,password,enabled,registrationtoken,registered_since,passwordresettoken,passwordreset_requested_at"
                + " FROM user WHERE enabled = 0 AND registrationtoken IS NOT NULL",
                new CustomUserDetailsRowMapper());
    }

    @Override
    public List<UserDetails> loadUsersWithMissingPasswordReset() {
        return super.getJdbcTemplate().query("SELECT"
                + " user_id,password,enabled,registrationtoken,registered_since,passwordresettoken,passwordreset_requested_at"
                + " FROM user WHERE enabled = 1 AND passwordresettoken IS NOT NULL",
                new CustomUserDetailsRowMapper());
    }

    @Override
    @Transactional(readOnly = false)
    public void enableUser(final String userId) {
        getJdbcTemplate().update("UPDATE user SET enabled = 1, registrationtoken = NULL WHERE user_id = ?",
                userId.toLowerCase());
    }

    @Override
    public void clearPasswordResetToken(final String userId) {
        updatePasswordResetToken(userId, null, null);
    }

    @Override
    @Transactional(readOnly = false)
    public void updatePasswordResetToken(final String userId, final String passwordResetToken) {
        updatePasswordResetToken(userId, passwordResetToken, new Date());
    }

    @Override
    @Transactional(readOnly = false)
    public void updatePasswordResetToken(final String userId, final String passwordResetToken,
                                         final Date passwordResetRequestedAt) {
        getJdbcTemplate().update("UPDATE user SET passwordresettoken = ?, passwordreset_requested_at = ? WHERE user_id = ?", passwordResetToken, passwordResetRequestedAt, userId.toLowerCase());
    }

    @Override
    @Transactional(readOnly = false)
    public void updatePassword(final CustomUserDetails user) {
        getJdbcTemplate().update("UPDATE user SET password = ?, passwordresettoken = NULL, passwordreset_requested_at = NULL WHERE user_id = ?",
                user.getPassword(), user.getUsername().toLowerCase());
    }

    @Override
    @Transactional(readOnly = false)
    public void deleteUser(final String userId) {
        getJdbcTemplate().update("DELETE FROM user WHERE user_id = ?", userId.toLowerCase());
    }

    // --- JdbcDaoImpl overrides

    @Override
    protected List<UserDetails> loadUsersByUsername(final String username) {
        log.debug("loadUsersByUsername {}", username.toLowerCase());
        final String query = "SELECT user_id,password,enabled,registrationtoken,registered_since,passwordresettoken,passwordreset_requested_at"
                + " FROM user WHERE user_id = ?";

        return super.getJdbcTemplate().query(query, new CustomUserDetailsRowMapper(), username.toLowerCase());
    }

    @Override
    protected UserDetails createUserDetails(final String username, final UserDetails userFromUserQuery,
                                            final List<GrantedAuthority> combinedAuthorities) {
        final CustomUserDetails user = (CustomUserDetails) userFromUserQuery;

        final Set<GrantedAuthority> grantedAuthorities = new HashSet<GrantedAuthority>();
        grantedAuthorities.addAll(combinedAuthorities);

        return new CustomUserDetails(username.toLowerCase(),
                user.getPassword(),
                user.isEnabled(),
                grantedAuthorities,
                user.getRegistrationToken(),
                user.getRegisteredSince(),
                user.getPasswordResetToken(),
                user.getPasswordResetRequestedAt(),
                user.isAccountNonExpired(),
                user.isAccountNonLocked(),
                user.isCredentialsNonExpired());
    }

    private static class CustomUserDetailsRowMapper implements RowMapper<UserDetails> {

        @Override
        public CustomUserDetails mapRow(final ResultSet rs, final int rowNum) throws SQLException {
            int n = 1;
            final String userId = rs.getString(n++).toLowerCase(); // userId
            final String password = rs.getString(n++);
            final boolean enabled = rs.getBoolean(n++);
            final String registrationToken = rs.getString(n++);
            final Date registeredSince = rs.getTimestamp(n++);
            final String passwordResetToken = rs.getString(n++);
            final Date passwordResetRequestedAt = rs.getTimestamp(n);

            return new CustomUserDetails(userId,
                    password,
                    enabled,
                    new HashSet<GrantedAuthority>(),
                    registrationToken,
                    registeredSince,
                    passwordResetToken,
                    passwordResetRequestedAt,
                    true, true, true);
        }
    }
}
