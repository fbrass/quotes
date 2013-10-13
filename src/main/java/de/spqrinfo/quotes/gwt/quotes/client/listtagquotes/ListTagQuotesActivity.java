package de.spqrinfo.quotes.gwt.quotes.client.listtagquotes;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import de.spqrinfo.quotes.gwt.quotes.mvp.ClientFactory;

public class ListTagQuotesActivity extends AbstractActivity implements ListTagQuotesView.Presenter {

    private final ClientFactory clientFactory;

    public ListTagQuotesActivity(final ListTagQuotesPlace place, final ClientFactory clientFactory) {
        // TODO do something with place
        this.clientFactory = clientFactory;
    }

    @Override
    public void start(final AcceptsOneWidget panel, final EventBus eventBus) {


    }

    @Override
    public void goTo(final Place place) {
        this.clientFactory.getPlaceController().goTo(place);
    }
}
