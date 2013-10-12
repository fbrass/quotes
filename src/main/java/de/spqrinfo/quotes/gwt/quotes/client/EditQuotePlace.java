package de.spqrinfo.quotes.gwt.quotes.client;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

public class EditQuotePlace extends Place {

    private String name;

    public EditQuotePlace(final String token) {
        this.name = token;
    }

    public String getName() {
        return this.name;
    }

    public static class Tokenizer implements PlaceTokenizer<EditQuotePlace> {

        @Override
        public String getToken(final EditQuotePlace place) {
            return place.getName();
        }

        @Override
        public EditQuotePlace getPlace(final String token) {
            return new EditQuotePlace(token);
        }
    }
}
