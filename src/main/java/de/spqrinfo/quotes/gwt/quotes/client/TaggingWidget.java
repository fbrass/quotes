package de.spqrinfo.quotes.gwt.quotes.client;


import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.UListElement;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

public class TaggingWidget extends Composite {

    interface TaggingWidgetUiBinder extends UiBinder<Widget, TaggingWidget> {
    }

    private static TaggingWidgetUiBinder uiBinder = GWT.create(TaggingWidgetUiBinder.class);

    @UiField
    UListElement tagsList;

    @UiField
    TextBox textBox;

    public TaggingWidget() {
        initWidget(uiBinder.createAndBindUi(this));
    }

    @UiHandler("textBox")
    void textBoxKeyUp(final KeyUpEvent event) {
        GWT.log("keyUp " + event.getNativeKeyCode());
    }

    @UiHandler("textBox")
    void textBoxValueChanged(final ValueChangeEvent<String> event) {
        GWT.log("valueChanged " + event.getValue());
        addTagToList(event.getValue());
    }

    private void addTagToList(final String tag) {

        final InlineLabel label = new InlineLabel(tag);
        this.tagsList.appendChild(label.getElement());
        final UListElement li = UListElement.as(label.getElement());
    }
}
