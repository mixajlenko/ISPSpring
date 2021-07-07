package com.mixajlenko.ispspring.controller;

import com.mixajlenko.ispspring.entity.User;
import com.mixajlenko.ispspring.repository.UserRepository;
import com.mixajlenko.ispspring.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Controller
public class AdminController {

    @Autowired
    private UserService userService;

    @Autowired
    UserRepository userRepository;


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
        System.out.println(users);
        model.addAttribute("users", users);
        return "admin/userPageAdmin";
    }

    @PostMapping("/userPageAdmin")
    public String deleteUser(@RequestParam(required = true, defaultValue = "") Long userId,
                             @RequestParam(required = true, defaultValue = "") String action,
                             Model model) {

        if (action.equals("delete")) {
            userService.deleteUser(userId);
        } else if (action.equals("block")) {
            userService.manageUserStatus(userRepository.findById(userId).orElse(new User()), action);
        } else if (action.equals("unblock")) {
            userService.manageUserStatus(userRepository.findById(userId).orElse(new User()), action);
        }
        return "redirect:/userPageAdmin";
    }

//
//    @GetMapping("/admin/gt/{userId}")
//    public String  gtUser(@PathVariable("userId") Long userId, Model model) {
//        model.addAttribute("allUsers", userService.usergtList(userId));
//        return "adminPage";
//    }

}
