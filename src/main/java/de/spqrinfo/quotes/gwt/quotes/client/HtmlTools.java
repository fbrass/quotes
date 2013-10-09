package de.spqrinfo.quotes.gwt.quotes.client;

import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.LabelElement;
import com.google.gwt.user.client.ui.UIObject;
import com.google.gwt.user.client.ui.Widget;

public class HtmlTools {

    private HtmlTools() {}

    public static String associateLabel(final Widget widget, final LabelElement label) {
        final String uniqueId = setUniqueId(widget);
        label.setHtmlFor(uniqueId);
        return uniqueId;
    }

    public static String setUniqueId(final UIObject uiObject) {
        final String uniqueId = Document.get().createUniqueId();
        setAttribute(uiObject, "id", uniqueId);
        return uniqueId;
    }

    public static void setPlaceholder(final UIObject uiObject, final String role) {
        setAttribute(uiObject, "placeholder", role);
    }

    private static void setAttribute(final UIObject uiObject, final String name, final String value) {
        uiObject.getElement().setAttribute(name, value);
    }
}
