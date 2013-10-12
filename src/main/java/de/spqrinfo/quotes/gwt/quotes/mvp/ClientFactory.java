package de.spqrinfo.quotes.gwt.quotes.mvp;

import com.google.web.bindery.event.shared.EventBus;
import com.google.gwt.place.shared.PlaceController;
import de.spqrinfo.quotes.gwt.quotes.client.EditQuoteView;
import de.spqrinfo.quotes.gwt.quotes.client.GoodByeView;

public interface ClientFactory {
    EventBus getEventBus();
    PlaceController getPlaceController();
    EditQuoteView getEditQuoteView();
    GoodByeView getEdGoodByeView();
}
