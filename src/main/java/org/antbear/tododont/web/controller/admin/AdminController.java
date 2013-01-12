package org.antbear.tododont.web.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AdminController {

    @RequestMapping("/admin/")
    public String adminHandler() {
        return "/admin/home";
    }
}
