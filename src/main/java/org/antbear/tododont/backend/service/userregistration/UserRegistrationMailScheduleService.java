package org.antbear.tododont.backend.service.userregistration;

import org.antbear.tododont.backend.dao.UserDao;
import org.antbear.tododont.backend.dao.UserRegistrationMailScheduleDao;
import org.antbear.tododont.backend.entity.UserRegistrationMailSchedule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class UserRegistrationMailScheduleService {

    private static final Logger log = LoggerFactory.getLogger(UserRegistrationMailScheduleService.class);

    @Autowired
    private UserDao userDao;

    @Autowired
    private UserRegistrationMailScheduleDao userRegistrationMailScheduleDao;

    @Autowired
    private UserRegistrationMail concreteMail;

    @Autowired
    private UserRegistrationMailSender mailSender;

    private int maxAttempts = 10;

    public int getMaxAttempts() {
        return maxAttempts;
    }

    public void setMaxAttempts(final int maxAttempts) {
        this.maxAttempts = maxAttempts;
    }

    @Scheduled(fixedDelay = 120000) // TODO unrealistic, better use exponential back off
    public void processSchedule() {
        log.debug("processSchedule");

        boolean success;
        for (final UserRegistrationMailSchedule srm : this.userRegistrationMailScheduleDao.findAll()) {

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
                    this.userRegistrationMailScheduleDao.delete(srm.getPK());
                } else {
                    if (srm.getAttempts() < maxAttempts) {
                        this.userRegistrationMailScheduleDao.update(srm);
                    } else {
                        log.warn("Max attempts of scheduled registration mail reached, deleting user {}", srm.getEmail());
                        // scheduled task will be deleted by dropping user
                        this.userDao.delete(srm.getEmail());
                    }
                }
            }
        }
    }

    public void processRegistrationMail(final UserRegistrationMailSchedule userRegistrationMailSchedule) {
        this.concreteMail.setEmail(userRegistrationMailSchedule.getEmail());
        this.concreteMail.setActivationUrl(userRegistrationMailSchedule.getActivationUrl());

        log.debug("Scheduled sending registration email to {}", userRegistrationMailSchedule.getEmail());
        this.mailSender.sendRegistration(this.concreteMail);
    }
}
