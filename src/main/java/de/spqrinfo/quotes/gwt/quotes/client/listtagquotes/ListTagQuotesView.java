package de.spqrinfo.quotes.gwt.quotes.client.listtagquotes;

import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.ui.IsWidget;

public interface ListTagQuotesView extends IsWidget {

    void setData();
    void setPresenter(Presenter presenter);

    public interface Presenter {
        void goTo(Place place);
    }
}
