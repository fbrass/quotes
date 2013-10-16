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
import de.spqrinfo.quotes.gwt.quotes.client.widgets.InformationWidget;
import de.spqrinfo.quotes.gwt.quotes.client.mvp.AppActivityMapper;
import de.spqrinfo.quotes.gwt.quotes.client.mvp.AppPlaceHistoryMapper;
import de.spqrinfo.quotes.gwt.quotes.client.mvp.ClientFactory;
import de.spqrinfo.quotes.gwt.quotes.shared.QuotesService;
import de.spqrinfo.quotes.gwt.quotes.shared.QuotesServiceAsync;

public class QuotesEntryPoint implements EntryPoint {

    private final QuotesServiceAsync quotesService = GWT.create(QuotesService.class);

    @Override
    public void onModuleLoad() {
        final SimplePanel topNotificationPanel = new SimplePanel();
        topNotificationPanel.setStyleName("container");

        final SimplePanel viewsPanel = new SimplePanel();
        viewsPanel.setStyleName("container");

        // Setup MVP
        final Place defaultPlace = new ListQuotesPlace(""); // TODO empty string

        final ClientFactory clientFactory = GWT.create(ClientFactory.class);
        final EventBus eventBus = clientFactory.getEventBus();
        final PlaceController placeController = clientFactory.getPlaceController();

        final ActivityMapper activityMapper = new AppActivityMapper(clientFactory);
        final ActivityManager activityManager = new ActivityManager(activityMapper, eventBus);
        activityManager.setDisplay(viewsPanel);

        final AppPlaceHistoryMapper historyMapper = GWT.create(AppPlaceHistoryMapper.class);
        final PlaceHistoryHandler historyHandler = new PlaceHistoryHandler(historyMapper);
        historyHandler.register(placeController, eventBus, defaultPlace);

        // Register app event handler
        clientFactory.getEventBus().addHandler(QuotesAppEvent.TYPE, new QuotesAppEventHandler() {
            @Override
            public void onAppEvent(final QuotesAppEvent event) {
                GWT.log("Got QuotesAppEvent");
                final InformationWidget informationWidget = new InformationWidget();
                topNotificationPanel.add(informationWidget);
                informationWidget.setMessage("Got QuotesAppEvent");
            }
        });

        // Setup the root panel
        RootPanel.get().add(topNotificationPanel);
        RootPanel.get().add(viewsPanel);
        historyHandler.handleCurrentHistory();
    }
}
