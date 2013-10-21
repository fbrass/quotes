package de.spqrinfo.quotes.backend.todo.dao;

import de.spqrinfo.quotes.backend.base.GenericDao;
import de.spqrinfo.quotes.backend.todo.domain.TodoItem;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotNull;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class TodoItemDao extends GenericDao<TodoItem, Long> {

    public TodoItemDao() {
        super("id", "todoitem");
    }

    @Transactional
    public List<TodoItem> findAllByListId(@NotNull final long listId) {
        return getJdbcTemplate().query("SELECT * FROM " + getTableName() + " WHERE todolist_id = :listid",
                new MapSqlParameterSource("listid", listId),
                newRowMapper());
    }

    @Override
    protected RowMapper<TodoItem> newRowMapper() {
        return new TodoItemRowMapper();
    }

    private static class TodoItemRowMapper implements RowMapper<TodoItem> {

        @Override
        public TodoItem mapRow(final ResultSet rs, final int rowNum) throws SQLException {
            final TodoItem item = new TodoItem();
            item.setPK(rs.getLong(1));
            item.setTodoListId(rs.getLong(2));
            item.setItemName(rs.getString(3));
            item.setCreated(rs.getTimestamp(4));
            item.setDone(rs.getBoolean(5));
            return item;
        }
    }
}
