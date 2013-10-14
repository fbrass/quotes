package de.spqrinfo.quotes.gwt.quotes.client;

import com.google.gwt.event.shared.GwtEvent;

public class QuotesAppEvent extends GwtEvent<QuotesAppEventHandler> {

    public static Type<QuotesAppEventHandler> TYPE = new Type<QuotesAppEventHandler>();

    @Override
    public Type<QuotesAppEventHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(final QuotesAppEventHandler handler) {
        handler.onAppEvent(this);
    }
}
