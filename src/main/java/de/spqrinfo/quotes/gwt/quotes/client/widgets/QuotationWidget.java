package de.spqrinfo.quotes.gwt.quotes.client.widgets;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.ParagraphElement;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.InlineLabel;
import de.spqrinfo.quotes.gwt.quotes.shared.Quotation;

public class QuotationWidget extends Composite {

    interface QuoteWidgetUiBinder extends UiBinder<HTMLPanel, QuotationWidget> {

    }

    private static final QuoteWidgetUiBinder ourUiBinder = GWT.create(QuoteWidgetUiBinder.class);

    @UiField
    HTMLPanel quotationTextContainer;

    @UiField
    ParagraphElement quotationText;

    @UiField
    InlineLabel lastModifiedLabel;

//    @UiField
//    Anchor authorLink;
    @UiField
    AuthorWidget author;

//    private final Quotation quotation;
//    private final AuthorClickHandler authorClickHandler;

    public QuotationWidget(final Quotation quotation,
                           final QuotationClickHandler quotationClickHandler,
                           final AuthorClickHandler authorClickHandler) {
//        this.quotation = quotation;
//        this.authorClickHandler = authorClickHandler;

        initWidget(ourUiBinder.createAndBindUi(this));

        this.quotationText.setInnerText(quotation.getQuotationText());
        final DateTimeFormat format = DateTimeFormat.getFormat(DateTimeFormat.PredefinedFormat.YEAR_MONTH_ABBR_DAY);
        this.lastModifiedLabel.setText(format.format(quotation.getLastModified()));
//        this.authorLink.setText(quotation.getAuthor().getName());
        this.author.setAuthor(quotation.getAuthor());
        this.author.setAuthorClickHandler(authorClickHandler);

        // Cannot use @UiHandler on HTMLPanel
        this.quotationTextContainer.addDomHandler(new ClickHandler() {
            @Override
            public void onClick(final ClickEvent event) {
                quotationClickHandler.onClick(quotation);
            }
        }, ClickEvent.getType());

        // Conditionally show the author link
//        if (quotationAuthorClickHandler == null) {
//            this.authorLink.setVisible(false);
//        }
    }

//    @UiHandler("authorLink")
//    void authorLinkClick(final ClickEvent event) {
//        this.quotationAuthorClickHandler.onClick(this.quotation.getAuthor());
//    }
}
