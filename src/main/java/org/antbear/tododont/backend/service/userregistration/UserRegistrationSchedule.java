package org.antbear.tododont.backend.service.userregistration;

import org.antbear.tododont.backend.dao.ScheduledRegistrationMailDao;
import org.antbear.tododont.backend.dao.UserDao;
import org.antbear.tododont.backend.entity.ScheduledRegistrationMail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class UserRegistrationSchedule {

    private static final Logger log = LoggerFactory.getLogger(UserRegistrationSchedule.class);

    @Autowired
    private UserDao userDao;

    @Autowired
    private ScheduledRegistrationMailDao scheduledRegistrationMailDao;

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
        for (final ScheduledRegistrationMail srm : this.scheduledRegistrationMailDao.findAll()) {

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
                    log.info("Done sending registration to mail to {}", srm.getPK());
                    this.scheduledRegistrationMailDao.delete(srm.getPK());
                } else {
                    if (srm.getAttempts() < maxAttempts) {
                        this.scheduledRegistrationMailDao.update(srm);
                    } else {
                        log.warn("Max attempts of scheduled registration mail reached, deleting user {}", srm.getEmail());
                        // scheduled task will be deleted by dropping user
                        this.userDao.delete(srm.getEmail());
                    }
                }
            }
        }
    }

    public void processRegistrationMail(final ScheduledRegistrationMail scheduledRegistrationMail) {
        this.concreteMail.setEmail(scheduledRegistrationMail.getEmail());
        this.concreteMail.setActivationUrl(scheduledRegistrationMail.getActivationUrl());

        log.debug("Scheduled sending registration email to {}", scheduledRegistrationMail.getPK());
        this.mailSender.sendRegistration(this.concreteMail);
    }
}
