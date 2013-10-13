package de.spqrinfo.quotes.gwt.quotes.shared;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class Quotation implements Serializable {

    private Integer id;
    private String quotationText;
    private Date lastModified;
    private QuotationAuthor author;
    private Integer rating;
    private List<QuotationTag> tags;

    public Quotation() {
        // for serialization
    }

    public Quotation(final Integer id, final String quotationText, final Integer rating,
                     final QuotationAuthor author, final List<QuotationTag> tags) {
        this.id = id;
        this.quotationText = quotationText;
        this.lastModified = new Date();
        this.author = author;
        this.rating = rating;
        this.tags = tags;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(final Integer id) {
        this.id = id;
    }

    public String getQuotationText() {
        return this.quotationText;
    }

    public void setQuotationText(final String quotationText) {
        this.quotationText = quotationText;
    }

    public Date getLastModified() {
        return this.lastModified;
    }

    public void setLastModified(final Date lastModified) {
        this.lastModified = lastModified;
    }

    public QuotationAuthor getAuthor() {
        return this.author;
    }

    public void setAuthor(final QuotationAuthor author) {
        this.author = author;
    }

    public Integer getRating() {
        return this.rating;
    }

    public void setRating(final Integer rating) {
        this.rating = rating;
    }

    public List<QuotationTag> getTags() {
        return this.tags;
    }

    public void setTags(final List<QuotationTag> tags) {
        this.tags = tags;
    }

    public String getTagsAsText() {
        final StringBuilder buf = new StringBuilder();
        if (this.tags != null) {
            for (final QuotationTag tag : this.tags) {
                if (buf.length() != 0) {
                    buf.append(", ");
                }
                buf.append(tag.getTagName());
            }
        }
        return buf.toString();
    }
}
