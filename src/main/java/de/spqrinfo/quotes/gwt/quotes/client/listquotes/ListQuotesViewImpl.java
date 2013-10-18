package de.spqrinfo.quotes.gwt.quotes.client.listquotes;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.HeadingElement;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import de.spqrinfo.quotes.gwt.quotes.client.widgets.QuotationAuthorClickHandler;
import de.spqrinfo.quotes.gwt.quotes.client.widgets.QuotationClickHandler;
import de.spqrinfo.quotes.gwt.quotes.client.widgets.QuotationWidget;
import de.spqrinfo.quotes.gwt.quotes.shared.Quotation;

import java.util.List;

public class ListQuotesViewImpl extends Composite implements ListQuotesView {

    interface QuotesListViewImplUiBinder extends UiBinder<HTMLPanel, ListQuotesViewImpl> {
    }
    private static QuotesListViewImplUiBinder ourUiBinder = GWT.create(QuotesListViewImplUiBinder.class);

    @SuppressWarnings("FieldCanBeLocal")
    private Presenter presenter;

    @UiField
    HeadingElement header;

    @UiField
    HTMLPanel listQuotes;

    public ListQuotesViewImpl() {
        initWidget(ourUiBinder.createAndBindUi(this));
    }

    @Override
    public void setData(final List<Quotation> quotations,
                        final QuotationClickHandler quotationClickHandler,
                        final QuotationAuthorClickHandler quotationAuthorClickHandler) {
        this.header.setInnerText("Your quotes"); // TODO i18n
        this.listQuotes.clear();

        for (final Quotation quot : quotations) {
            final QuotationWidget quotationWidget = new QuotationWidget(quot, quotationClickHandler, quotationAuthorClickHandler);
            this.listQuotes.add(quotationWidget);
        }
    }

    @Override
    public void setPresenter(final Presenter presenter) {
        this.presenter = presenter;
    }
}
