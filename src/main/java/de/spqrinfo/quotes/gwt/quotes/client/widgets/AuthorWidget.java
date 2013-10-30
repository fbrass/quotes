package de.spqrinfo.quotes.gwt.quotes.client.widgets;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import de.spqrinfo.quotes.gwt.quotes.shared.QuotationAuthor;

public class AuthorWidget extends Composite {

    interface AuthorWidgetUiBinder extends UiBinder<HTMLPanel, AuthorWidget> {
    }

    private static final AuthorWidgetUiBinder uiBinder = GWT.create(AuthorWidgetUiBinder.class);

    @UiField
    Anchor authorLink;

    private QuotationAuthor author;
    private AuthorClickHandler authorClickHandler;

    public AuthorWidget() {
        initWidget(uiBinder.createAndBindUi(this));
    }

    public void setAuthor(final QuotationAuthor author) {
        this.author = author;
        this.authorLink.setText(author.getName());
    }

    public void setAuthorClickHandler(final AuthorClickHandler authorClickHandler) {
        this.authorClickHandler = authorClickHandler;
    }

    @UiHandler("authorLink")
    public void handleClick(final ClickEvent event) {
        if (this.author != null && this.authorClickHandler != null) {
            this.authorClickHandler.onClick(this.author);
        }
    }
}
