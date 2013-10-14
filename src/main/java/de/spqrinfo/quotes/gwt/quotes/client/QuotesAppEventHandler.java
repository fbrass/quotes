package de.spqrinfo.quotes.gwt.quotes.client;

import com.google.gwt.event.shared.EventHandler;

public interface QuotesAppEventHandler extends EventHandler {
    void onAppEvent(QuotesAppEvent event);
}
