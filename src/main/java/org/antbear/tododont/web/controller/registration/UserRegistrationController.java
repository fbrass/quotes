package org.antbear.tododont.web.controller.registration;

import org.antbear.tododont.backend.service.userregistration.UserRegistrationException;
import org.antbear.tododont.backend.service.userregistration.UserRegistrationService;
import org.antbear.tododont.util.InvariantException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import javax.annotation.PostConstruct;
import javax.validation.Valid;

@RequestMapping("/register")
@Controller
public class UserRegistrationController {

    private static final Logger log = LoggerFactory.getLogger(UserRegistrationController.class);
    public static final String ACTIVATION_PATH = "register/activate-user/";

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
        final ModelAndView modelAndView = new ModelAndView("register", "userRegistration", new UserRegistration());
        return modelAndView;
    }

    @RequestMapping(method = RequestMethod.POST)
    public String performRegistration(@Valid final UserRegistration userRegistration, final BindingResult bindingResult) {
        log.debug("registration attempt (POST) {}", userRegistration);

        if (bindingResult.hasErrors()) {
            log.warn("binding result has errors; returning to registration page");
            return "register";
        } else {
            log.debug("registration OK, handing over registration attempt to registration service");
            UriComponents userActivationUriComponents = UriComponentsBuilder.fromUriString(
                    this.applicationBaseUri + ACTIVATION_PATH + "{email}/{activationToken}").build();
            try {
                this.userRegistrationService.register(userRegistration, userActivationUriComponents);
            } catch (UserRegistrationException ure) {
                log.error("User registration failed", ure);
                return "redirect:/register-error";
            }
            return "redirect:/register-done";
        }
    }

    @RequestMapping(value = "/activate-user/{email}/{activationToken}", method = RequestMethod.GET)
    public String performActivation(@RequestParam("email") final String email,
                                    @RequestParam("activationToken") final String activationToken) {
        log.debug("activation attempt (GET) for {} with token {}", email, activationToken);

        try {
            log.debug("Handing over activation attempt to registration service");
            this.userRegistrationService.activate(email, activationToken);
        } catch (UserRegistrationException ure) {
            log.error("User activation failed", ure);
            return "redirect:/activation-error";
        }
        return "redirect:/home";
    }
}
