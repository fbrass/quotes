package de.spqrinfo.quotes.backend.dao;

import com.google.common.collect.ImmutableMap;
import de.spqrinfo.quotes.backend.entity.TodoList;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Transactional(propagation = Propagation.REQUIRED)
@Repository
public class TodoListDao extends GenericDao<TodoList, Long> {

    public TodoListDao() {
        super("id", "todolist");
    }

    @NotNull
    public List<TodoList> findAllByUsername(@NotNull final String username) {
        return getJdbcTemplate().query("SELECT * FROM " + getTableName() + " WHERE username = :user",
                ImmutableMap.of("user", username),
                newRowMapper());
    }

    @Null
    public TodoList findByListName(@NotNull final String username, @NotNull final String listName) {
        return getJdbcTemplate().queryForObject("SELECT * FROM " + getTableName()
                + " WHERE username = :user AND listname = :listname",
                ImmutableMap.of("user", username, "listname", listName),
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
            todoList.setUsername(rs.getString(2));
            todoList.setListName(rs.getString(3));
            todoList.setCreated(rs.getTimestamp(4));
            return todoList;
        }
    }
}
