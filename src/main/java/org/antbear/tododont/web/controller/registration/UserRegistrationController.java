package org.antbear.tododont.web.controller.registration;

import org.antbear.tododont.backend.service.UserRegistrationException;
import org.antbear.tododont.backend.service.UserRegistrationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@RequestMapping("/register")
@Controller
public class UserRegistrationController {

    private static final Logger log = LoggerFactory.getLogger(UserRegistrationController.class);

    @Autowired
    UserRegistrationService userRegistrationService;

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView startRegistration() {
        log.debug("registration start (GET)");
        final ModelAndView modelAndView = new ModelAndView("register", "userRegistration", new UserRegistration());
        return modelAndView;
    }

    @RequestMapping(method = RequestMethod.POST)
    public String performResitration(@Valid final UserRegistration userRegistration, final BindingResult bindingResult) {
        log.debug("registration attempt (POST) {}", userRegistration);

        if (bindingResult.hasErrors()) {
            log.warn("binding result has errors; returing to registration page");
            return "register";
        } else {
            log.debug("registration OK, giving the registration service a chance");
            try {
                this.userRegistrationService.register(userRegistration);
            } catch (UserRegistrationException ure) {
                log.error("User registration failed", ure);
                // TODO show user a registration failed page
            }
            return "redirect:/register-done"; // TODO call service, goto register-done.jsp
        }
    }

}
