package org.antbear.tododont.backend.dao;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Repository
public class RegistrationMailScheduleDao extends SecurityTokenMailScheduleDaoBase {

    public RegistrationMailScheduleDao() {
        super("id", "scheduled_registration_mail");
    }
}
