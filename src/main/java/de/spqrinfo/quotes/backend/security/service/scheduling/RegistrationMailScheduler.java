package de.spqrinfo.quotes.backend.security.service.scheduling;

import de.spqrinfo.quotes.backend.security.dao.CustomUserDetailsService;
import de.spqrinfo.quotes.backend.security.dao.RegistrationMailScheduleDao;
import de.spqrinfo.quotes.backend.security.entity.SecurityTokenMailSchedule;
import de.spqrinfo.quotes.backend.security.service.RegistrationMail;
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
                        log.info("Done sending registration to mail to {}", srm.getUserId());
                        this.registrationMailScheduleDao.delete(srm.getPK());
                    } else {
                        if (srm.getAttempts() < this.maxAttempts) {
                            this.registrationMailScheduleDao.update(srm);
                        } else {
                            log.warn("Max attempts of scheduled registration mail reached, deleting user {}", srm.getUserId());
                            // scheduled task will be deleted by dropping user
                            this.userDetailsService.deleteUser(srm.getUserId());
                        }
                    }
                }
            }
        }
    }

    public void processRegistrationMail(final SecurityTokenMailSchedule securityTokenMailSchedule) {
        this.registrationMail.setEmail(securityTokenMailSchedule.getUserId());
        this.registrationMail.setUrl(securityTokenMailSchedule.getUrl());

        log.debug("Scheduled sending registration email to {}", securityTokenMailSchedule.getUserId());
        this.mailSender.send(this.registrationMail);
    }
}
