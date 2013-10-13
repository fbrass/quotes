package de.spqrinfo.quotes.gwt.quotes.shared;

import java.io.Serializable;
import java.util.Collection;

public class QuotationsOfAuthor implements Serializable {

    private QuotationAuthor quotationAuthor;
    private Collection<Quotation> quotations;

    public QuotationsOfAuthor() {
        // for serialization
    }

    public QuotationsOfAuthor(final QuotationAuthor quotationAuthor, final Collection<Quotation> quotations) {
        this.quotationAuthor = quotationAuthor;
        this.quotations = quotations;
    }

    public QuotationAuthor getQuotationAuthor() {
        return this.quotationAuthor;
    }

    public void setQuotationAuthor(final QuotationAuthor quotationAuthor) {
        this.quotationAuthor = quotationAuthor;
    }

    public Collection<Quotation> getQuotations() {
        return this.quotations;
    }

    public void setQuotations(final Collection<Quotation> quotations) {
        this.quotations = quotations;
    }
}
