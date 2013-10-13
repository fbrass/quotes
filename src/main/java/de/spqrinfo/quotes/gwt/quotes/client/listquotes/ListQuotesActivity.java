package de.spqrinfo.quotes.gwt.quotes.client.listquotes;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import de.spqrinfo.quotes.gwt.quotes.mvp.ClientFactory;
import de.spqrinfo.quotes.gwt.quotes.shared.Quotation;
import de.spqrinfo.quotes.gwt.quotes.shared.QuotesService;
import de.spqrinfo.quotes.gwt.quotes.shared.QuotesServiceAsync;

import java.util.List;

public class ListQuotesActivity extends AbstractActivity implements ListQuotesView.Presenter {

    private final ClientFactory clientFactory;

    public ListQuotesActivity(final ListQuotesPlace place, final ClientFactory clientFactory) {
        this.clientFactory = clientFactory;
    }

    @Override
    public void start(final AcceptsOneWidget panel, final EventBus eventBus) {
        final ListQuotesView view = this.clientFactory.getListQuotesView();
        view.setPresenter(this);
        panel.setWidget(view.asWidget());

        final QuotesServiceAsync quotesService = GWT.create(QuotesService.class);
        populateAndInitView(quotesService, view);
    }

    private void populateAndInitView(final QuotesServiceAsync quotesService, final ListQuotesView view) {

        quotesService.getQuotes(new AsyncCallback<List<Quotation>>() {
            @Override
            public void onFailure(final Throwable caught) {
                GWT.log("Failed to get quotes"); // TODO indicate error somehow
            }

            @Override
            public void onSuccess(final List<Quotation> quotes) {
                view.setData(quotes);
            }
        });
    }

    @Override
    public void goTo(final Place place) {
        this.clientFactory.getPlaceController().goTo(place);
    }
}
