package org.antbear.tododont.backend.dao;

import org.antbear.tododont.backend.entity.TodoItem;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotNull;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
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
            final long id = rs.getLong(1);
            final long listId = rs.getLong(2);
            final String itemName = rs.getString(3);
            final Date created = rs.getTimestamp(4);
            final boolean done = rs.getBoolean(5);

            final TodoItem item = new TodoItem();
            item.setPK(id);
            item.setTodoListId(listId);
            item.setItemName(itemName);
            item.setCreated(created);
            item.setDone(done);
            return item;
        }
    }
}
