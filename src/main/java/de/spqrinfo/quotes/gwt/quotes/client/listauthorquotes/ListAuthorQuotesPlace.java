package de.spqrinfo.quotes.gwt.quotes.client.listauthorquotes;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;
import com.google.gwt.place.shared.Prefix;

public class ListAuthorQuotesPlace extends Place {

    private final Integer authorId;

    public ListAuthorQuotesPlace(final String token) {
        this.authorId = Integer.valueOf(token);
    }

    public ListAuthorQuotesPlace(final Integer authorId) {
        this.authorId = authorId;
    }

    public Integer getAuthorId() {
        return this.authorId;
    }

    @Prefix("author")
    public static class Tokenizer implements PlaceTokenizer<ListAuthorQuotesPlace> {

        @Override
        public String getToken(final ListAuthorQuotesPlace place) {
            return String.valueOf(place.authorId);
        }

        @Override
        public ListAuthorQuotesPlace getPlace(final String token) {
            return new ListAuthorQuotesPlace(token);
        }
    }
}
