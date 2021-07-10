package com.mixajlenko.ispspring.controller;

import com.mixajlenko.ispspring.entity.User;
import com.mixajlenko.ispspring.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@Controller
public class RegistrationController {

    @Autowired
    private UserService userService;

    @GetMapping("/registration")
    public String registration(Model model) {
        model.addAttribute("userForm", new User());
        return "registration";
    }

    @GetMapping("/manageUsers")
    public String addNewUserByAdminForm(Model model) {
        model.addAttribute("userForm", new User());
        return "admin/manageUsers";
    }

    @PostMapping("/manageUsers")
    public String addNewUserByAdmin( @ModelAttribute("userForm") @Valid User userForm, BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            return "admin/manageUsers";
        }

        if (!userService.saveUser(userForm)) {
            model.addAttribute("alreadyExist", true);
            return "admin/manageUsers";
        }

        return "redirect:/userPageAdmin";
    }

    @PostMapping("/registration")
    public String addUser(@Valid @ModelAttribute("userForm") User userForm, BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("er", true);
            return "registration";
        }

        if (!userService.saveUser(userForm)) {
            model.addAttribute("alreadyExist", true);
            return "registration";
        }

        return "redirect:/";
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }



}
