package org.antbear.tododont.backend.security.dao;

import org.springframework.stereotype.Repository;

@Repository
public class PasswordResetMailScheduleDao extends SecurityTokenMailScheduleDaoBase {

    public PasswordResetMailScheduleDao() {
        super("id", "scheduled_password_reset_mail");
    }
}
