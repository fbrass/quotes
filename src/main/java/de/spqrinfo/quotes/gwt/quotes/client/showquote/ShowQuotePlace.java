package de.spqrinfo.quotes.gwt.quotes.client.showquote;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;
import com.google.gwt.place.shared.Prefix;

public class ShowQuotePlace extends Place {

    private final Integer quotationId;

    public ShowQuotePlace(final Integer quotationId) {
        this.quotationId = quotationId;
    }

    public ShowQuotePlace(final String token) {
        this.quotationId = Integer.valueOf(token);
    }


    public Integer getQuotationId() {
        return this.quotationId;
    }

    @Prefix("show")
    public static class Tokenzier implements PlaceTokenizer<ShowQuotePlace> {
        @Override
        public ShowQuotePlace getPlace(final String token) {
            return new ShowQuotePlace(token);
        }

        @Override
        public String getToken(final ShowQuotePlace place) {
            return Integer.toString(place.quotationId);
        }
    }
}
