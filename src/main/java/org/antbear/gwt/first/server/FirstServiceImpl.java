package org.antbear.gwt.first.server;

import org.antbear.gwt.first.client.FirstService;
import org.antbear.tododont.backend.dao.TodoListDao;
import org.antbear.tododont.backend.entity.TodoList;
import org.antbear.tododont.gwt.SpringRemoteServiceServlet;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * The server side implementation of the RPC service.
 */
@SuppressWarnings("serial")
//public class FirstServiceImpl extends RemoteServiceServlet implements FirstService {
public class FirstServiceImpl extends SpringRemoteServiceServlet implements FirstService {

    // TODO just for demonstration
    @Autowired
    private TodoListDao todoListDao;

    @Override
    public String first(final String inputText) throws IllegalArgumentException {
        if (null == inputText || 0 == inputText.length()) {
            throw new IllegalArgumentException("Name must be at least 4 characters long");
        }

        // TODO just for demonstration
        final List<TodoList> all = this.todoListDao.findAll();
        final int size = all.size() + 11;

        // Escape data from the client to avoid cross-site script vulnerabilities.
        return escapeHtml("Server feedback (" + size + ") " + inputText);
    }

    /**
     * Escape an html string. Escaping data received from the client helps to prevent
     * cross-site script vulnerabilities.
     *
     * @param html the html string to escape
     * @return the escaped string
     */
    private String escapeHtml(String html) {
        if (html == null) {
            return null;
        }
        return html.replaceAll("&", "&amp;").replaceAll("<", "&lt;").replaceAll(
                ">", "&gt;");
    }
}
