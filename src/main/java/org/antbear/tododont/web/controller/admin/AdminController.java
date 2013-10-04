package org.antbear.tododont.web.controller.admin;

import org.antbear.tododont.backend.admin.service.AdminService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class AdminController {

    private static final Logger log = LoggerFactory.getLogger(AdminController.class);

    @Autowired
    private AdminService adminService;

    @RequestMapping("/admin/")
    public String adminHandler() {
        return "/admin/home";
    }

    @RequestMapping("/admin/users")
    public ModelAndView adminUsersHandler() {
        log.debug("adminUsersHandler");
        final ModelAndView mv = new ModelAndView("/admin/users");
        mv.addObject("users", this.adminService.getUsers());
        return mv;
    }
}
