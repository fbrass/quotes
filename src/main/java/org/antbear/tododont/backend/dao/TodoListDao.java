package org.antbear.tododont.backend.dao;

import com.google.common.collect.ImmutableMap;
import org.antbear.tododont.backend.entity.TodoList;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

@Repository
public class TodoListDao extends GenericDao<TodoList, Long> {

    @NotNull
    @Transactional
    public List<TodoList> findAllByUser(@NotNull final String login) {
        return getJdbcTemplate().query("SELECT * FROM " + getTableName() + " WHERE user = :user",
                new MapSqlParameterSource("user", login),
                newRowMapper());
    }

    @Null
    @Transactional
    public TodoList findByName(@NotNull final String login, @NotNull final String listName) {
        return getJdbcTemplate().queryForObject("SELECT * FROM " + getTableName()
                + " WHERE user = :user AND listname = :listname",
                ImmutableMap.of("user", login, "listname", listName),
                newRowMapper());
    }

    @Override
    protected String getTableName() {
        return "todolist";
    }

    @Override
    protected RowMapper<TodoList> newRowMapper() {
        return new TodoListRowMapper();
    }

    private static class TodoListRowMapper implements RowMapper<TodoList> {

        @Override
        public TodoList mapRow(final ResultSet rs, final int rowNum) throws SQLException {
            final long id = rs.getLong(1);
            final String user = rs.getString(2);
            final String listName = rs.getString(3);
            final Date created = rs.getTimestamp(4);

            final TodoList todoList = new TodoList();
            todoList.setPK(id);
            todoList.setUser(user);
            todoList.setListName(listName);
            todoList.setCreated(created);
            return todoList;
        }
    }
}
