package de.spqrinfo.quotes.gwt.quotes.client.showquote;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.DivElement;
import com.google.gwt.dom.client.ParagraphElement;
import com.google.gwt.dom.client.Style;
import com.google.gwt.event.logical.shared.ResizeEvent;
import com.google.gwt.event.logical.shared.ResizeHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import de.spqrinfo.quotes.gwt.quotes.client.widgets.AuthorClickHandler;
import de.spqrinfo.quotes.gwt.quotes.client.widgets.AuthorWidget;
import de.spqrinfo.quotes.gwt.quotes.client.widgets.TagClickHandler;
import de.spqrinfo.quotes.gwt.quotes.client.widgets.TagWidget;
import de.spqrinfo.quotes.gwt.quotes.shared.Quotation;
import de.spqrinfo.quotes.gwt.quotes.shared.QuotationAuthor;
import de.spqrinfo.quotes.gwt.quotes.shared.QuotationTag;

public class ShowQuoteViewImpl extends Composite implements ShowQuoteView {

    private static final int TOP_OFFSET_HEIGHT = 50;

    interface ShowQuoteViewImplUiBinder extends UiBinder<HTMLPanel, ShowQuoteViewImpl> {
    }

    private static final ShowQuoteViewImplUiBinder ourUiBinder = GWT.create(ShowQuoteViewImplUiBinder.class);

    private Presenter presenter;

    @UiField
    DivElement container;
    @UiField
    ParagraphElement text;
    @UiField
    AuthorWidget author;
    @UiField
    TagWidget tags;

    public ShowQuoteViewImpl() {
        initWidget(ourUiBinder.createAndBindUi(this));

        Window.addResizeHandler(new ResizeHandler() {
            @Override
            public void onResize(final ResizeEvent event) {
                centerContent(event.getHeight());
            }
        });
    }

    @Override
    public void setPresenter(final Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void setData(final Quotation quotation) {
        this.text.setInnerText(quotation.getQuotationText());

        this.author.setAuthor(quotation.getAuthor());
        this.author.setAuthorClickHandler(new AuthorClickHandler() {
            @Override
            public void onClick(final QuotationAuthor author) {
                presenter.gotoAuthor(author);
            }
        });
        this.tags.setTags(quotation.getTags(), new TagClickHandler() {
            @Override
            public void onClick(final QuotationTag tag) {
                presenter.gotoTag(tag);
            }
        });
        centerContent(0);
    }

    private void centerContent(final int height) {
        final float windowHeight;
        if (height == 0) {
            windowHeight = Window.getClientHeight();
        } else {
            windowHeight = height;
        }
        final float containerHeight = this.container.getClientHeight();

        final float top = windowHeight / 2 - containerHeight /  2 - (TOP_OFFSET_HEIGHT * 4 / 3);
        this.container.getStyle().setTop(top, Style.Unit.PX);
    }
}
