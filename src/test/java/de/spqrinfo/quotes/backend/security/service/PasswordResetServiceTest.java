package de.spqrinfo.quotes.backend.security.service;

import de.spqrinfo.quotes.backend.security.beans.PasswordResetAttempt;
import de.spqrinfo.quotes.backend.security.entity.CustomUserDetails;
import de.spqrinfo.quotes.backend.security.beans.PasswordReset;
import de.spqrinfo.quotes.backend.security.dao.CustomUserDetailsService;
import de.spqrinfo.quotes.web.controller.security.PasswordResetController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/test-base-context.xml", "classpath:/test-mail-null-context.xml"})
public class PasswordResetServiceTest {

    @Autowired
    PasswordResetController passwordResetController;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Autowired
    private PasswordResetService passwordResetService;

    @Test
    public void testPasswordResetWithChangeTest() throws Exception {
        final String email = "alice@nowhere.tld";
        final String password = "totallySecret@0815";

        final String securityToken = this.passwordResetService.passwordResetAttempt(
                new PasswordResetAttempt(email),
                this.passwordResetController.getPasswordResetUriComponents());

        assertThat(((CustomUserDetails) this.userDetailsService.loadUserByUsername(email)).getPasswordResetToken(), is(securityToken));

        final PasswordReset passwordReset = new PasswordReset();
        passwordReset.setEmail(email);
        passwordReset.setPassword(password);
        passwordReset.setPassword2(password);
        passwordReset.setPasswordResetToken(securityToken);

        this.passwordResetService.passwordChange(passwordReset);

        final String tokenShouldBeNull = ((CustomUserDetails) this.userDetailsService.loadUserByUsername(email)).getPasswordResetToken();
        assertThat(tokenShouldBeNull, nullValue());
    }
}
