package de.spqrinfo.quotes.gwt.quotes.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.SpanElement;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;

public class EditQuoteViewImpl extends Composite implements EditQuoteView {

    interface EditQuoteViewImplUiBinder extends UiBinder<HTMLPanel, EditQuoteViewImpl> {
    }

    private static EditQuoteViewImplUiBinder ourUiBinder = GWT.create(EditQuoteViewImplUiBinder.class);

    private Presenter presenter;

    @UiField
    SpanElement name;

    @UiField
    Anchor goodByeLink;

    public EditQuoteViewImpl() {
        initWidget(ourUiBinder.createAndBindUi(this));
    }

    @Override
    public void setData(final String name) {
        this.name.setInnerText(name);
    }

    @Override
    public void setPresenter(final Presenter presenter) {
        this.presenter = presenter;
    }

    @UiHandler("goodByeLink")
    void goodByOnClick(final ClickEvent event) {
        this.presenter.goTo(new GoodByPlace(this.name.getInnerText()));
    }
}
