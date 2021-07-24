package com.mixajlenko.ispspring.controller;

import com.mixajlenko.ispspring.entity.Payment;
import com.mixajlenko.ispspring.entity.Tariff;
import com.mixajlenko.ispspring.entity.User;
import com.mixajlenko.ispspring.repository.ServiceRepository;
import com.mixajlenko.ispspring.repository.TariffRepository;
import com.mixajlenko.ispspring.repository.UserRepository;
import com.mixajlenko.ispspring.service.PaymentService;
import com.mixajlenko.ispspring.service.TariffService;
import com.mixajlenko.ispspring.service.UserPlansService;
import com.mixajlenko.ispspring.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Controller
public class ClientController {

    @Autowired
    UserService userService;

    @Autowired
    PaymentService paymentService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ServiceRepository serviceRepository;

    @Autowired
    TariffRepository tariffRepository;

    @Autowired
    TariffService tariffService;

    @Autowired
    UserPlansService userPlansService;

    @GetMapping("/clientPage")
    public String clientPage(Model model, HttpServletRequest request) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        request.getSession().setAttribute("services", serviceRepository.findAll());
        request.getSession().setAttribute("user", userService.findUserById(((User) principal).getId()));
        model.addAttribute("tariffs", userPlansService.tariffsByUser(((User) principal).getId()));
        model.addAttribute("showTariff", false);
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

    @PostMapping("/clientPage")
    public String payService(@RequestParam(value = "service") Long service,@RequestParam(value = "val") Integer val,Model model) {
        paymentService.payService(service, val);
        model.addAttribute("showTariff", false);
        return "redirect:/clientPage";
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
    public String supportPage() {
        return "client/supportPage";
    }


    @GetMapping("/servicePageClient")
    public String servicePage(@RequestParam(value = "service", required = false) Long service, Model model) {
        List<Tariff> complex = tariffRepository.findAll().stream().filter(t -> t.getName().contains("Plan")).collect(Collectors.toList());
        System.out.println(service);
        if (Objects.nonNull(service)) {
            model.addAttribute("tariffs", tariffService.allTariffsBySvcId(service));
            model.addAttribute("showTariffs", true);
            model.addAttribute("serviceId", service);
        }
        model.addAttribute("complex", complex);
        return "client/servicePageClient";
    }

    @PostMapping("/subscribe")
    public String tariffSubscription(@RequestParam(value = "tariffId") String tariffId, Model model) {

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userService.findUserById(((User) principal).getId());

        if (!user.isStatus()) {
            model.addAttribute("subFail", true);
        } else {
//            if (user.getTariffs().stream().anyMatch(t -> t.getId() == Long.parseLong(tariffId))) {
//                model.addAttribute("alreadyExistTariff", true);
//                model.addAttribute("subSuccess", false);
//            } else {
                userPlansService.addUserTariff(user.getId(), Long.valueOf(tariffId));
                model.addAttribute("subSuccess", true);
            }
        return "redirect:/servicePageClient";
    }

    @GetMapping("/sort")
    public String sort(@RequestParam("sort") String sort, @RequestParam("service") Long service, Model model) {
        model.addAttribute("tariffs", tariffService.sort(service, sort));
        model.addAttribute("showTariffs", true);
        return "client/servicePageClient";
    }

    @PostMapping("/unsubscribe")
    public String unsubscribeTariff(@RequestParam("service") Long service) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userService.findUserById(((User) principal).getId());

        userPlansService.deleteTariff(user.getId(), service);

        return "redirect:/clientPage";
    }

    @GetMapping("/more")
    public String moreTariffInfo(@RequestParam("tariffId") Long tariffId, Model model){
        model.addAttribute("showTariff", true);
        model.addAttribute("tariff", tariffService.findTariffById(tariffId));
        return "client/clientPage";
    }

}
