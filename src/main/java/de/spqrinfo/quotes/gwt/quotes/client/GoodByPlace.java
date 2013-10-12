package de.spqrinfo.quotes.gwt.quotes.client;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

public class GoodByPlace extends Place {

    private final String text;

    public GoodByPlace(final String token) {
        this.text = token;
    }

    public String getText() {
        return this.text;
    }

    public static class Tokenizer implements PlaceTokenizer<GoodByPlace> {

        @Override
        public String getToken(final GoodByPlace place) {
            return place.getText();
        }

        @Override
        public GoodByPlace getPlace(final String token) {
            return new GoodByPlace(token);
        }
    }
}
