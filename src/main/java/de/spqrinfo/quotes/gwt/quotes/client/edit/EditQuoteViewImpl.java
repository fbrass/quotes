package de.spqrinfo.quotes.gwt.quotes.client.edit;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.HeadingElement;
import com.google.gwt.dom.client.LabelElement;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.*;
import de.spqrinfo.quotes.gwt.quotes.client.HtmlTools;
import de.spqrinfo.quotes.gwt.quotes.client.widgets.RatingWidget;
import de.spqrinfo.quotes.gwt.quotes.shared.Quotation;

public class EditQuoteViewImpl extends Composite implements EditQuoteView {

    interface EditQuoteViewImplUiBinder extends UiBinder<HTMLPanel, EditQuoteViewImpl> {
    }

    private static final EditQuoteViewImplUiBinder ourUiBinder = GWT.create(EditQuoteViewImplUiBinder.class);

    private Presenter presenter;

    @UiField
    HeadingElement heading;

    @UiField
    LabelElement quoteTextLabel;

    @UiField
    TextArea quoteText;

    @UiField
    LabelElement authorLabel;

    @UiField
    TextBox author;

    @UiField
    Button authorBrowseButton;

    @UiField
    LabelElement ratingLabel;

    @UiField
    RatingWidget ratingWidget;

    @UiField
    LabelElement tagsLabel;

    @UiField
    TextBox tags;

    @UiField
    Button tagsBrowseButton;

    @UiField
    Button saveButton;

    public EditQuoteViewImpl() {
        initWidget(ourUiBinder.createAndBindUi(this));

        HtmlTools.associateLabel(this.quoteText, this.quoteTextLabel);
        this.quoteTextLabel.setInnerText("Quote"); // TODO i18n
        HtmlTools.setPlaceholder(this.quoteText, "Quote text"); // TODO i18n

        HtmlTools.associateLabel(this.author, this.authorLabel);
        this.authorLabel.setInnerText("Author"); // TODO i18n
        HtmlTools.setPlaceholder(this.author, "Author"); // TODO i18n
        this.authorBrowseButton.setText("Select"); // TODO i18n

        HtmlTools.associateLabel(this.ratingWidget, this.ratingLabel);
        this.ratingLabel.setInnerText("Rating"); // TODO i18n

        HtmlTools.associateLabel(this.tags, this.tagsLabel);
        this.tagsLabel.setInnerText("Tags"); // TODO i18n
        HtmlTools.setPlaceholder(this.tags, "tags - space separated");
        this.tagsBrowseButton.setText("Select"); // TODO i18n
    }

    @Override
    public void setPresenter(final Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void setData(final String headingText, final Quotation quotation) {
        this.heading.setInnerText(headingText);
        this.quoteText.setText(quotation.getQuotationText());
        this.author.setText(quotation.getAuthor().getName());
        this.ratingWidget.setRating(quotation.getRating());
        this.tags.setText(quotation.getTagsAsText());
    }

    @UiHandler("authorBrowseButton")
    void authorBrowseClick(final ClickEvent e) {
        Window.alert("Browse authors");
    }

    @UiHandler("tagsBrowseButton")
    void tagsBrowseClick(final ClickEvent e) {
        Window.alert("Browse tags");
    }

    @UiHandler("saveButton")
    void saveClick(final ClickEvent e) {
        this.presenter.save();
    }
}
