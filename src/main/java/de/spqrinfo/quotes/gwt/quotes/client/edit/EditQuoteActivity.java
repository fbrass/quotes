package de.spqrinfo.quotes.gwt.quotes.client.edit;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import de.spqrinfo.quotes.gwt.quotes.client.QuotesAppEvent;
import de.spqrinfo.quotes.gwt.quotes.client.listquotes.ListQuotesPlace;
import de.spqrinfo.quotes.gwt.quotes.client.mvp.ClientFactory;
import de.spqrinfo.quotes.gwt.quotes.shared.Quotation;
import de.spqrinfo.quotes.gwt.quotes.shared.QuotesService;
import de.spqrinfo.quotes.gwt.quotes.shared.QuotesServiceAsync;

public class EditQuoteActivity extends AbstractActivity implements EditQuoteView.Presenter {

    private final Integer quotationId;
    private final ClientFactory clientFactory;
    private Quotation quotation;

    public EditQuoteActivity(final EditQuotePlace place, final ClientFactory clientFactory) {
        this.quotationId = place.getQuotationId();
        this.clientFactory = clientFactory;
    }

    @Override
    public void start(final AcceptsOneWidget panel, final EventBus eventBus) {
        final EditQuoteView view = this.clientFactory.getEditQuoteView();
        view.setPresenter(this);
        panel.setWidget(view.asWidget());

        if (this.quotationId == null) {
            this.quotation = null;
            return;
        }

        final QuotesServiceAsync quotesService = GWT.create(QuotesService.class);
        quotesService.getQuote(this.quotationId, new AsyncCallback<Quotation>() {
            @Override
            public void onFailure(final Throwable caught) {
                GWT.log("Failed to get quote by id"); // TODO reflect this in UI somehow
                EditQuoteActivity.this.quotation = null;
            }

            @Override
            public void onSuccess(final Quotation result) {
                EditQuoteActivity.this.quotation = result;
                view.setData("Edit quote", result); // TODO i18n
            }
        });
    }

    @Override
    public void save() {
        // TODO save quote

        // Notify application that we saved
        this.clientFactory.getEventBus().fireEvent(new QuotesAppEvent());

        // Navigate to next view
        this.clientFactory.getPlaceController().goTo(new ListQuotesPlace());
    }
}
