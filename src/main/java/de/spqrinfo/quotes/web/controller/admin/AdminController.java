package de.spqrinfo.quotes.web.controller.admin;

import de.spqrinfo.quotes.backend.admin.service.AdminService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class AdminController {

    private static final Logger log = LoggerFactory.getLogger(AdminController.class);
    private static final String REDIRECT_ADMIN_USERS = "redirect:/admin/users/";

    @Autowired
    private AdminService adminService;

    @RequestMapping("/admin/")
    public String adminHandler() {
        return "/admin/home";
    }

    @RequestMapping("/admin/users/")
    public ModelAndView usersHandler() {
        log.debug("usersHandler");
        final ModelAndView mv = new ModelAndView("/admin/users");
        mv.addObject("users", this.adminService.getUsers());
        return mv;
    }

    @RequestMapping(value = "/admin/disable-user/{userId:.+}")
    public String disableUserHandler(@PathVariable(value = "userId") final String userId) {
        log.debug("disableUserHandler {}", userId);
        this.adminService.disableUser(userId);
        return REDIRECT_ADMIN_USERS;
    }

    @RequestMapping(value = "/admin/enable-user/{userId:.+}")
    public String enableUserHandler(@PathVariable(value = "userId") final String userId) {
        log.debug("enableUserHandler {}", userId);
        this.adminService.enableUser(userId);
        return REDIRECT_ADMIN_USERS;
    }

    @RequestMapping(value = "/admin/password-reset-user/{userId:.+}")
    public String passwordResetForUserHandler(@PathVariable(value = "userId") final String userId) {
        log.debug("passwordResetForUserHandler {}", userId);
        // TODO send password reset for given user
        return REDIRECT_ADMIN_USERS;
    }
}
