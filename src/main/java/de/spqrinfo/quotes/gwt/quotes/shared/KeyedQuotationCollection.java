package de.spqrinfo.quotes.gwt.quotes.shared;

import java.io.Serializable;
import java.util.Collection;

public class KeyedQuotationCollection<K extends Serializable> implements Serializable {

    private K key;
    private Collection<Quotation> quotations;

    public KeyedQuotationCollection() {
        // for serialization
    }

    public KeyedQuotationCollection(final K key, final Collection<Quotation> quotations) {
        this.key = key;
        this.quotations = quotations;
    }

    public K getKey() {
        return this.key;
    }

    public void setKey(final K key) {
        this.key = key;
    }

    public Collection<Quotation> getQuotations() {
        return this.quotations;
    }

    public void setQuotations(final Collection<Quotation> quotations) {
        this.quotations = quotations;
    }
}
