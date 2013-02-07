package org.antbear.tododont.backend.entity;

import javax.validation.constraints.NotNull;
import java.util.Date;

public class TodoList implements DomainObject<Long> {

    @NotNull
    private Long id;

    @NotNull
    private String user;

    @NotNull
    private String listName;

    @NotNull
    private Date created;

    public Long getPK() {
        return id;
    }

    public void setPK(final Long id) {
        this.id = id;
    }

    public String getUser() {
        return user;
    }

    public void setUser(final String username) {
        this.user = username;
    }

    public String getListName() {
        return listName;
    }

    public void setListName(final String listName) {
        this.listName = listName;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(final Date created) {
        this.created = created;
    }

    @Override
    public String toString() {
        return "TodoList{" +
                "id=" + id +
                ", user='" + user + '\'' +
                ", listName='" + listName + '\'' +
                ", created=" + created +
                '}';
    }
}
