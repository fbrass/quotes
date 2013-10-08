package de.spqrinfo.quotes.gwt.quotes.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface QuotesServiceAsync {
    void first(String text, AsyncCallback<String> async);
}
