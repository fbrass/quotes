package org.antbear.tododont.backend.app.dao;

import com.google.common.collect.ImmutableMap;
import org.antbear.tododont.backend.dataaccess.GenericDao;
import org.antbear.tododont.backend.app.entity.TodoList;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class TodoListDao extends GenericDao<TodoList, Long> {

    public TodoListDao() {
        super("id", "todolist");
    }

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
    protected RowMapper<TodoList> newRowMapper() {
        return new TodoListRowMapper();
    }

    private static class TodoListRowMapper implements RowMapper<TodoList> {

        @Override
        public TodoList mapRow(final ResultSet rs, final int rowNum) throws SQLException {
            final TodoList todoList = new TodoList();
            todoList.setPK(rs.getLong(1));
            todoList.setUser(rs.getString(2));
            todoList.setListName(rs.getString(3));
            todoList.setCreated(rs.getTimestamp(4));
            return todoList;
        }
    }
}