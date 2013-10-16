package de.spqrinfo.quotes.gwt.quotes.client.mvp;

import com.google.gwt.place.shared.PlaceController;
import com.google.web.bindery.event.shared.EventBus;
import de.spqrinfo.quotes.gwt.quotes.client.edit.EditQuoteView;
import de.spqrinfo.quotes.gwt.quotes.client.listauthorquotes.ListAuthorQuotesView;
import de.spqrinfo.quotes.gwt.quotes.client.listquotes.ListQuotesView;
import de.spqrinfo.quotes.gwt.quotes.client.listtagquotes.ListTagQuotesView;

public interface ClientFactory {
    EventBus getEventBus();
    PlaceController getPlaceController();
    EditQuoteView getEditQuoteView();
    ListQuotesView getListQuotesView();
    ListAuthorQuotesView getListAuthorQuotesView();
    ListTagQuotesView getListTagQuotesView();
}
