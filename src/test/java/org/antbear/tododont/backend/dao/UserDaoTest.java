package org.antbear.tododont.backend.dao;

import org.antbear.tododont.backend.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:/testContext.xml")
public class UserDaoTest {

    private static final Logger log = LoggerFactory.getLogger(UserDaoTest.class);

    @Autowired
    private UserDao userDao;

    @Test
    public void testFindAll() throws Exception {
        assertNotNull(this.userDao);
        final List<User> userList = this.userDao.findAll();
        assertNotNull(userList);
        assertEquals(2, userList.size());
    }
}
