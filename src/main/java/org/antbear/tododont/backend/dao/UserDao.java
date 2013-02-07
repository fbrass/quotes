package org.antbear.tododont.backend.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.jdbc.JdbcDaoImpl;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class UserDao {

    @Autowired
    private JdbcDaoImpl userDetailsService;

    public boolean exists(final String email) {
        final int count = this.userDetailsService.getJdbcTemplate().queryForInt(
                "SELECT COUNT(*) FROM users WHERE email = ?", email);
        return count > 0;
    }

    public void createActiveUser(final String email, final String registrationToken) {
        createUser(email, true, registrationToken);
    }

    public void createInactiveUser(final String email, final String registrationToken) {
        createUser(email, false, registrationToken);
    }

    public void createUser(final String email, final boolean enabled, final String registrationToken) {
        this.userDetailsService.getJdbcTemplate().update(
                "INSERT INTO users (email,password,enabled,registrationtoken) VALUES (?,?,?,?)",
                email, "DISABLED", enabled, registrationToken);
    }

    public String findRegistrationTokenByUser(final String email) {
        final String sql = "SELECT registrationtoken FROM users WHERE email = ? AND enabled = 0";
        final String token = this.userDetailsService.getJdbcTemplate().queryForObject(sql, String.class, email);
        return token;
    }

    public boolean getActiveStateByUser(final String email) {
        final int enabled = this.userDetailsService.getJdbcTemplate().queryForInt("SELECT enabled FROM users WHERE email = ?", email);
        return enabled == 0 ? false : true;
    }
}
