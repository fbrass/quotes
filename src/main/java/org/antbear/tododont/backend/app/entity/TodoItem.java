package org.antbear.tododont.backend.app.entity;

import org.antbear.tododont.backend.dataaccess.DomainObject;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

public class TodoItem implements DomainObject<Long> {

    @NotNull
    private Long id;

    @NotNull
    private Long todoListId;

    @NotNull
    @Size(max = 64)
    private String itemName;

    @NotNull
    private Date created;

    private boolean done;

    public Long getPK() {
        return id;
    }

    public void setPK(final Long id) {
        this.id = id;
    }

    public Long getTodoListId() {
        return todoListId;
    }

    public void setTodoListId(final Long todoListId) {
        this.todoListId = todoListId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(final String itemName) {
        this.itemName = itemName;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(final Date created) {
        this.created = created;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(final boolean done) {
        this.done = done;
    }

    @Override
    public String toString() {
        return "TodoItem{" +
                "id=" + id +
                ", todoListId=" + todoListId +
                ", itemName='" + itemName + '\'' +
                ", created=" + created +
                ", done=" + done +
                '}';
    }
}
