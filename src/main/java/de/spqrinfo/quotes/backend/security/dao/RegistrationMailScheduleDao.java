package de.spqrinfo.quotes.backend.security.dao;

import org.springframework.stereotype.Repository;

@Repository
public class RegistrationMailScheduleDao extends SecurityTokenMailScheduleDaoBase {

    public RegistrationMailScheduleDao() {
        super("id", "scheduled_registration_mail");
    }
}
