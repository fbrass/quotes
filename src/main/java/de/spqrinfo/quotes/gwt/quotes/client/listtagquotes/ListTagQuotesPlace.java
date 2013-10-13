package de.spqrinfo.quotes.gwt.quotes.client.listtagquotes;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;
import com.google.gwt.place.shared.Prefix;

public class ListTagQuotesPlace extends Place {

    private final String tagName;

    public ListTagQuotesPlace(final String token) {
        this.tagName = token;
    }

    public String getTagName() {
        return this.tagName;
    }

    @Prefix("tagQuotes")
    public static class Tokenizer implements PlaceTokenizer<ListTagQuotesPlace> {

        @Override
        public String getToken(final ListTagQuotesPlace place) {
            return place.tagName;
        }

        @Override
        public ListTagQuotesPlace getPlace(final String token) {
            return new ListTagQuotesPlace(token);
        }
    }
}
