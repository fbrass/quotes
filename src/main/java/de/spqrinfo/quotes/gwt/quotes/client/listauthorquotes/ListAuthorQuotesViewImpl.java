package de.spqrinfo.quotes.gwt.quotes.client.listauthorquotes;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.HeadingElement;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import de.spqrinfo.quotes.gwt.quotes.client.widgets.QuotationClickHandler;
import de.spqrinfo.quotes.gwt.quotes.client.widgets.QuotationWidget;
import de.spqrinfo.quotes.gwt.quotes.shared.Quotation;
import de.spqrinfo.quotes.gwt.quotes.shared.QuotationsOfAuthor;

public class ListAuthorQuotesViewImpl extends Composite implements ListAuthorQuotesView {

    interface ListAuthorQuotesViewImplUiBinder extends UiBinder<HTMLPanel, ListAuthorQuotesViewImpl> {
    }

    private static ListAuthorQuotesViewImplUiBinder ourUiBinder = GWT.create(ListAuthorQuotesViewImplUiBinder.class);

    @UiField
    HeadingElement header;

    @UiField
    HTMLPanel listQuotes;

    @SuppressWarnings("FieldCanBeLocal")
    private Controller presenter;

    public ListAuthorQuotesViewImpl() {
        initWidget(ourUiBinder.createAndBindUi(this));
    }

    @Override
    public void setData(final QuotationsOfAuthor quotationsOfAuthor,
                        final QuotationClickHandler quotationClickHandler) {
        this.header.setInnerText("");
        this.listQuotes.clear();

        if (quotationsOfAuthor == null) {
            return;
        }

        this.header.setInnerText("Quotes of " + quotationsOfAuthor.getQuotationAuthor().getName()); // TODO i18n
        for (final Quotation quotation : quotationsOfAuthor.getQuotations()) {
            final QuotationWidget quotationWidget = new QuotationWidget(quotation, quotationClickHandler, null);
            this.listQuotes.add(quotationWidget);
        }
    }

    @Override
    public void setPresenter(final Controller presenter) {
        this.presenter = presenter;
    }
}
