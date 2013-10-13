package de.spqrinfo.quotes.gwt.quotes.client.widgets;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class RatingWidget extends Composite {

    private static final String CSS_STARRED = "btn-info";

    interface RatingWidgetUiBinder extends UiBinder<Widget, RatingWidget> {
    }

    private static RatingWidgetUiBinder ourUiBinder = GWT.create(RatingWidgetUiBinder.class);

    @UiField
    Button starButton1;

    @UiField
    Button starButton2;

    @UiField
    Button starButton3;

    @UiField
    Button starButton4;

    @UiField
    Button starButton5;

    private int rating = -1; // -1=disabled, enabled=0,1,2,3,4

    private final Button[] buttons;

    public RatingWidget() {
        initWidget(ourUiBinder.createAndBindUi(this));

        this.buttons = new Button[]{this.starButton1, this.starButton2, this.starButton3, this.starButton4, this.starButton5};

        for (int i = 0; i < this.buttons.length; i++) {
            final Button b = this.buttons[i];
            final int buttonIndex = i;
            b.addClickHandler(new ClickHandler() {
                @Override
                public void onClick(final ClickEvent clickEvent) {
                    toggleButtons(buttonIndex);
                }
            });
        }
    }

    public boolean hasRating() {
        return this.rating >= 0;
    }

    public int getRating() {
        return this.rating + 1;
    }

    public void setRating(final Integer rating) {
        if (rating == null) {
            toggleButtons(this.rating);
            return;
        }

        if (rating < 1 || rating > this.buttons.length) {
            throw new IllegalArgumentException("Rating out of range " + rating);
        }

        if (hasRating()) {
            toggleButtons(this.rating);
        }

        toggleButtons(rating - 1);
    }

    private void toggleButtons(final int buttonIndex) {
        assert buttonIndex < this.buttons.length;

        if (buttonIndex < this.rating) {
            // e.g. rating = 4, buttonIndex = 3 => disable button 4
            for (int n = this.rating; n > buttonIndex; n--) {
                toggleStarred(n, false);
            }
            this.rating = buttonIndex;
        } else if (buttonIndex > this.rating) {
            // e.g. rating = 2, buttonIndex = 4 => enable button 3,4
            // e.g. rating = -1, buttonIndex = 2 => enable button 0,1,2
            for (int n = this.rating + 1; n <= buttonIndex; n++) {
                toggleStarred(n, true);
            }
            this.rating = buttonIndex;
        } else {
            // e.g. rating = 3, buttonIndex = 3 => disable button 1,2,3
            for (int n = 0; n <= buttonIndex; n++) {
                toggleStarred(n, false);
            }
            this.rating = -1;
        }
    }

    private void toggleStarred(final int buttonIndex, final boolean enable) {
        final Button button = this.buttons[buttonIndex];
        if (enable) {
            button.addStyleName(CSS_STARRED);
        } else {
            button.removeStyleName(CSS_STARRED);
        }
    }
}
