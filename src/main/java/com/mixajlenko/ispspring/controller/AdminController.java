package com.mixajlenko.ispspring.controller;

import com.mixajlenko.ispspring.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AdminController {

    @Autowired
    private UserService userService;

    @GetMapping("/adminPage")
    public String userList(Model model) {
//        model.addAttribute("allUsers", userService.allUsers());
        return "admin/adminPage";
    }

    @GetMapping("/adminPage/userPageAdmin")
    public String userPage(Model model) {
//        model.addAttribute("allUsers", userService.allUsers());
        return "admin/userPageAdmin";
    }

//    @PostMapping("/admin/adminPage")
//    public String  deleteUser(@RequestParam(required = true, defaultValue = "" ) Long userId,
//                              @RequestParam(required = true, defaultValue = "" ) String action,
//                              Model model) {
//        if (action.equals("delete")){
//            userService.deleteUser(userId);
//        }
//        return "redirect:/admin/adminPage";
//    }
//
//    @GetMapping("/admin/gt/{userId}")
//    public String  gtUser(@PathVariable("userId") Long userId, Model model) {
//        model.addAttribute("allUsers", userService.usergtList(userId));
//        return "adminPage";
//    }

}
