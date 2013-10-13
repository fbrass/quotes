package de.spqrinfo.quotes.gwt.quotes.shared;

import java.io.Serializable;

public class QuotationAuthor implements Serializable {

    private Integer id;
    private String name;

    public QuotationAuthor() {
        // for serialization
    }

    public QuotationAuthor(final Integer id, final String name) {
        this.id = id;
        this.name = name;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(final Integer id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(final String name) {
        this.name = name;
    }
}
