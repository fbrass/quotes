package de.spqrinfo.quotes.gwt.quotes.client;

import com.google.gwt.activity.shared.Activity;
import com.google.gwt.activity.shared.ActivityMapper;
import com.google.gwt.place.shared.Place;

public class AppActivityMapper implements ActivityMapper {

    private final ClientFactory clientFactory;

    public AppActivityMapper(final ClientFactory clientFactory) {
        this.clientFactory = clientFactory;
    }

    @Override
    public Activity getActivity(final Place place) {
        if (place instanceof EditQuotePlace) {
            return new EditQuoteActivity((EditQuotePlace) place, this.clientFactory);
        } else if (place instanceof GoodByPlace) {
            return new GoodByActivity((GoodByPlace) place, this.clientFactory);
        } else {
            return null;
        }
    }
}
