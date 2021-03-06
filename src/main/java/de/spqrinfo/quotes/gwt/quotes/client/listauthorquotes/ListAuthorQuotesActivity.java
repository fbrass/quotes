package de.spqrinfo.quotes.gwt.quotes.client.listauthorquotes;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import de.spqrinfo.quotes.gwt.quotes.client.mvp.ClientFactory;
import de.spqrinfo.quotes.gwt.quotes.client.showquote.ShowQuotePlace;
import de.spqrinfo.quotes.gwt.quotes.client.widgets.QuotationClickHandler;
import de.spqrinfo.quotes.gwt.quotes.shared.*;

public class ListAuthorQuotesActivity extends AbstractActivity implements ListAuthorQuotesView.Controller {

    private final Integer authorId;
    private final ClientFactory clientFactory;

    public ListAuthorQuotesActivity(final ListAuthorQuotesPlace place, final ClientFactory clientFactory) {
        this.clientFactory = clientFactory;
        this.authorId = place.getAuthorId();
    }

    @Override
    public void start(final AcceptsOneWidget panel, final EventBus eventBus) {
        final ListAuthorQuotesView view = this.clientFactory.getListAuthorQuotesView();
        view.setPresenter(this);
        panel.setWidget(view.asWidget());

        final QuotesServiceAsync quotesService = GWT.create(QuotesService.class);
        quotesService.getQuotesByAuthor(this.authorId, new AsyncCallback<KeyedQuotationCollection<QuotationAuthor>>() {
            @Override
            public void onFailure(final Throwable caught) {
                GWT.log("Failed to get quotes by author");
            }

            @Override
            public void onSuccess(final KeyedQuotationCollection<QuotationAuthor> quotations) {
                view.setData(quotations, new QuotationClickHandler() {
                    @Override
                    public void onClick(final Quotation quotation) {
                        final Place place = new ShowQuotePlace(quotation.getId());
                        ListAuthorQuotesActivity.this.clientFactory.getPlaceController().goTo(place);
                    }
                });
            }
        });
    }
}
