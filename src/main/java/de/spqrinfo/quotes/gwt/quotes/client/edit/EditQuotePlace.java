package de.spqrinfo.quotes.gwt.quotes.client.edit;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;
import com.google.gwt.place.shared.Prefix;

public class EditQuotePlace extends Place {

    private Integer quotationId;

    public EditQuotePlace(final String token) {
        this.quotationId = Integer.valueOf(token);
    }

    public EditQuotePlace(final Integer quotationId) {
        this.quotationId = quotationId;
    }

    public Integer getQuotationId() {
        return this.quotationId;
    }

    @Prefix("edit")
    public static class Tokenizer implements PlaceTokenizer<EditQuotePlace> {

        @Override
        public String getToken(final EditQuotePlace place) {
            return String.valueOf(place.quotationId);
        }

        @Override
        public EditQuotePlace getPlace(final String token) {
            return new EditQuotePlace(token);
        }
    }
}
