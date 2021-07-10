package com.mixajlenko.ispspring.controller;

import com.mixajlenko.ispspring.entity.Service;
import com.mixajlenko.ispspring.entity.Tariff;
import com.mixajlenko.ispspring.entity.User;
import com.mixajlenko.ispspring.repository.ServiceRepository;

import com.mixajlenko.ispspring.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class AdminController {

    @Autowired
    private UserService userService;

    @Autowired
    private ServiceRepository serviceRepository;



    @GetMapping("/adminPage")
    public String userList(Model model) {
        model.addAttribute("allUsers", (int) userService.allUsers().stream().filter(u -> u.getId() != 1).count());
        model.addAttribute("blocked", (int) userService.findUsersByStatus(1L).stream().filter(u -> u.getId() != 1).count());
        model.addAttribute("unlocked", (int) userService.findUsersByStatus(2L).stream().filter(u -> u.getId() != 1).count());
        return "admin/adminPage";
    }

    @GetMapping("/userPageAdmin")
    public String userPage(Model model) {
        List<User> users = userService.allUsers().stream().filter(u -> u.getId() != 1).collect(Collectors.toList());
        model.addAttribute("users", users);
        return "admin/userPageAdmin";
    }

    @PostMapping("/userPageAdmin")
    public String deleteUser(@RequestParam(required = true, defaultValue = "") Long userId,
                             @RequestParam(required = true, defaultValue = "") String action,
                             Model model) {

        switch (action) {
            case "delete":
                userService.deleteUser(userId);
                break;
            case "block":
                userService.manageUserStatus(userId, 1);
                break;
            case "unblock":
                userService.manageUserStatus(userId, 2);
                break;
            default:
        }
        return "redirect:/userPageAdmin";
    }

    @GetMapping("/servicePage")
    public String servicePage(Model model) {
        List<Service> services = serviceRepository.findAll();
        model.addAttribute("services", services);
        return "admin/servicePage";
    }

//    @GetMapping("/tariffPage")
//    public String tariffPage(@RequestParam("param") String param, Model model) {
//        List<Tariff> tariffs = tariffRepository.findAllByServiceId(Long.valueOf(param));
//        model.addAttribute("commandInterface", false);
//        model.addAttribute("tariffs", tariffs);
//        return "admin/servicePage";
//    }

}
