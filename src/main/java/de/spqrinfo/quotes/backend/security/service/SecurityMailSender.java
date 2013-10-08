package de.spqrinfo.quotes.backend.security.service;

public interface SecurityMailSender {
    void send(final SecurityMail securityMail);
}
