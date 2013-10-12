package de.spqrinfo.quotes.gwt.quotes.client;

import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.place.shared.PlaceController;
import com.google.web.bindery.event.shared.EventBus;

public class ClientFactoryImpl implements ClientFactory {

    private final EventBus eventBus = new SimpleEventBus();
    private final PlaceController placeController = new PlaceController(this.eventBus);
    private final EditQuoteView editQuoteView = new EditQuoteViewImpl();
    private final GoodByeView goodByeView = new GoodByViewImpl();

    @Override
    public EventBus getEventBus() {
        return this.eventBus;
    }

    @Override
    public PlaceController getPlaceController() {
        return this.placeController;
    }

    @Override
    public EditQuoteView getEditQuoteView() {
        return this.editQuoteView;
    }

    @Override
    public GoodByeView getEdGoodByeView() {
        return this.goodByeView;
    }
}
