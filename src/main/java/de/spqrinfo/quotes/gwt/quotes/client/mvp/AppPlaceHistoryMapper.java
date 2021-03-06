package de.spqrinfo.quotes.gwt.quotes.client.mvp;

import com.google.gwt.place.shared.PlaceHistoryMapper;
import com.google.gwt.place.shared.WithTokenizers;
import de.spqrinfo.quotes.gwt.quotes.client.edit.EditQuotePlace;
import de.spqrinfo.quotes.gwt.quotes.client.listauthorquotes.ListAuthorQuotesPlace;
import de.spqrinfo.quotes.gwt.quotes.client.listquotes.ListQuotesPlace;
import de.spqrinfo.quotes.gwt.quotes.client.listtagquotes.ListTagQuotesPlace;
import de.spqrinfo.quotes.gwt.quotes.client.showquote.ShowQuotePlace;

@WithTokenizers({
        EditQuotePlace.Tokenizer.class,
        ListQuotesPlace.Tokenizer.class,
        ListAuthorQuotesPlace.Tokenizer.class,
        ListTagQuotesPlace.Tokenizer.class,
        ShowQuotePlace.Tokenzier.class
})
public interface AppPlaceHistoryMapper extends PlaceHistoryMapper {
}
