package com.mixajlenko.ispspring.controller;

import com.mixajlenko.ispspring.entity.User;
import com.mixajlenko.ispspring.service.UserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;

@Controller
public class HomeController {

    Logger logger = Logger.getLogger(HomeController.class);

    @Autowired
    UserService userService;

    @GetMapping("/")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/login")
    public String loginFail(@RequestParam(name = "error") boolean error, Model model) {
        if (error) {
            model.addAttribute("er", true);
        }
        return "login";
    }

    @GetMapping("/successLogin")
    public String loginPageRedirect(Model model, Authentication authResult) throws IOException {

        String role =  authResult.getAuthorities().toString();

        User principal = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userService.findUserById(principal.getId());

        if(user.getStatuses().get(0).getName().equals("STATUS_BLOCKED")){
            model.addAttribute("blo", true);
            return "login";
        }
        if (role.contains("ROLE_ADMIN")) {
            return "redirect:/adminPage";
        } else {
            return "redirect:/clientPage";
        }
    }

}
