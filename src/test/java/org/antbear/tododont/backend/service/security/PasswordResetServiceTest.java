package org.antbear.tododont.backend.service.security;

import org.antbear.tododont.backend.dao.UserDao;
import org.antbear.tododont.web.beans.security.PasswordResetAttempt;
import org.antbear.tododont.web.controller.security.PasswordResetController;
import org.hamcrest.CoreMatchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:/test-context.xml")
public class PasswordResetServiceTest {

    @Autowired
    PasswordResetController passwordResetController;

    @Autowired
    private UserDao userDao;

    @Autowired
    private PasswordResetService passwordResetService;

    @Test
    public void testPasswordForgetExistingUser() throws Exception {
        final String email = "alice@nowhere.tld";

        final String securityToken = this.passwordResetService.passwordResetAttempt(
                new PasswordResetAttempt(email),
                this.passwordResetController.getPasswordResetUriComponents());

        final String savedSecurityToken = this.userDao.getChangePasswordToken(email);
        assertThat(securityToken, CoreMatchers.is(savedSecurityToken));
    }
}
