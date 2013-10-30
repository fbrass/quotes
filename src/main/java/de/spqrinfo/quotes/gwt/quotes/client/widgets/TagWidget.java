package de.spqrinfo.quotes.gwt.quotes.client.widgets;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import de.spqrinfo.quotes.gwt.quotes.shared.QuotationTag;

import java.util.List;

public class TagWidget extends Composite {

    interface TagWidgetUiBinder extends UiBinder<HTMLPanel, TagWidget> {
    }

    private static final TagWidgetUiBinder uiBinder = GWT.create(TagWidgetUiBinder.class);

    @UiField
    HTMLPanel tags;

    public TagWidget() {
        initWidget(uiBinder.createAndBindUi(this));
    }

    public void setTags(final List<QuotationTag> tags, final TagClickHandler tagClickHandler) {
        this.tags.clear();
        final int size = tags.size();
        for (int n = 0; n < size; n++) {
            final QuotationTag tag = tags.get(n);
            final String text = n + 1 < size ? tag.getTagName() + ',' : tag.getTagName();
            final Anchor anchor = new Anchor(text);
            anchor.addClickHandler(new ClickHandler() {
                @Override
                public void onClick(final ClickEvent event) {
                    tagClickHandler.onClick(tag);
                }
            });
            this.tags.add(anchor);
        }
    }
}
