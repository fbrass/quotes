package de.spqrinfo.quotes.gwt.quotes.client.listtagquotes;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;

public class ListTagQuotesViewImpl extends Composite implements ListTagQuotesView {

    @SuppressWarnings("FieldCanBeLocal")
    private Presenter presenter;

    interface ListTagQuotesViewImplUiBinder extends UiBinder<HTMLPanel, ListTagQuotesViewImpl> {
    }

    private static ListTagQuotesViewImplUiBinder ourUiBinder = GWT.create(ListTagQuotesViewImplUiBinder.class);

    public ListTagQuotesViewImpl() {
        initWidget(ourUiBinder.createAndBindUi(this));
    }

    @Override
    public void setData() {
    }

    @Override
    public void setPresenter(final Presenter presenter) {
        this.presenter = presenter;
    }
}
