package de.spqrinfo.quotes.gwt.quotes.shared;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import java.util.List;

@RemoteServiceRelativePath("quotesService")
public interface QuotesService extends RemoteService {

    String first(String text) throws IllegalArgumentException;

    List<Quotation> getQuotes();

    QuotationsOfAuthor getQuotesByAuthor(Integer authorId);

    Quotation getQuote(Integer quoteId);
}
