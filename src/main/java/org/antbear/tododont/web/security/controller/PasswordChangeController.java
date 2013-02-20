package org.antbear.tododont.web.security.controller;

import org.antbear.tododont.backend.security.service.PasswordChangeException;
import org.antbear.tododont.backend.security.service.PasswordChangeService;
import org.antbear.tododont.web.security.beans.PasswordChange;
import org.antbear.tododont.web.security.beans.validation.PasswordChangeValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
import java.security.Principal;

@RolesAllowed("hasRole('ROLE_USER')")
@RequestMapping("/s/pwc")
@Controller
public class PasswordChangeController {

    private static final Logger log = LoggerFactory.getLogger(PasswordChangeController.class);

    @Autowired
    private PasswordChangeValidator passwordChangeValidator;

    @Autowired
    private PasswordChangeService passwordChangeService;

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView startChangePassword() {
        log.debug("Start change password (GET)");
        return new ModelAndView("security/password-change/start", "passwordChange", new PasswordChange());
    }

    @RequestMapping(method = RequestMethod.POST)
    public String performChangePassword(final Principal principal,
                                        @Valid final PasswordChange passwordChange,
                                        final BindingResult bindingResult) throws PasswordChangeException {
        log.debug("Change password request (POST) for {} with {}", principal, passwordChange);

        assert this.passwordChangeValidator.supports(passwordChange.getClass());
        this.passwordChangeValidator.validate(passwordChange, bindingResult);

        if (bindingResult.hasErrors()) {
            log.warn("binding result has errors; returning to start page");
            return "security/password-change/start";
        } else {
            log.debug("OK, handing over password change request to password change service");
            this.passwordChangeService.passwordChange(principal.getName(), passwordChange);
            return "/home";
        }
    }

    @ExceptionHandler(PasswordChangeException.class)
    public ModelAndView handlePasswordChangeException(final PasswordChangeException pce) {
        return new ModelAndView("security/password-change/error", "errorMessageKey", pce.getMessageKey());
    }
}
