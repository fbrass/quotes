package de.spqrinfo.quotes.gwt.quotes.client.listquotes;

import com.google.gwt.user.client.ui.IsWidget;
import de.spqrinfo.quotes.gwt.quotes.client.widgets.AuthorClickHandler;
import de.spqrinfo.quotes.gwt.quotes.client.widgets.QuotationClickHandler;
import de.spqrinfo.quotes.gwt.quotes.shared.Quotation;

import java.util.List;

public interface ListQuotesView extends IsWidget {

    void setData(List<Quotation> quotations,
                 QuotationClickHandler quotationClickHandler,
                 AuthorClickHandler authorClickHandler);

    void setPresenter(Presenter presenter);

    public interface Presenter {
    }
}
