package de.spqrinfo.quotes.gwt.quotes.client.showquote;

import com.google.gwt.user.client.ui.IsWidget;
import de.spqrinfo.quotes.gwt.quotes.shared.Quotation;
import de.spqrinfo.quotes.gwt.quotes.shared.QuotationAuthor;
import de.spqrinfo.quotes.gwt.quotes.shared.QuotationTag;

public interface ShowQuoteView extends IsWidget {

    void setPresenter(Presenter presenter);
    void setData(Quotation quotation);

    public interface Presenter {
        void gotoTag(QuotationTag tag);
        void gotoAuthor(QuotationAuthor author);
    }
}
