package de.spqrinfo.quotes.gwt.quotes.client.listauthorquotes;

import com.google.gwt.user.client.ui.IsWidget;
import de.spqrinfo.quotes.gwt.quotes.client.widgets.QuotationClickHandler;
import de.spqrinfo.quotes.gwt.quotes.shared.KeyedQuotationCollection;
import de.spqrinfo.quotes.gwt.quotes.shared.QuotationAuthor;

public interface ListAuthorQuotesView extends IsWidget {

    void setData(KeyedQuotationCollection<QuotationAuthor> quotationCollection,
                 QuotationClickHandler quotationClickHandler);

    void setPresenter(Controller presenter);

    public interface Controller {
    }
}
