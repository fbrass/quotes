package de.spqrinfo.quotes.gwt.quotes.client.showquote;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import de.spqrinfo.quotes.gwt.quotes.client.listauthorquotes.ListAuthorQuotesPlace;
import de.spqrinfo.quotes.gwt.quotes.client.listtagquotes.ListTagQuotesPlace;
import de.spqrinfo.quotes.gwt.quotes.client.mvp.ClientFactory;
import de.spqrinfo.quotes.gwt.quotes.shared.*;

public class ShowQuoteActivity extends AbstractActivity implements ShowQuoteView.Presenter {

    private final Integer quotationId;
    private final ClientFactory clientFactory;

    public ShowQuoteActivity(final ShowQuotePlace place, final ClientFactory clientFactory) {
        this.quotationId = place.getQuotationId();
        this.clientFactory = clientFactory;
    }

    @Override
    public void start(final AcceptsOneWidget panel, final EventBus eventBus) {
        final ShowQuoteView view = this.clientFactory.getShowQuoteView();
        view.setPresenter(this);
        panel.setWidget(view.asWidget());

        if (this.quotationId == null) {
            return;
        }

        final QuotesServiceAsync quotesService = GWT.create(QuotesService.class);
        quotesService.getQuote(this.quotationId, new AsyncCallback<Quotation>() {
            @Override
            public void onFailure(final Throwable caught) {
                GWT.log("Failed to get quote by id"); // TODO reflect this in UI somehow
            }

            @Override
            public void onSuccess(final Quotation result) {
                view.setData(result);
            }
        });
    }

    @Override
    public void gotoTag(final QuotationTag tag) {
        this.clientFactory.getPlaceController().goTo(new ListTagQuotesPlace(tag.getTagName()));
    }

    @Override
    public void gotoAuthor(final QuotationAuthor author) {
        this.clientFactory.getPlaceController().goTo(new ListAuthorQuotesPlace(author.getId()));
    }
}
