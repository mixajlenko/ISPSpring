package com.mixajlenko.ispspring.controller;

import com.mixajlenko.ispspring.entity.Payment;
import com.mixajlenko.ispspring.entity.User;
import com.mixajlenko.ispspring.repository.UserRepository;
import com.mixajlenko.ispspring.service.PaymentService;
import com.mixajlenko.ispspring.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
public class ClientController {

    @Autowired
    UserService userService;

    @Autowired
    PaymentService paymentService;

    @Autowired
    UserRepository userRepository;

    @GetMapping("/clientPage")
    public String clientPage() {
        return "/client/clientPage";
    }

    @GetMapping("/paymentSystem")
    public String paymentSystem(Model model) {
        model.addAttribute("payment", new Payment());
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        int wallet = ((User) principal).getWallet();
        model.addAttribute("paymentHistory", paymentService.allPayments());
        model.addAttribute("funds", wallet);
        return "client/paymentSystem";
    }

    @PostMapping("/paymentSystem")
    public String payment(@ModelAttribute("payment") Payment payment) {
        paymentService.savePayment(payment);
        return "redirect:/paymentSystem";
    }

    @GetMapping("/manage")
    public String manage(@RequestParam(value = "param") String param, Model model) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userService.findUserById(((User)principal).getId());
        model.addAttribute("command", param);
        model.addAttribute("userForm", user);
        return "client/profile";
    }

    @PostMapping("/manage")
    public String manageSubmit(@ModelAttribute("userForm") @Valid User userForm,@RequestParam(value = "param") String param) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userService.findUserById(((User)principal).getId());
        user.setFirstName(userForm.getFirstName());
        user.setSecondName(userForm.getSecondName());
        //TODO
        userRepository.save(user);
        return "redirect:/manage?param="+param;
    }


}
