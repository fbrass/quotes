package de.spqrinfo.quotes.gwt.quotes.shared;

import java.io.Serializable;

public class QuotationTag implements Serializable {

    private String tagName;

    public QuotationTag() {
        // for serialization
    }

    public QuotationTag(final String tagName) {
        this.tagName = tagName;
    }

    public String getTagName() {
        return this.tagName;
    }

    public void setTagName(final String tagName) {
        this.tagName = tagName;
    }
}
