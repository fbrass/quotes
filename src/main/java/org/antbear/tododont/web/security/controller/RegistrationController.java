package org.antbear.tododont.web.security.controller;

import org.antbear.tododont.backend.security.service.RegistrationActivationException;
import org.antbear.tododont.backend.security.service.RegistrationException;
import org.antbear.tododont.backend.security.service.RegistrationService;
import org.antbear.tododont.backend.security.util.InvariantException;
import org.antbear.tododont.web.security.beans.Registration;
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

@RequestMapping("/s/r")
@Controller
public class RegistrationController {

    private static final Logger log = LoggerFactory.getLogger(RegistrationController.class);

    // Security/registration/activation
    public static final String ACTIVATION_PATH = "s/r/a/";

    @Value("${web.app.base.uri}")
    private String applicationBaseUri;

    @Autowired
    RegistrationService registrationService;

    @PostConstruct
    private void init() {
        InvariantException.notNullOrEmpty(this.applicationBaseUri, "RegistrationController.applicationBaseUri");
        if (!this.applicationBaseUri.endsWith("/")) {
            throw new InvariantException("RegistrationController.applicationBaseUri must end with '/'");
        }
    }

    public UriComponents getActivationUriComponents() {
        return UriComponentsBuilder.fromUriString(
                this.applicationBaseUri + ACTIVATION_PATH + "{email}/{activationToken}").build();
    }

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView startRegistration() {
        log.debug("registration start (GET)");
        return new ModelAndView("security/register/start", "registration", new Registration());
    }

    @RequestMapping(method = RequestMethod.POST)
    public ModelAndView performRegistration(@Valid final Registration registration,
                                            final BindingResult bindingResult) throws RegistrationException {
        log.debug("registration attempt (POST) {}", registration);

        if (bindingResult.hasErrors()) {
            log.warn("binding result has errors; returning to registration page");
            return new ModelAndView("security/register/start");
        } else {
            log.debug("registration OK, handing over registration attempt to registration service");
            this.registrationService.register(registration, getActivationUriComponents());
            return new ModelAndView("security/register/done", "email", registration.getEmail());
        }
    }

    @ExceptionHandler(RegistrationException.class)
    public ModelAndView handleRegistrationException(final RegistrationException ex) {
        log.debug("ExceptionHandler handleRegistrationException", ex);
        return new ModelAndView("security/register/error", "errorMessageKey", ex.getMessageKey());
    }

    @RequestMapping(value = "/a/{email}/{activationToken}", method = RequestMethod.GET)
    public ModelAndView performActivation(@PathVariable("email") final String email,
                                          @PathVariable("activationToken") final String activationToken)
            throws RegistrationActivationException {
        log.debug("activation attempt (GET) for {} with token {}", email, activationToken);

        log.debug("Handing over activation attempt to registration service");
        this.registrationService.activate(email, activationToken);

        return new ModelAndView("security/register/activate/done", "email", email);
    }

    @ExceptionHandler(RegistrationActivationException.class)
    public ModelAndView handleUserActivationException(final RegistrationActivationException ex) {
        log.debug("ExceptionHandler handleActivationException", ex);
        return new ModelAndView("security/register/activate/error", "errorMessageKey", ex.getMessageKey());
    }
}