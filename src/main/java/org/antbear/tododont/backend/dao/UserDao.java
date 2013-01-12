package org.antbear.tododont.backend.dao;

import org.antbear.tododont.backend.entity.User;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class UserDao extends GenericDao<User, Long> {

    @Override
    protected String getTableName() {
        return "user";
    }

    @Override
    protected RowMapper<User> newRowMapper() {
        return new UserRowMapper();
    }

    private static class UserRowMapper implements RowMapper<User> {

        @Override
        public User mapRow(final ResultSet rs, final int rowNum) throws SQLException {
            final long id = rs.getLong(1);
            final String email = rs.getString(2);
            final String cryptedPassword = rs.getString(3);

            final User user = new User();
            user.setId(id);
            user.setEmail(email);
            user.setCryptedPassword(cryptedPassword);
            return user;
        }
    }
}
