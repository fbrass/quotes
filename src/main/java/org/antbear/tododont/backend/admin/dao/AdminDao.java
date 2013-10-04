package org.antbear.tododont.backend.admin.dao;

import org.antbear.tododont.backend.admin.entity.CustomUser;
import org.antbear.tododont.backend.dao.GenericDao;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

@Transactional(propagation = Propagation.REQUIRED)
@Repository
public class AdminDao extends GenericDao<CustomUser, String> {

    protected AdminDao() {
        super("email", "users");
    }

    @Override
    protected RowMapper<CustomUser> newRowMapper() {
        return new CustomUserRowMapper();
    }

    private static class CustomUserRowMapper implements RowMapper<CustomUser> {

        @Override
        public CustomUser mapRow(ResultSet rs, int i) throws SQLException {
            final CustomUser u = new CustomUser();
            u.setPK(rs.getString(1));
            u.setEnabled(rs.getBoolean(3));
            final String registrationToken = rs.getString(4);
            final boolean hasRegistrationToken = !rs.wasNull() && registrationToken != null && !registrationToken.isEmpty();
            u.setHasRegistrationToken(hasRegistrationToken);
            final Date registeredSince = rs.getDate(5);
            u.setRegisteredSince(registeredSince);
            final String passwordResetToken = rs.getString(6);
            final boolean hasPasswordResetToken = !rs.wasNull() && passwordResetToken != null && !passwordResetToken.isEmpty();
            u.setHasPasswordResetToken(hasPasswordResetToken);
            final Date passwordResetRequestedAt = rs.getDate(7);
            u.setPasswordResetRequestedAt(passwordResetRequestedAt);
            return u;
        }
    }
}
