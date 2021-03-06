package de.spqrinfo.quotes.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;

@Controller
public class HomeController {

    private static final Logger log = LoggerFactory.getLogger(HomeController.class);

    @RequestMapping("/home")
    public ModelAndView home(final Principal principal) {
        log.debug("homeHandler called");

        final ModelAndView mav = new ModelAndView("home");
        mav.addObject("username", principal.getName());
        mav.addObject("message", "Hello from authenticated controller call");

        return mav;
    }
}
