package de.spqrinfo.quotes.backend.entity;

import de.spqrinfo.quotes.backend.dao.DomainObject;

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

    @Override
    public Long getPK() {
        return this.id;
    }

    @Override
    public void setPK(final Long id) {
        this.id = id;
    }

    public Long getTodoListId() {
        return this.todoListId;
    }

    public void setTodoListId(final Long todoListId) {
        this.todoListId = todoListId;
    }

    public String getItemName() {
        return this.itemName;
    }

    public void setItemName(final String itemName) {
        this.itemName = itemName;
    }

    public Date getCreated() {
        return this.created;
    }

    public void setCreated(final Date created) {
        this.created = created;
    }

    public boolean isDone() {
        return this.done;
    }

    public void setDone(final boolean done) {
        this.done = done;
    }

    @Override
    public String toString() {
        return "TodoItem{" +
                "id=" + this.id +
                ", todoListId=" + this.todoListId +
                ", itemName='" + this.itemName + '\'' +
                ", created=" + this.created +
                ", done=" + this.done +
                '}';
    }
}
