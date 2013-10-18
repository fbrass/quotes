package de.spqrinfo.quotes.gwt.quotes.client.listquotes;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;
import com.google.gwt.place.shared.Prefix;

/**
 * Place to show a list of quotes or a single quote.
 */
public class ListQuotesPlace extends Place {

    private Integer singleQuoteId;

    public ListQuotesPlace(final Integer singleQuoteId) {
        this.singleQuoteId = singleQuoteId;
    }

    public ListQuotesPlace() {
        this.singleQuoteId = null;
    }

    public ListQuotesPlace(final String token) {
        if (!token.isEmpty()) {
            this.singleQuoteId = Integer.valueOf(token);
        }
    }

    public boolean showSingleQuote() {
        return this.singleQuoteId != null;
    }

    public Integer getSingleQuoteId() {
        return this.singleQuoteId;
    }

    @Prefix("list")
    public static class Tokenizer implements PlaceTokenizer<ListQuotesPlace> {

        @Override
        public String getToken(final ListQuotesPlace place) {
            return place.singleQuoteId != null ? String.valueOf(place.singleQuoteId) : "";
        }

        @Override
        public ListQuotesPlace getPlace(final String token) {
            return new ListQuotesPlace(token);
        }
    }
}
