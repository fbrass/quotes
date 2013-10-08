package de.spqrinfo.quotes.web.controller.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LoginController {

    private static final Logger log = LoggerFactory.getLogger(LoginController.class);

    @RequestMapping("/login")
    public String login() {
        log.debug("loginHandler called");
        return "security/login";
    }

    @RequestMapping("/loginfailed")
    public ModelAndView loginFailed() {
        return new ModelAndView("security/login", "error", true);
    }
}
