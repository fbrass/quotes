package de.spqrinfo.quotes.backend.security.service.scheduling;

import de.spqrinfo.quotes.backend.security.dao.PasswordResetMailScheduleDao;
import de.spqrinfo.quotes.backend.security.entity.SecurityTokenMailSchedule;
import de.spqrinfo.quotes.backend.security.service.PasswordResetMail;
import de.spqrinfo.quotes.backend.security.service.SecurityMailSender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * Sends password reset mails which could not be sent.
 */
@Service
public class PasswordResetMailScheduler {

    private static final Logger log = LoggerFactory.getLogger(PasswordResetMailScheduler.class);

    @Autowired
    private PasswordResetMailScheduleDao passwordResetMailScheduleDao;

    @Autowired
    private PasswordResetMail passwordResetMail;

    @Autowired
    private SecurityMailSender mailSender;

    @Autowired
    private ScheduleExecuteStrategy scheduleExecuteStrategy;

    @Value("${app.mail.schedule.maxAttempts}")
    private int maxAttempts;

    public int getMaxAttempts() {
        return this.maxAttempts;
    }

    @Transactional
    @Scheduled(fixedDelay = 2 * 60 * 1000)
    public void onSchedule() {
        for (final SecurityTokenMailSchedule srm : this.passwordResetMailScheduleDao.findAll()) {
            log.debug("Processing scheduled password reset mail {}", srm);

            if (this.scheduleExecuteStrategy.isSchedulingAttemptDesired(srm.getAttempts(), srm.getLastAttempt())) {
                boolean success = false;
                try {
                    srm.setAttempts(1 + srm.getAttempts());
                    srm.setLastAttempt(new Date());
                    processPasswordResetMail(srm);
                    success = true;
                } catch (Exception ex) {
                    log.warn("Failed processing scheduled password reset mail " + srm, ex);
                } finally {
                    if (success) {
                        log.info("Done sending password reset mail to {}", srm.getUserId());
                        this.passwordResetMailScheduleDao.delete(srm.getPK());
                    } else {
                        if (srm.getAttempts() < this.maxAttempts) {
                            this.passwordResetMailScheduleDao.update(srm);
                        } else {
                            log.warn("Max attempts of scheduled password reset mail reached, aborting {}", srm);
                            this.passwordResetMailScheduleDao.delete(srm.getPK());
                        }
                    }
                }
            }
        }
    }

    public void processPasswordResetMail(final SecurityTokenMailSchedule securityTokenMailSchedule) {
        this.passwordResetMail.setEmail(securityTokenMailSchedule.getUserId());
        this.passwordResetMail.setUrl(securityTokenMailSchedule.getUrl());

        log.debug("Sending scheduled password reset mail to {}", securityTokenMailSchedule.getUserId());
        this.mailSender.send(this.passwordResetMail);
    }
}
