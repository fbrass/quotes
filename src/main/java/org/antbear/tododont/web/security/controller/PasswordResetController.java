package org.antbear.tododont.web.security.controller;

import org.antbear.tododont.backend.security.service.PasswordResetException;
import org.antbear.tododont.backend.security.service.PasswordResetService;
import org.antbear.tododont.backend.security.util.InvariantException;
import org.antbear.tododont.web.security.beans.PasswordReset;
import org.antbear.tododont.web.security.beans.PasswordResetAttempt;
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

@RequestMapping("/s/pwr")
@Controller
public class PasswordResetController {

    private static final Logger log = LoggerFactory.getLogger(PasswordResetController.class);

    @Value("${web.app.base.uri}")
    private String applicationBaseUri;

    @Autowired
    private PasswordResetService passwordResetService;

    @PostConstruct
    private void init() {
        InvariantException.notNullOrEmpty(this.applicationBaseUri, "PasswordResetController.applicationBaseUri");
        if (!this.applicationBaseUri.endsWith("/")) {
            throw new InvariantException("PasswordResetController.applicationBaseUri must end with '/'");
        }
    }

    public UriComponents getPasswordResetUriComponents() {
        // Build change password URI by appending /c/ to the controller request mapping
        final RequestMapping requestMapping = this.getClass().getAnnotation(RequestMapping.class);
        final String changePasswordPath = requestMapping.value()[0].replaceFirst("^/(.*)$", "$1/c/");

        return UriComponentsBuilder.fromUriString(
                this.applicationBaseUri + changePasswordPath + "{email}/{changeToken}").build();
    }

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView showPasswordReset() {
        log.debug("GET");
        // The password is unused in a password reset attempt, pre fill it to make bean validation happy
        final PasswordResetAttempt passwordResetAttempt = new PasswordResetAttempt();
        return new ModelAndView("security/password-reset/start", "passwordResetAttempt", passwordResetAttempt);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ModelAndView passwordReset(@Valid final PasswordResetAttempt passwordResetAttempt,
                                      final BindingResult bindingResult) throws PasswordResetException {
        log.debug("password reset request (POST) {}", passwordResetAttempt);

        if (bindingResult.hasErrors()) {
            log.warn("binding result has errors; returning to start page");
            return new ModelAndView("security/password-reset/start");
        } else {
            log.debug("Validating password reset request for {}", passwordResetAttempt);
            this.passwordResetService.validateInitialRequest(passwordResetAttempt);
            log.debug("OK, handing over password forget attempt to password reset service");
            this.passwordResetService.passwordResetAttempt(passwordResetAttempt, getPasswordResetUriComponents());
            return new ModelAndView("security/password-reset/done", "email", passwordResetAttempt.getEmail());
        }
    }

    @ExceptionHandler(PasswordResetException.class)
    public ModelAndView handlePasswordResetException(final PasswordResetException ex) {
        log.debug("ExceptionHandler handlePasswordResetException", ex);
        return new ModelAndView("security/password-reset/error", "errorMessageKey", ex.getMessageKey());
    }

    @RequestMapping(value = "c/{email}/{passwordResetToken}", method = RequestMethod.GET)
    public ModelAndView passwordReset(@PathVariable("email") final String email,
                                      @PathVariable("passwordResetToken") final String passwordResetToken)
            throws PasswordResetException {
        log.debug("Password reset change form requested for {} and token {}", email, passwordResetToken);

        this.passwordResetService.validateChangeAttempt(email, passwordResetToken);
        final PasswordReset passwordReset = new PasswordReset();
        passwordReset.setEmail(email);
        passwordReset.setPasswordResetToken(passwordResetToken);
        return new ModelAndView("security/password-reset/change/start", "passwordReset", passwordReset);
    }

    @RequestMapping(value = "c", method = RequestMethod.POST)
    public ModelAndView performPasswordReset(@Valid final PasswordReset passwordReset,
                                             final BindingResult bindingResult) throws PasswordResetException {
        log.debug("Perform password change for {}", passwordReset.getEmail());

        if (bindingResult.hasErrors()) {
            log.warn("binding result has errors; returning to change/start page");
            return new ModelAndView("security/password-reset/change/start");
        } else {
            log.debug("Validating password reset change request for {}", passwordReset.getEmail());
            this.passwordResetService.validateChangeAttempt(passwordReset.getEmail(), passwordReset.getPasswordResetToken());
            log.info("Giving password reset service a chance for {}", passwordReset);
            this.passwordResetService.passwordChange(passwordReset);
            return new ModelAndView("security/password-reset/change/done");
        }
    }
}
