package org.antbear.tododont.backend.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

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
        this.userDao.createUser("newUser@nowhere.tld", false, "register-token");
    }

    @Test(expected = Exception.class)
    public void testCreateUserExisting() throws Exception {
        this.userDao.createUser("alice@nowhere.tld", false, "register-token");
    }

    @Test
    public void testFindRegistrationTokeByUser() throws Exception {
        final String expected = "registration-token";
        final String selected = this.userDao.findRegistrationTokenByUser("new@registered.tld");
        assertEquals(expected, selected);
    }
}
