package org.antbear.tododont.backend.security.service.scheduling;

import org.antbear.tododont.backend.security.dao.CustomUserDetailsService;
import org.antbear.tododont.backend.security.dao.RegistrationMailScheduleDao;
import org.antbear.tododont.backend.security.entity.SecurityTokenMailSchedule;
import org.antbear.tododont.backend.security.service.RegistrationMail;
import org.antbear.tododont.backend.security.service.SecurityMailSender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * Send registration mails which could not be sent.
 */
@Service
public class RegistrationMailScheduler {

    private static final Logger log = LoggerFactory.getLogger(RegistrationMailScheduler.class);

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Autowired
    private RegistrationMailScheduleDao registrationMailScheduleDao;

    @Autowired
    private RegistrationMail registrationMail;

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
        for (final SecurityTokenMailSchedule srm : this.registrationMailScheduleDao.findAll()) {
            log.debug("Processing scheduled registration mail {}", srm);

            if (this.scheduleExecuteStrategy.isSchedulingAttemptDesired(srm.getAttempts(), srm.getLastAttempt())) {
                boolean success = false;
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
                        if (srm.getAttempts() < this.maxAttempts) {
                            this.registrationMailScheduleDao.update(srm);
                        } else {
                            log.warn("Max attempts of scheduled registration mail reached, deleting user {}", srm.getEmail());
                            // scheduled task will be deleted by dropping user
                            this.userDetailsService.deleteUser(srm.getEmail());
                        }
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
