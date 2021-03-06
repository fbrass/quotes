package de.spqrinfo.quotes.backend.security.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SecurityMailSenderNullTestSupport implements SecurityMailSender {

    private static final Logger log = LoggerFactory.getLogger(SecurityMailSenderNullTestSupport.class);

    private SecurityMail securityMail;

    @Override
    public void send(final SecurityMail registrationMail) {
        log.debug("send " + registrationMail);
        this.securityMail = registrationMail;
    }

    public SecurityMail getSecurityMail() {
        return this.securityMail;
    }
}
