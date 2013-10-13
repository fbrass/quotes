package de.spqrinfo.quotes.gwt.quotes.client.edit;

import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.ui.IsWidget;
import de.spqrinfo.quotes.gwt.quotes.shared.Quotation;

public interface EditQuoteView extends IsWidget {

    void setData(String headerText, Quotation quotation);
    void setPresenter(Presenter presenter);

    public interface Presenter {
        void goTo(Place place);
        void save();
    }
}
