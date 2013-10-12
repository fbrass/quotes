package de.spqrinfo.quotes.gwt.quotes.client;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.ui.AcceptsOneWidget;

public class GoodByActivity extends AbstractActivity implements GoodByeView.Presenter {

    private final String text;
    private final ClientFactory clientFactory;

    public GoodByActivity(final GoodByPlace place, final ClientFactory clientFactory) {
        this.text = place.getText();
        this.clientFactory = clientFactory;
    }

    @Override
    public void start(final AcceptsOneWidget panel, final EventBus eventBus) {
        final GoodByeView view = this.clientFactory.getEdGoodByeView();
        view.setData(this.text);
        view.setPresenter(this);
        panel.setWidget(view.asWidget());
    }

    @Override
    public void goTo(final Place place) {
        this.clientFactory.getPlaceController().goTo(place);
    }
}
