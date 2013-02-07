package org.antbear.tododont.util;

import com.google.common.collect.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.util.Map;

/**
 * This bean initialized the application.
 * It creates default users and more.
 * @see #afterPropertiesSet()
 */
@Transactional
public class InitialSetup implements InitializingBean {

    private static final Logger log = LoggerFactory.getLogger(InitialSetup.class);

    private NamedParameterJdbcTemplate jdbcTemplate;

    private PasswordEncoder passwordEncoder;

    @Required
    public void setDataSource(final DataSource dataSource) {
        this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    @Required
    public void setPasswordEncoder(final PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        log.debug("Checking if initial setup is required");
        if (noUsers()) {
            log.info("Creating initial useres");
            createDefaultUsers();
        }
    }

    private void createDefaultUsers() {
        final String[][] credentials = new String[][] {
            new String[] {"alice", "secret", "ROLE_USER"},
        };

        for (final String[] userPwd : credentials) {
            final String username = userPwd[0];
            final String password = userPwd[1];
            final String authority = userPwd[2];
            final String passwordEncoded = this.passwordEncoder.encode(password);

            final Map<String, String> params = Maps.newHashMap();
            params.put("username", username);
            params.put("password", passwordEncoded);
            this.jdbcTemplate.update("INSERT INTO users (user, password) values (:username, :password)", params);

            final Map<String, String> params2 = Maps.newHashMap();
            params2.put("username", username);
            params2.put("authority", authority);
            this.jdbcTemplate.update("INSERT INTO authorities (username, authority) values (:username, :authority)", params2);

            log.warn("Created default user '{}' with password '{}'! IT IS RECOMMENDED TO LOGIN AND CHANGE THE PASSWORDS!",
                    username, password);
        }
    }

    private boolean noUsers() {
        return 0 ==  this.jdbcTemplate.queryForInt("SELECT COUNT(*) FROM users", new MapSqlParameterSource());
    }
}
