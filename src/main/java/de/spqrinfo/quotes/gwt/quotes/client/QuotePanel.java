package de.spqrinfo.quotes.gwt.quotes.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.HeadingElement;
import com.google.gwt.dom.client.LabelElement;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.*;

public class QuotePanel extends Composite {

    interface MyUiBinder extends UiBinder<Widget, QuotePanel> {
    }

    private static MyUiBinder uiBinder = GWT.create(MyUiBinder.class);

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

    public QuotePanel(final String name) {
        initWidget(uiBinder.createAndBindUi(this));

        this.heading.setInnerText(name);

        HtmlTools.associateLabel(this.quoteText, this.quoteTextLabel);
        this.quoteTextLabel.setInnerText("Quote"); // TODO i18n
        HtmlTools.setPlaceholder(this.quoteText, "Quote text"); // TODO i18n

        HtmlTools.associateLabel(this.author, this.authorLabel);
        this.authorLabel.setInnerText("Author"); // TODO i18n
        HtmlTools.setPlaceholder(this.author, "Author"); // TODO i18n
        this.authorBrowseButton.setText("Select"); // TODO i18n

        HtmlTools.associateLabel(this.ratingWidget, this.ratingLabel); // TODO rename associatelabel and swap parameters
        this.ratingLabel.setInnerText("Rating"); // TODO i18n

        HtmlTools.associateLabel(this.tags, this.tagsLabel);
        this.tagsLabel.setInnerText("Tags"); // TODO i18n
        HtmlTools.setPlaceholder(this.tags, "tags - space separated");
        this.tagsBrowseButton.setText("Select"); // TODO i18n
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
        Window.alert("Save");
    }
}
