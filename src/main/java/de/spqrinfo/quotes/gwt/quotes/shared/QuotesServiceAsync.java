package de.spqrinfo.quotes.gwt.quotes.shared;

import com.google.gwt.user.client.rpc.AsyncCallback;

import java.util.List;

public interface QuotesServiceAsync {

    void getQuotes(AsyncCallback<List<Quotation>> async);

    void getQuotesByAuthor(Integer authorId, AsyncCallback<KeyedQuotationCollection<QuotationAuthor>> async);

    void getQuotesByTagName(String tagName, AsyncCallback<KeyedQuotationCollection<QuotationTag>> async);

    void getQuote(Integer quoteId, AsyncCallback<Quotation> async);
}
