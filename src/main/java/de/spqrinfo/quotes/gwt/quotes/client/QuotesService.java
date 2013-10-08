package de.spqrinfo.quotes.gwt.quotes.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("quotesService")
public interface QuotesService extends RemoteService {

    String first(String text) throws IllegalArgumentException;
}
