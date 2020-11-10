package rest.web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import rest.model.User;
import rest.service.RoleService;
import rest.service.UserService;

@Controller
public class BasicController {

    @Autowired
    UserService userService;

    @Autowired
    RoleService roleService;

    @GetMapping(value = "/")
    public String homePage(Model model) {
        if (SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream().anyMatch(a -> a.toString().contains("ROLE_ADMIN"))) {
            model.addAttribute("usersList", userService.listUsers());
            model.addAttribute("newUser", new User());
            model.addAttribute("allRoles", roleService.listRoles());
        }
        return "index";
    }


}
