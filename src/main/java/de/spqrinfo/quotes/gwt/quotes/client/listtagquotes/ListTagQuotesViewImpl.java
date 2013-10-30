package de.spqrinfo.quotes.gwt.quotes.client.listtagquotes;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.HeadingElement;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import de.spqrinfo.quotes.gwt.quotes.client.widgets.AuthorClickHandler;
import de.spqrinfo.quotes.gwt.quotes.client.widgets.QuotationClickHandler;
import de.spqrinfo.quotes.gwt.quotes.client.widgets.QuotationWidget;
import de.spqrinfo.quotes.gwt.quotes.shared.KeyedQuotationCollection;
import de.spqrinfo.quotes.gwt.quotes.shared.Quotation;
import de.spqrinfo.quotes.gwt.quotes.shared.QuotationTag;

public class ListTagQuotesViewImpl extends Composite implements ListTagQuotesView {

    @SuppressWarnings("FieldCanBeLocal")
    private Presenter presenter;

    interface ListTagQuotesViewImplUiBinder extends UiBinder<HTMLPanel, ListTagQuotesViewImpl> {
    }

    private static final ListTagQuotesViewImplUiBinder ourUiBinder = GWT.create(ListTagQuotesViewImplUiBinder.class);

    @UiField
    HTMLPanel listQuotes;

    @UiField
    HeadingElement header;

    public ListTagQuotesViewImpl() {
        initWidget(ourUiBinder.createAndBindUi(this));
    }

    @Override
    public void setData(final KeyedQuotationCollection<QuotationTag> quotations,
                        final QuotationClickHandler quotationClickHandler,
                        final AuthorClickHandler authorClickHandler) {
        this.header.setInnerText("Your quotes"); // TODO i18n
        this.listQuotes.clear();

        for (final Quotation quot : quotations.getQuotations()) {
            final QuotationWidget quotationWidget = new QuotationWidget(quot, quotationClickHandler, authorClickHandler);
            this.listQuotes.add(quotationWidget);
        }
    }

    @Override
    public void setPresenter(final Presenter presenter) {
        this.presenter = presenter;
    }
}
