package de.spqrinfo.quotes.gwt.quotes.client.listauthorquotes;

import com.google.gwt.user.client.ui.IsWidget;
import de.spqrinfo.quotes.gwt.quotes.client.mvp.GoToPresenter;
import de.spqrinfo.quotes.gwt.quotes.shared.QuotationsOfAuthor;

public interface ListAuthorQuotesView extends IsWidget {

    void setData(QuotationsOfAuthor quotations);
    void setPresenter(Presenter presenter);

    public interface Presenter extends GoToPresenter{
    }
}
