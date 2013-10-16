package de.spqrinfo.quotes.gwt.quotes.client.widgets;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.ParagraphElement;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.InlineLabel;
import de.spqrinfo.quotes.gwt.quotes.client.edit.EditQuotePlace;
import de.spqrinfo.quotes.gwt.quotes.client.listauthorquotes.ListAuthorQuotesPlace;
import de.spqrinfo.quotes.gwt.quotes.client.mvp.GoToPresenter;
import de.spqrinfo.quotes.gwt.quotes.shared.Quotation;

public class QuoteWidget extends Composite {

    interface QuoteWidgetUiBinder extends UiBinder<HTMLPanel, QuoteWidget> {

    }
    private static QuoteWidgetUiBinder ourUiBinder = GWT.create(QuoteWidgetUiBinder.class);
    @UiField
    HTMLPanel quotationTextContainer;

    @UiField
    ParagraphElement quotationText;

    @UiField
    InlineLabel lastModifiedLabel;

    @UiField
    Anchor authorLink;

    private Quotation quotation;

    private final GoToPresenter goToPresenter;

    public QuoteWidget(final Quotation quotation, final GoToPresenter goToPresenter) {
        initWidget(ourUiBinder.createAndBindUi(this));

        this.quotation = quotation;
        this.goToPresenter = goToPresenter;

        this.quotationText.setInnerText(quotation.getQuotationText());
        final DateTimeFormat format = DateTimeFormat.getFormat(DateTimeFormat.PredefinedFormat.YEAR_MONTH_ABBR_DAY);
        this.lastModifiedLabel.setText(format.format(quotation.getLastModified()));
        this.authorLink.setText(quotation.getAuthor().getName());

        // Cannot use @UiHandler on HTMLPanel
        this.quotationTextContainer.addDomHandler(new ClickHandler() {
            @Override
            public void onClick(final ClickEvent event) {
                goToPresenter.goTo(new EditQuotePlace(quotation.getId()));
            }
        }, ClickEvent.getType());
    }

    @UiHandler("authorLink")
    void authorLinkClick(final ClickEvent event) {
        this.goToPresenter.goTo(new ListAuthorQuotesPlace(this.quotation.getAuthor().getId()));
    }
}
