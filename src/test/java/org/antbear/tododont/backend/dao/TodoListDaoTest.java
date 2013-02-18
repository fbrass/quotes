package org.antbear.tododont.backend.dao;

import org.antbear.tododont.backend.entity.TodoItem;
import org.antbear.tododont.backend.entity.TodoList;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.jdbc.JdbcDaoImpl;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import static junit.framework.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/test-context.xml")
public class TodoListDaoTest {

    @Autowired
    private JdbcDaoImpl userDetailsService;

    @Autowired
    private TodoListDao todoListDao;

    @Autowired
    private TodoItemDao todoItemDao;

    @Test
    public void testDumpUsers() {
        final List<UserDetails> users = userDetailsService.getJdbcTemplate().query("SELECT email,password,enabled FROM users", new RowMapper<UserDetails>() {
            @Override
            public UserDetails mapRow(ResultSet rs, int rowNum) throws SQLException {
                String email = rs.getString(1);
                String password = rs.getString(2);
                boolean enabled = rs.getBoolean(3);
                return new User(email, password, enabled, true, true, true, AuthorityUtils.NO_AUTHORITIES);
            }
        });
        for (final UserDetails user : users) {
            System.out.println(user + " with stored password '" + user.getPassword() + "'");
        }
        assertThat(users.size(), equalTo(2));
    }

    @Test
    public void testFindAllByUsername() throws Exception {
        final List<TodoList> lists = this.todoListDao.findAllByUser("alice@nowhere.tld");
        assertEquals(2, lists.size());
    }

    @Test
    public void testFindByName() throws Exception {
        final TodoList list = this.todoListDao.findByName("alice@nowhere.tld", "eins");
        assertThat(list, notNullValue());
        final List<TodoItem> itemList = this.todoItemDao.findAllByListId(list.getPK());
        assertThat(itemList.size(), is(3));
    }
}
