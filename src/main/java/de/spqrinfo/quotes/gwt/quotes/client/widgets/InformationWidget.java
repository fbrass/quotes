package de.spqrinfo.quotes.gwt.quotes.client.widgets;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.SpanElement;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;

// TODO move to widgets package
public class InformationWidget extends Composite {

    interface InformationWidgetUiBinder extends UiBinder<HTMLPanel, InformationWidget> {
    }

    private static final InformationWidgetUiBinder ourUiBinder = GWT.create(InformationWidgetUiBinder.class);

    @UiField
    SpanElement message;

    @UiField
    Button closeButton;

    public InformationWidget() {
        initWidget(ourUiBinder.createAndBindUi(this));

        this.closeButton.getElement().setAttribute("data-dismiss", "alert");
        this.closeButton.getElement().setAttribute("aria-hidden", "true");
    }

    public void setMessage(final String text) {
        this.message.setInnerText(text);
    }

    @UiHandler("closeButton")
    void onCloseClick(final ClickEvent event) {
        this.removeFromParent();
    }
}
