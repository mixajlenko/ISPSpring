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
        model.addAttribute("paymentHistory", paymentService.allPaymentsByUser());
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
        User principal = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userService.findUserById(principal.getId());
        model.addAttribute("command", param);
        model.addAttribute("userForm", user);
        return "client/profile";
    }

    @PostMapping("/managePassword")
    public String manageSubmit(@RequestParam(value = "oldPass") String oldPass,
                               @RequestParam(value = "newPass") String newPass) {

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userService.findUserById(((User) principal).getId());

        if (userService.passwordVerify(user, oldPass)) {
            user.setPassword(userService.passwordEncode(newPass));
        }

        userRepository.save(user);
        return "redirect:/manage?param=managePassword";
    }

    @PostMapping("/manageName")
    public String manageName(@RequestParam(value = "firstName") String firstName,
                             @RequestParam(value = "secondName") String secondName) {

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userService.findUserById(((User) principal).getId());


        user.setFirstName(firstName);
        user.setSecondName(secondName);


        userRepository.save(user);
        return "redirect:/manage?param=manageName";
    }

    @PostMapping("/manageEmail")
    public String manageName(@RequestParam(value = "username") String username) {

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userService.findUserById(((User) principal).getId());

        user.setUsername(username);

        userRepository.save(user);
        return "redirect:/manage?param=manageEmail";
    }

    @PostMapping("/managePhone")
    public String managePhone(@RequestParam(value = "phone") String phone) {

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userService.findUserById(((User) principal).getId());

        user.setPhone(phone);

        userRepository.save(user);
        return "redirect:/manage?param=managePhone";
    }

    @GetMapping("/supportPage")
    public String supportPage(){
        return "client/supportPage";
    }


}
