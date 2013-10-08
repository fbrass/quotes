package de.spqrinfo.quotes.util;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import javax.servlet.ServletException;

public abstract class SpringRemoteServiceServlet extends RemoteServiceServlet {

    @Override
    public void init() throws ServletException {
        super.init();
        SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this, getServletContext());
    }
}
