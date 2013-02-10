package org.antbear.tododont.web.controller;

import org.antbear.tododont.backend.service.userregistration.UserActivationException;
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
import org.springframework.web.bind.annotation.ExceptionHandler;
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
public class UserRegistrationController { // TODO rename registration to signUp?

    private static final Logger log = LoggerFactory.getLogger(UserRegistrationController.class);

    public static final String ACTIVATION_PATH = "register/activate/";

    @Value("${web.app.base.uri}")
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
        return new ModelAndView("register/start", "userRegistration", new UserRegistration());
    }

    @RequestMapping(method = RequestMethod.POST)
    public ModelAndView performRegistration(@Valid final UserRegistration userRegistration,
                                            final BindingResult bindingResult) throws UserRegistrationException {
        log.debug("registration attempt (POST) {}", userRegistration);

        if (bindingResult.hasErrors()) {
            log.warn("binding result has errors; returning to registration page");
            return new ModelAndView("register/start");
        } else {
            log.debug("registration OK, handing over registration attempt to registration service");
            UriComponents userActivationUriComponents = UriComponentsBuilder.fromUriString(
                    this.applicationBaseUri + ACTIVATION_PATH + "{email}/{activationToken}").build();
            this.userRegistrationService.register(userRegistration, userActivationUriComponents);

            return new ModelAndView("register/done", "email", userRegistration.getEmail());
        }
    }

    @ExceptionHandler(UserRegistrationException.class)
    public ModelAndView handleUserRegistrationException(final UserRegistrationException ex) {
        log.debug("ExceptionHandler handleUserRegistrationException", ex);
        final ModelAndView modelAndView = new ModelAndView("register/error");
        modelAndView.addObject("errorMessageKey", ex.getMessageKey());
        return modelAndView;
    }

    @RequestMapping(value = "/activate/{email}/{activationToken}", method = RequestMethod.GET)
    public ModelAndView performActivation(@PathVariable("email") final String email,
                                          @PathVariable("activationToken") final String activationToken)
            throws UserActivationException {
        log.debug("activation attempt (GET) for {} with token {}", email, activationToken);

        log.debug("Handing over activation attempt to registration service");
        this.userRegistrationService.activate(email, activationToken);

        return new ModelAndView("register/activate/done", "email", email);
    }

    @ExceptionHandler(UserActivationException.class)
    public ModelAndView handleUserActivationException(final UserActivationException ex) {
        log.debug("ExceptionHandler handleUserActivationException", ex);
        return new ModelAndView("register/activate/error", "errorMessageKey", ex.getMessageKey());
    }
}
