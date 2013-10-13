package de.spqrinfo.quotes.gwt.quotes.mvp;

import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.place.shared.PlaceController;
import com.google.web.bindery.event.shared.EventBus;
import de.spqrinfo.quotes.gwt.quotes.client.edit.EditQuoteView;
import de.spqrinfo.quotes.gwt.quotes.client.edit.EditQuoteViewImpl;
import de.spqrinfo.quotes.gwt.quotes.client.listauthorquotes.ListAuthorQuotesView;
import de.spqrinfo.quotes.gwt.quotes.client.listauthorquotes.ListAuthorQuotesViewImpl;
import de.spqrinfo.quotes.gwt.quotes.client.listquotes.ListQuotesView;
import de.spqrinfo.quotes.gwt.quotes.client.listquotes.ListQuotesViewImpl;
import de.spqrinfo.quotes.gwt.quotes.client.listtagquotes.ListTagQuotesView;
import de.spqrinfo.quotes.gwt.quotes.client.listtagquotes.ListTagQuotesViewImpl;

public class ClientFactoryImpl implements ClientFactory {

    private final EventBus eventBus = new SimpleEventBus();
    private final PlaceController placeController = new PlaceController(this.eventBus);
    private final EditQuoteView editQuoteView = new EditQuoteViewImpl();
    private final ListQuotesView listQuotesView = new ListQuotesViewImpl();
    private final ListAuthorQuotesView listAuthorQuotesView = new ListAuthorQuotesViewImpl();
    private final ListTagQuotesView listTagQuotesView = new ListTagQuotesViewImpl();

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
    public ListQuotesView getListQuotesView() {
        return this.listQuotesView;
    }

    @Override
    public ListAuthorQuotesView getListAuthorQuotesView() {
        return this.listAuthorQuotesView;
    }

    @Override
    public ListTagQuotesView getListTagQuotesView() {
        return this.listTagQuotesView;
    }
}
