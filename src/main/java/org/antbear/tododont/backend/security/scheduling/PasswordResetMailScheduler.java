package org.antbear.tododont.backend.security.scheduling;

import org.antbear.tododont.backend.security.dao.PasswordResetMailScheduleDao;
import org.antbear.tododont.backend.security.entity.SecurityTokenMailSchedule;
import org.antbear.tododont.backend.security.service.PasswordResetMail;
import org.antbear.tododont.backend.security.service.SecurityMailSender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * Sends password reset mails which could not be sent.
 */
@Component
public class PasswordResetMailScheduler {

    private static final Logger log = LoggerFactory.getLogger(PasswordResetMailScheduler.class);

    @Autowired
    private PasswordResetMailScheduleDao passwordResetMailScheduleDao;

    @Autowired
    private PasswordResetMail passwordResetMail;

    @Autowired
    private SecurityMailSender mailSender;

    private int maxAttempts = 10;

    public int getMaxAttempts() {
        return maxAttempts;
    }

    public void setMaxAttempts(final int maxAttempts) {
        this.maxAttempts = maxAttempts;
    }

    @Scheduled(fixedDelay = 2 * 60 * 1000) // TODO unrealistic, better use exponential back off
    public void onSchedule() {
        boolean success;
        for (final SecurityTokenMailSchedule srm : this.passwordResetMailScheduleDao.findAll()) {
            log.debug("Processing scheduled password reset mail {}", srm);

            success = false;
            try {
                srm.setAttempts(1 + srm.getAttempts());
                srm.setLastAttempt(new Date());
                processPasswordResetMail(srm);
                success = true;
            } catch (Exception ex) {
                log.warn("Failed processing scheduled password reset mail " + srm, ex);
            } finally {
                if (success) {
                    log.info("Done sending password reset mail to {}", srm.getEmail());
                    this.passwordResetMailScheduleDao.delete(srm.getPK());
                } else {
                    if (srm.getAttempts() < maxAttempts) {
                        this.passwordResetMailScheduleDao.update(srm);
                    } else {
                        log.warn("Max attempts of scheduled password reset mail reached, aborting {}", srm);
                        this.passwordResetMailScheduleDao.delete(srm.getPK());
                    }
                }
            }
        }
    }

    public void processPasswordResetMail(final SecurityTokenMailSchedule securityTokenMailSchedule) {
        this.passwordResetMail.setEmail(securityTokenMailSchedule.getEmail());
        this.passwordResetMail.setUrl(securityTokenMailSchedule.getUrl());

        log.debug("Sending scheduled password reset mail to {}", securityTokenMailSchedule.getEmail());
        this.mailSender.send(this.passwordResetMail);
    }
}
