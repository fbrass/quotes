package de.spqrinfo.quotes.gwt.quotes.client;

import com.google.gwt.activity.shared.ActivityManager;
import com.google.gwt.activity.shared.ActivityMapper;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.place.shared.PlaceHistoryHandler;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.web.bindery.event.shared.EventBus;
import de.spqrinfo.quotes.gwt.quotes.client.listquotes.ListQuotesPlace;
import de.spqrinfo.quotes.gwt.quotes.mvp.AppActivityMapper;
import de.spqrinfo.quotes.gwt.quotes.mvp.AppPlaceHistoryMapper;
import de.spqrinfo.quotes.gwt.quotes.mvp.ClientFactory;
import de.spqrinfo.quotes.gwt.quotes.shared.QuotesService;
import de.spqrinfo.quotes.gwt.quotes.shared.QuotesServiceAsync;

public class QuotesEntryPoint implements EntryPoint {

    private final QuotesServiceAsync quotesService = GWT.create(QuotesService.class);

    @Override
    public void onModuleLoad() {
        final SimplePanel appWidget = new SimplePanel();
        appWidget.setStyleName("container");
        final Place defaultPlace = new ListQuotesPlace("iDontKnwoNowWhatToPlace-Here");

        final ClientFactory clientFactory = GWT.create(ClientFactory.class);
        final EventBus eventBus = clientFactory.getEventBus();
        final PlaceController placeController = clientFactory.getPlaceController();

        final ActivityMapper activityMapper = new AppActivityMapper(clientFactory);
        final ActivityManager activityManager = new ActivityManager(activityMapper, eventBus);
        activityManager.setDisplay(appWidget);

        final AppPlaceHistoryMapper historyMapper = GWT.create(AppPlaceHistoryMapper.class);
        final PlaceHistoryHandler historyHandler = new PlaceHistoryHandler(historyMapper);
        historyHandler.register(placeController, eventBus, defaultPlace);

        RootPanel.get().add(appWidget);
        historyHandler.handleCurrentHistory();
    }
}
