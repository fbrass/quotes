package de.spqrinfo.quotes.gwt.quotes.client.listtagquotes;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import de.spqrinfo.quotes.gwt.quotes.client.listauthorquotes.ListAuthorQuotesPlace;
import de.spqrinfo.quotes.gwt.quotes.client.mvp.ClientFactory;
import de.spqrinfo.quotes.gwt.quotes.client.showquote.ShowQuotePlace;
import de.spqrinfo.quotes.gwt.quotes.client.widgets.AuthorClickHandler;
import de.spqrinfo.quotes.gwt.quotes.client.widgets.QuotationClickHandler;
import de.spqrinfo.quotes.gwt.quotes.shared.*;

public class ListTagQuotesActivity extends AbstractActivity implements ListTagQuotesView.Presenter {

    private final ListTagQuotesPlace place;
    private final ClientFactory clientFactory;

    public ListTagQuotesActivity(final ListTagQuotesPlace place, final ClientFactory clientFactory) {
        this.place = place;
        this.clientFactory = clientFactory;
    }

    @Override
    public void start(final AcceptsOneWidget panel, final EventBus eventBus) {
        final ListTagQuotesView view = this.clientFactory.getListTagQuotesView();
        view.setPresenter(this);

        final QuotesServiceAsync quotesService = GWT.create(QuotesService.class);
        quotesService.getQuotesByTagName(place.getTagName(), new AsyncCallback<KeyedQuotationCollection<QuotationTag>>() {
            @Override
            public void onFailure(final Throwable caught) {
                GWT.log("Failed to get quotes"); // TODO indicate error somehow
            }

            @Override
            public void onSuccess(final KeyedQuotationCollection<QuotationTag> result) {
                view.setData(result, new QuotationClickHandler() {
                            @Override
                            public void onClick(final Quotation quotation) {
                                singleQuoteSelected(quotation);
                            }
                        }, new AuthorClickHandler() {
                            @Override
                            public void onClick(final QuotationAuthor quotationAuthor) {
                                authorClick(quotationAuthor);
                            }
                        }
                );
            }
        });

        panel.setWidget(view);
    }

    private void singleQuoteSelected(final Quotation quotation) {
        this.clientFactory.getPlaceController().goTo(new ShowQuotePlace(quotation.getId()));
    }

    private void authorClick(final QuotationAuthor quotationAuthor) {
        this.clientFactory.getPlaceController().goTo(new ListAuthorQuotesPlace(quotationAuthor.getId()));
    }

    @Override
    public void goTo(final Place place) {
        this.clientFactory.getPlaceController().goTo(place);
    }
}
