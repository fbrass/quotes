package org.antbear.tododont.backend.security.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:/test-context.xml")
public class PasswordChangeServiceTest {

    private static final Logger log = LoggerFactory.getLogger(PasswordChangeServiceTest.class);

    private static final String EMAIL = "alice@nowhere.tld";

    @Autowired
    private PasswordChangeService passwordChangeService;

    @Test
    public void changePasswordTest() {

    }
}
