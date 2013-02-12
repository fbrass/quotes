package org.antbear.tododont.backend.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static java.lang.Boolean.TRUE;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:/test-context.xml")
public class UserDaoTest {

    @Autowired
    private UserDao userDao;

    @Test
    public void testExists() throws Exception {
        assertTrue(this.userDao.exists("alice@nowhere.tld"));
    }

    @Test
    public void testCreateUser() throws Exception {
        final String email = "newUser@nowhere.tld";
        final String password = "secure";
        final boolean enabled = false;
        final String registrationToken = "register-token";
        this.userDao.createUser(email, password, enabled, registrationToken);

        final UserDetails user = this.userDao.findUser(email);
        assertThat(user.getUsername(), equalTo(email));
        assertThat(user.getPassword(), equalTo(password));
        assertThat(user.isAccountNonLocked(), is(TRUE));

        final boolean active = this.userDao.getActiveStateByUser(email);
        assertEquals(enabled, active);
    }

    @Test(expected = Exception.class)
    public void testCreateUserExisting() throws Exception {
        this.userDao.createUser("alice@nowhere.tld", "secure", false, "register-token");
    }

    @Test
    public void testFindRegistrationTokeByUser() throws Exception {
        assertThat(this.userDao.findRegistrationTokenByUser("new@registered.tld"), is("registration-token"));
    }
}
