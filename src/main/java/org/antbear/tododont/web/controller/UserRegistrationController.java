package org.antbear.tododont.web.controller;

import org.antbear.tododont.backend.service.userregistration.UserRegistrationException;
import org.antbear.tododont.backend.service.userregistration.UserRegistrationService;
import org.antbear.tododont.util.InvariantException;
import org.antbear.tododont.web.beans.UserRegistration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import javax.annotation.PostConstruct;
import javax.validation.Valid;

@RequestMapping("/register")
@Controller
public class UserRegistrationController {

    // TODO rename registration to signUp?
    // TODO check @ExceptionHandler for a way to handle registration exceptions

    private static final Logger log = LoggerFactory.getLogger(UserRegistrationController.class);

    public static final String ACTIVATION_PATH = "register/activate/";

    @Value("${application.base.uri}")
    private String applicationBaseUri;

    @Autowired
    UserRegistrationService userRegistrationService;

    @PostConstruct
    private void init() {
        log.debug("@PostConstruct");
        InvariantException.notNullOrEmpty(this.applicationBaseUri, "UserRegistrationService.applicationBaseUri");
        if (!this.applicationBaseUri.endsWith("/")) {
            throw new InvariantException("UserRegistrationService.applicationBaseUri must end with '/'");
        }
    }

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView startRegistration() {
        log.debug("registration start (GET)");
        final ModelAndView modelAndView = new ModelAndView("register/start", "userRegistration", new UserRegistration());
        return modelAndView;
    }

    @RequestMapping(method = RequestMethod.POST)
    public String performRegistration(@Valid final UserRegistration userRegistration, final BindingResult bindingResult) {
        log.debug("registration attempt (POST) {}", userRegistration);

        if (bindingResult.hasErrors()) {
            log.warn("binding result has errors; returning to registration page");
            return "register/start";
        } else {
            log.debug("registration OK, handing over registration attempt to registration service");
            UriComponents userActivationUriComponents = UriComponentsBuilder.fromUriString(
                    this.applicationBaseUri + ACTIVATION_PATH + "{email}/{activationToken}").build();
            try {
                this.userRegistrationService.register(userRegistration, userActivationUriComponents);
            } catch (UserRegistrationException ure) {
                log.error("User registration failed", ure);
                return "redirect:/register/error";
            }
            return "redirect:/register/done";
        }
    }

    @RequestMapping("/error")
    public String registrationError() {
        log.debug("/register/error");
        return "register/error"; // TODO we need to show the error in the view
    }

    @RequestMapping("/done")
    public String registrationDone() {
        log.debug("/register/done");
        return "register/done";
    }

    @RequestMapping(value = "/activate/{email}/{activationToken}", method = RequestMethod.GET)
    public String performActivation(@PathVariable("email") final String email,
                                    @PathVariable("activationToken") final String activationToken) {
        log.debug("activation attempt (GET) for {} with token {}", email, activationToken);

        try {
            log.debug("Handing over activation attempt to registration service");
            this.userRegistrationService.activate(email, activationToken);
            return "redirect:/register/activate/done"; // TODO we need to show the success of the activation first, then let the user login via home page
        } catch (UserRegistrationException ure) {
            log.error("User activation failed", ure);
            return "redirect:/register/activate/error";
        }
    }

    @RequestMapping("/activate/done")
    public String acivationDone() {
        log.debug("/register/activate/done");
        return "register/activate/done"; // TODO show info how to log in with email password via link to home
    }

    @RequestMapping("/activate/error")
    public String activationError() {
        log.debug("/register/activation/error");
        return "register/activate/error"; // TODO we need to show the error in the view
    }
}
