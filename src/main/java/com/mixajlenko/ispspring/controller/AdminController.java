package com.mixajlenko.ispspring.controller;

import com.mixajlenko.ispspring.entity.Svc;
import com.mixajlenko.ispspring.entity.Tariff;
import com.mixajlenko.ispspring.entity.User;
import com.mixajlenko.ispspring.repository.ServiceRepository;

import com.mixajlenko.ispspring.repository.TariffRepository;
import com.mixajlenko.ispspring.service.TariffService;
import com.mixajlenko.ispspring.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class AdminController {

    @Autowired
    private UserService userService;

    @Autowired
    private ServiceRepository serviceRepository;

    @Autowired
    private TariffRepository tariffRepository;

    @Autowired
    private TariffService tariffService;


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

    @GetMapping("/servicePageAdmin")
    public String servicePage(Model model) {
        List<Svc> svcs = serviceRepository.findAll();
        model.addAttribute("services", svcs);
        return "servicePageAdmin";
    }

    @GetMapping("/tariffPage")
    public String tariffPage(@RequestParam("param") String param, Model model) {
        model.addAttribute("commandInterface", false);
        model.addAttribute("tariffs", serviceRepository.getById(Long.valueOf(param)).getTariffs());
        model.addAttribute("service", param);
        return "admin/tariffPage";
    }

    @GetMapping("/managePlan")
    public String managePlan(@RequestParam("service") String service, @RequestParam("id") String id, Model model) {
        Tariff tariff = tariffRepository.getById(Long.valueOf(id));
        model.addAttribute("tariffUpdate", tariff);
        model.addAttribute("commandInterface", true);
        model.addAttribute("service", service);
        return "admin/tariffPage";
    }

    @PostMapping("/managePlan")
    public String managePlanSubmit(@RequestParam(value = "service") String service, @Valid @ModelAttribute("tariffUpdate") Tariff tariffUpdate, BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            return "managePlan";
        }

        if (!tariffService.manageTariff(tariffUpdate)) {
            return "managePlan";
        }

        return "redirect:/tariffPage?param=" + service;
    }


    @GetMapping("/newTariff")
    public String newTariff(@RequestParam("id") String id, Model model) {
        model.addAttribute("tariffForm", new Tariff());
        model.addAttribute("service", id);
        return "admin/newTariff";
    }

    @PostMapping("/newTariff")
    public String newTariffSubmit(@RequestParam(value = "service") String service, @Valid @ModelAttribute("tariffForm") Tariff tariffForm, BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            return "newTariff";
        }
        if (!tariffService.saveTariff(tariffForm, Long.valueOf(service))) {
            model.addAttribute("alreadyExist", true);
            return "newTariff";
        }

        return "redirect:/tariffPage?param=" + service;
    }


    @PostMapping("/deleteTariff")
    public String deleteTariff(@RequestParam("service") Long service, @RequestParam("id") Long id, Model model) {

        tariffService.deleteTariff(id);

        return "redirect:/tariffPage?param=" + service;
    }

}
