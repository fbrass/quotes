package de.spqrinfo.quotes.gwt.quotes.client.mvp;

import com.google.gwt.activity.shared.Activity;
import com.google.gwt.activity.shared.ActivityMapper;
import com.google.gwt.place.shared.Place;
import de.spqrinfo.quotes.gwt.quotes.client.edit.EditQuoteActivity;
import de.spqrinfo.quotes.gwt.quotes.client.edit.EditQuotePlace;
import de.spqrinfo.quotes.gwt.quotes.client.listauthorquotes.ListAuthorQuotesActivity;
import de.spqrinfo.quotes.gwt.quotes.client.listauthorquotes.ListAuthorQuotesPlace;
import de.spqrinfo.quotes.gwt.quotes.client.listquotes.ListQuotesActivity;
import de.spqrinfo.quotes.gwt.quotes.client.listquotes.ListQuotesPlace;
import de.spqrinfo.quotes.gwt.quotes.client.listtagquotes.ListTagQuotesActivity;
import de.spqrinfo.quotes.gwt.quotes.client.listtagquotes.ListTagQuotesPlace;

public class AppActivityMapper implements ActivityMapper {

    private final ClientFactory clientFactory;

    public AppActivityMapper(final ClientFactory clientFactory) {
        this.clientFactory = clientFactory;
    }

    @Override
    public Activity getActivity(final Place place) {
        if (place instanceof EditQuotePlace) {
            return new EditQuoteActivity((EditQuotePlace) place, this.clientFactory);
        } else if (place instanceof ListQuotesPlace) {
            return new ListQuotesActivity((ListQuotesPlace) place, this.clientFactory);
        } else if (place instanceof ListAuthorQuotesPlace) {
            return new ListAuthorQuotesActivity((ListAuthorQuotesPlace) place, this.clientFactory);
        } else if (place instanceof ListTagQuotesPlace) {
            return new ListTagQuotesActivity((ListTagQuotesPlace) place, this.clientFactory);
        } else {
            return null;
        }
    }
}
