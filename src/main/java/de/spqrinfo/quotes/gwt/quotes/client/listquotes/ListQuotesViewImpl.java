package de.spqrinfo.quotes.gwt.quotes.client.listquotes;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import de.spqrinfo.quotes.gwt.quotes.client.widgets.QuoteWidget;
import de.spqrinfo.quotes.gwt.quotes.shared.Quotation;

import java.util.Date;
import java.util.List;

public class ListQuotesViewImpl extends Composite implements ListQuotesView {

    interface QuotesListViewImplUiBinder extends UiBinder<HTMLPanel, ListQuotesViewImpl> {
    }
    private static QuotesListViewImplUiBinder ourUiBinder = GWT.create(QuotesListViewImplUiBinder.class);

    private Presenter presenter;

    @UiField
    HTMLPanel listQuotes;

    public ListQuotesViewImpl() {
        initWidget(ourUiBinder.createAndBindUi(this));
    }

    // TODO this does not belong to the view et al
    private static String lastModifiedAsString(final Date date) {
        return "Oct. 11";
    }

    @Override
    public void setData(final List<Quotation> quotations) {
        this.listQuotes.clear();

        for (final Quotation quot : quotations) {
            final QuoteWidget quoteWidget = new QuoteWidget(quot, this.presenter);
            this.listQuotes.add(quoteWidget);
        }
    }

    @Override
    public void setPresenter(final Presenter presenter) {
        this.presenter = presenter;
    }
}
