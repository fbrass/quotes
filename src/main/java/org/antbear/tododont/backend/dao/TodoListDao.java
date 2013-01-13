package org.antbear.tododont.backend.dao;

import com.google.common.collect.Maps;
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
import java.util.Map;

@Repository
public class TodoListDao extends GenericDao<TodoList, Long> {

    @NotNull
    @Transactional
    public List<TodoList> findAllByUsername(@NotNull final String username) {
        return getJdbcTemplate().query("SELECT * FROM " + getTableName() + " WHERE username = :username",
                new MapSqlParameterSource("username", username),
                newRowMapper());
    }

    @Null
    @Transactional
    public TodoList findByName(@NotNull final String username, @NotNull final String listName) {
        final Map<String, Object> parameters = Maps.newHashMap();
        parameters.put("username", username);
        parameters.put("listname", listName);

        return getJdbcTemplate().queryForObject("SELECT * FROM " + getTableName()
                + " WHERE username = :username AND listname = :listname",
                new MapSqlParameterSource(parameters),
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
            final String username = rs.getString(2);
            final String listName = rs.getString(3);
            final Date created = rs.getTimestamp(4);

            final TodoList todoList = new TodoList();
            todoList.setPK(id);
            todoList.setUsername(username);
            todoList.setListName(listName);
            todoList.setCreated(created);
            return todoList;
        }
    }
}
