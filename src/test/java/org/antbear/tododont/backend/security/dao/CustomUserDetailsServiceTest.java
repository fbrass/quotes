package org.antbear.tododont.backend.security.dao;

import org.antbear.tododont.backend.security.entity.CustomUserDetails;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:/test-context.xml")
public class CustomUserDetailsServiceTest {

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Test
    public void testExists() throws Exception {
        assertTrue(this.userDetailsService.isExistingUser("alice@nowhere.tld"));
    }

    @Test
    public void createUserTest() throws Exception {
        final String email = "newUser@nowhere.tld";
        final String password = "secure";
        final String registrationToken = "register-token";

        final CustomUserDetails userToCreate = new CustomUserDetails(email, registrationToken);
        userToCreate.setPassword(password);

        this.userDetailsService.createUser(userToCreate);

        final UserDetails user = this.userDetailsService.loadUserByUsername(email);
        assertThat(user.getUsername(), equalTo(email));
        assertThat(user.getPassword(), equalTo(password));
        assertFalse(user.isEnabled());
    }

    @Test(expected = Exception.class)
    public void createUserTestExisting() throws Exception {
        final CustomUserDetails user = new CustomUserDetails("alice@nowhere.tld", "register-token");
        user.setPassword("secure");
        this.userDetailsService.createUser(user);
    }

    @Test
    public void loadUserByUsernameRegistrationTokenTest() throws Exception {
        final CustomUserDetails user = (CustomUserDetails) this.userDetailsService.loadUserByUsername("new@registered.tld");
        assertThat(user.getRegistrationToken(), is("registration-token"));
    }
}