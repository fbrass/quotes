package de.spqrinfo.quotes.gwt.quotes.server;

import de.spqrinfo.quotes.gwt.quotes.client.QuotesService;
import de.spqrinfo.quotes.util.SpringRemoteServiceServlet;

public class QuotesServiceImpl extends SpringRemoteServiceServlet implements QuotesService {

    @Override
    public String first(final String inputText) throws IllegalArgumentException {
        if (null == inputText || 0 == inputText.length()) {
            throw new IllegalArgumentException("Name must be at least 4 characters long");
        }

        return escapeHtml("Server feedback " + inputText);
    }

    private String escapeHtml(String html) {
        if (html == null) {
            return null;
        }
        return html.replaceAll("&", "&amp;").replaceAll("<", "&lt;").replaceAll(">", "&gt;");
    }
}
