package de.spqrinfo.quotes.gwt.quotes.shared;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import java.util.List;

@RemoteServiceRelativePath("quotesService")
public interface QuotesService extends RemoteService {

    List<Quotation> getQuotes();

    KeyedQuotationCollection<QuotationAuthor> getQuotesByAuthor(Integer authorId);

    KeyedQuotationCollection<QuotationTag> getQuotesByTagName(String tagName);

    Quotation getQuote(Integer quoteId);
}
