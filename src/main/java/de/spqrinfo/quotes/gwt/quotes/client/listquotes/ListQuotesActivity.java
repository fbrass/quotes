package de.spqrinfo.quotes.gwt.quotes.client.listquotes;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import de.spqrinfo.quotes.gwt.quotes.client.edit.EditQuotePlace;
import de.spqrinfo.quotes.gwt.quotes.client.listauthorquotes.ListAuthorQuotesPlace;
import de.spqrinfo.quotes.gwt.quotes.client.mvp.ClientFactory;
import de.spqrinfo.quotes.gwt.quotes.client.widgets.QuotationAuthorClickHandler;
import de.spqrinfo.quotes.gwt.quotes.client.widgets.QuotationClickHandler;
import de.spqrinfo.quotes.gwt.quotes.shared.Quotation;
import de.spqrinfo.quotes.gwt.quotes.shared.QuotationAuthor;
import de.spqrinfo.quotes.gwt.quotes.shared.QuotesService;
import de.spqrinfo.quotes.gwt.quotes.shared.QuotesServiceAsync;

import java.util.ArrayList;
import java.util.List;

public class ListQuotesActivity extends AbstractActivity implements ListQuotesView.Presenter {

    private final ListQuotesPlace place;
    private final ClientFactory clientFactory;

    public ListQuotesActivity(final ListQuotesPlace place, final ClientFactory clientFactory) {
        this.place = place;
        this.clientFactory = clientFactory;
    }

    @Override
    public void start(final AcceptsOneWidget panel, final EventBus eventBus) {
        final ListQuotesView view = this.clientFactory.getListQuotesView();
        view.setPresenter(this);
        panel.setWidget(view.asWidget());

        final QuotesServiceAsync quotesService = GWT.create(QuotesService.class);

        if (this.place.showSingleQuote()) {
            quotesService.getQuote(this.place.getSingleQuoteId(), new AsyncCallback<Quotation>() {
                @Override
                public void onFailure(final Throwable caught) {
                    GWT.log("Failed to get single quote"); // TODO indicate error somehow
                }

                @Override
                public void onSuccess(final Quotation result) {
                    final List<Quotation> quotes = new ArrayList<Quotation>();
                    quotes.add(result);
                    view.setData(quotes, new QuotationClickHandler() {
                        @Override
                        public void onClick(final Quotation quotation) {
                            editSingleQuote(quotation);
                        }
                    }, new QuotationAuthorClickHandler() {
                        @Override
                        public void onClick(final QuotationAuthor quotationAuthor) {
                            authorClick(quotationAuthor);
                        }
                    });
                }
            });
        } else {
            quotesService.getQuotes(new AsyncCallback<List<Quotation>>() {
                @Override
                public void onFailure(final Throwable caught) {
                    GWT.log("Failed to get quotes"); // TODO indicate error somehow
                }

                @Override
                public void onSuccess(final List<Quotation> quotes) {
                    view.setData(quotes, new QuotationClickHandler() {
                        @Override
                        public void onClick(Quotation quotation) {
                            listSingleQuote(quotation);
                        }
                    }, new QuotationAuthorClickHandler() {
                        @Override
                        public void onClick(QuotationAuthor quotationAuthor) {
                            authorClick(quotationAuthor);
                        }
                    });
                }
            });
        }
    }

    private void editSingleQuote(final Quotation quotation) {
        this.clientFactory.getPlaceController().goTo(new EditQuotePlace(quotation.getId()));
    }

    private void authorClick(final QuotationAuthor quotationAuthor) {
        this.clientFactory.getPlaceController().goTo(new ListAuthorQuotesPlace(quotationAuthor.getId()));
    }

    private void listSingleQuote(final Quotation quotation) {
        this.clientFactory.getPlaceController().goTo(new ListQuotesPlace(quotation.getId()));
    }
}
