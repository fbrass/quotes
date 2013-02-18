package org.antbear.tododont.backend.service.security;

import org.springframework.mail.SimpleMailMessage;

/**
 * TODO add docs
 *
 * @author marcus
 */
public interface SecurityMail {

    String getEmail();
    String getUrl();
    SimpleMailMessage getMailMessage();
}
