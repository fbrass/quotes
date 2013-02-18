package org.antbear.tododont.backend.service.security;

import org.antbear.tododont.backend.dao.RegistrationMailScheduleDao;
import org.antbear.tododont.backend.dao.UserDao;
import org.antbear.tododont.backend.entity.SecurityTokenMailSchedule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class RegistrationMailScheduler {

    private static final Logger log = LoggerFactory.getLogger(RegistrationMailScheduler.class);

    @Autowired
    private UserDao userDao;

    @Autowired
    private RegistrationMailScheduleDao registrationMailScheduleDao;

    @Autowired
    private RegistrationMail registrationMail;

    @Autowired
    private SecurityMailSender mailSender;

    private int maxAttempts = 10;

    public int getMaxAttempts() {
        return maxAttempts;
    }

    public void setMaxAttempts(final int maxAttempts) {
        this.maxAttempts = maxAttempts;
    }

    @Scheduled(fixedDelay = 120000) // TODO unrealistic, better use exponential back off
    public void onSchedule() {
        boolean success;
        for (final SecurityTokenMailSchedule srm : this.registrationMailScheduleDao.findAll()) {
            log.debug("Processing scheduled registration mail {}", srm);

            success = false;
            try {
                srm.setAttempts(1 + srm.getAttempts());
                srm.setLastAttempt(new Date());
                processRegistrationMail(srm);
                success = true;
            } catch (Exception ex) {
                log.warn("Failed processing scheduled registration mail " + srm, ex);
            } finally {
                if (success) {
                    log.info("Done sending registration to mail to {}", srm.getEmail());
                    this.registrationMailScheduleDao.delete(srm.getPK());
                } else {
                    if (srm.getAttempts() < maxAttempts) {
                        this.registrationMailScheduleDao.update(srm);
                    } else {
                        log.warn("Max attempts of scheduled registration mail reached, deleting user {}", srm.getEmail());
                        // scheduled task will be deleted by dropping user
                        this.userDao.delete(srm.getEmail());
                    }
                }
            }
        }
    }

    public void processRegistrationMail(final SecurityTokenMailSchedule securityTokenMailSchedule) {
        this.registrationMail.setEmail(securityTokenMailSchedule.getEmail());
        this.registrationMail.setUrl(securityTokenMailSchedule.getUrl());

        log.debug("Scheduled sending registration email to {}", securityTokenMailSchedule.getEmail());
        this.mailSender.send(this.registrationMail);
    }
}
