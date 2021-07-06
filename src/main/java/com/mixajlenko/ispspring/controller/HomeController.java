package com.mixajlenko.ispspring.controller;

import org.apache.log4j.Logger;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class HomeController {

    Logger logger = Logger.getLogger(HomeController.class);

    @GetMapping("/")
    public String loginPage() {

        return "login";
    }

    @GetMapping("/successLogin")
    public void loginPageRedirect(HttpServletResponse response, Authentication authResult) throws IOException {

        String role = authResult.getAuthorities().toString();

        logger.info("????????????????????????????????????" + role);
        System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!" + role);

        if (role.contains("ROLE_ADMIN")) {
            response.sendRedirect(response.encodeRedirectURL("/adminPage"));
        } else {
            response.sendRedirect(response.encodeRedirectURL("/clientPage"));
        }
    }

}
