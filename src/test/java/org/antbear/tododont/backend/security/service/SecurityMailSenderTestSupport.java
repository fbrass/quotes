package org.antbear.tododont.backend.security.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SecurityMailSenderTestSupport implements SecurityMailSender {

    private static final Logger log = LoggerFactory.getLogger(SecurityMailSenderTestSupport.class);

    private SecurityMail securityMail;

    @Override
    public void send(final SecurityMail registrationMail) {
        log.debug("send " + registrationMail);
        this.securityMail = registrationMail;
    }

    public SecurityMail getSecurityMail() {
        return securityMail;
    }
}
