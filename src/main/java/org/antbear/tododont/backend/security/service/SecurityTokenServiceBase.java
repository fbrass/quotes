package org.antbear.tododont.backend.security.service;

import javax.xml.bind.DatatypeConverter;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.SecureRandom;

public abstract class SecurityTokenServiceBase {

    private final SecureRandom secureRandom = new SecureRandom();

    /**
     * Creates a security token to be stored in DB and send via mail.
     */
    protected String createSecurityToken() {
        final byte[] randomBytes = new byte[128];
        this.secureRandom.nextBytes(randomBytes);
        final String randomBase64 = DatatypeConverter.printBase64Binary(randomBytes);
        final String urlEncoded = urlEncode(randomBase64);
        // Truncate to 128 characters
        return urlEncoded.substring(0, 127);
    }

    protected static String urlEncode(final String s) {
        try {
            return URLEncoder.encode(s, "utf-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }
}
