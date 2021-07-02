package com.mixajlenko.ispspring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ClientController {

    @GetMapping("/clientPage")
    public String clientPage() {
        return "/client/clientPage";
    }

}
