package org.antbear.tododont.backend.dao;

import org.antbear.tododont.backend.entity.TodoItem;
import org.antbear.tododont.backend.entity.TodoList;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static junit.framework.Assert.assertEquals;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

@Ignore
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:/test-context.xml")
public class TodoListDaoTest {

    @Autowired
    private TodoListDao todoListDao;

    @Autowired
    private TodoItemDao todoItemDao;

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
