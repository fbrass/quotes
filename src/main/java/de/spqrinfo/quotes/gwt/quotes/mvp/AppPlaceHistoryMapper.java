package de.spqrinfo.quotes.gwt.quotes.mvp;

import com.google.gwt.place.shared.PlaceHistoryMapper;
import com.google.gwt.place.shared.WithTokenizers;
import de.spqrinfo.quotes.gwt.quotes.client.EditQuotePlace;
import de.spqrinfo.quotes.gwt.quotes.client.GoodByPlace;

@WithTokenizers({EditQuotePlace.Tokenizer.class, GoodByPlace.Tokenizer.class})
public interface AppPlaceHistoryMapper extends PlaceHistoryMapper {
}
