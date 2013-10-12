package de.spqrinfo.quotes.gwt.quotes.client;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.ui.AcceptsOneWidget;

public class EditQuoteActivity extends AbstractActivity implements EditQuoteView.Presenter {

    private String name;
    private ClientFactory clientFactory;

    public EditQuoteActivity(final EditQuotePlace place, final ClientFactory clientFactory) {
        this.name = place.getName();
        this.clientFactory = clientFactory;
    }

    @Override
    public void start(final AcceptsOneWidget panel, final EventBus eventBus) {
        final EditQuoteView view = this.clientFactory.getEditQuoteView();
        view.setData(this.name);
        view.setPresenter(this);
        panel.setWidget(view.asWidget());
    }

    @Override
    public void goTo(final Place place) {
        this.clientFactory.getPlaceController().goTo(place);
    }
}
