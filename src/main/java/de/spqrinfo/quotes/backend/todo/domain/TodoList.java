package de.spqrinfo.quotes.backend.todo.domain;

import de.spqrinfo.quotes.backend.base.DomainObject;

import javax.validation.constraints.NotNull;
import java.util.Date;

public class TodoList implements DomainObject<Long> {

    @NotNull
    private Long id;

    @NotNull
    private String username;

    @NotNull
    private String listName;

    @NotNull
    private Date created;

    @Override
    public Long getPK() {
        return this.id;
    }

    @Override
    public void setPK(final Long id) {
        this.id = id;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(final String username) {
        this.username = username;
    }

    public String getListName() {
        return this.listName;
    }

    public void setListName(final String listName) {
        this.listName = listName;
    }

    public Date getCreated() {
        return this.created;
    }

    public void setCreated(final Date created) {
        this.created = created;
    }

    @Override
    public String toString() {
        return "TodoList{" +
                "id=" + this.id +
                ", username='" + this.username + '\'' +
                ", listName='" + this.listName + '\'' +
                ", created=" + this.created +
                '}';
    }
}
