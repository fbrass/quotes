package org.antbear.tododont.backend.service.security;

public interface SecurityMailSender {
    void send(final SecurityMail securityMail);
}
