package de.spqrinfo.quotes.gwt.quotes.client;

import com.google.web.bindery.event.shared.EventBus;
import com.google.gwt.place.shared.PlaceController;

public interface ClientFactory {
    EventBus getEventBus();
    PlaceController getPlaceController();
    EditQuoteView getEditQuoteView();
    GoodByeView getEdGoodByeView();
}
