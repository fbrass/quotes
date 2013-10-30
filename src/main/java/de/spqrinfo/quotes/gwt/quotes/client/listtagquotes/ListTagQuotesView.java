package de.spqrinfo.quotes.gwt.quotes.client.listtagquotes;

import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.ui.IsWidget;
import de.spqrinfo.quotes.gwt.quotes.client.widgets.AuthorClickHandler;
import de.spqrinfo.quotes.gwt.quotes.client.widgets.QuotationClickHandler;
import de.spqrinfo.quotes.gwt.quotes.shared.KeyedQuotationCollection;
import de.spqrinfo.quotes.gwt.quotes.shared.QuotationTag;

public interface ListTagQuotesView extends IsWidget {

    void setPresenter(Presenter presenter);
    void setData(KeyedQuotationCollection<QuotationTag> quotations, QuotationClickHandler quotationClickHandler,
                 AuthorClickHandler authorClickHandler);

    public interface Presenter {
        void goTo(Place place);
    }
}
