package de.spqrinfo.quotes.gwt.quotes.client.listquotes;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;
import com.google.gwt.place.shared.Prefix;

public class ListQuotesPlace extends Place {

    public ListQuotesPlace(final String token) {
    }

    @Prefix("list")
    public static class Tokenizer implements PlaceTokenizer<ListQuotesPlace> {

        @Override
        public String getToken(final ListQuotesPlace place) {
            return "";
        }

        @Override
        public ListQuotesPlace getPlace(final String token) {
            return new ListQuotesPlace(token);
        }
    }
}
