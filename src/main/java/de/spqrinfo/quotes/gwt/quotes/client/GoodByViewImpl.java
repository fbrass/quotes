package de.spqrinfo.quotes.gwt.quotes.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.SpanElement;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;

public class GoodByViewImpl extends Composite implements GoodByeView {

    interface GoodByViewImplUiBinder extends UiBinder<HTMLPanel, GoodByViewImpl> {
    }

    private static GoodByViewImplUiBinder ourUiBinder = GWT.create(GoodByViewImplUiBinder.class);

    private Presenter presenter;

    @UiField
    SpanElement text;

    public GoodByViewImpl() {
        initWidget(ourUiBinder.createAndBindUi(this));
    }

    @Override
    public void setData(final String name) {
        this.text.setInnerText(name);
    }

    @Override
    public void setPresenter(final Presenter presenter) {
        this.presenter = presenter;
    }
}
