package org.antbear.tododont.web.app.controller.admin;

import org.antbear.tododont.web.security.annotation.RoleAdmin;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RoleAdmin
@Controller
public class AdminController {

    @RequestMapping("/admin/")
    public String adminHandler() {
        return "/admin/home";
    }

    // TODO do something useful, e.g. list current users as html table
}
