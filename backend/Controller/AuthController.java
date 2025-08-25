package com.rideease.democar_rental.Controller;

import com.rideease.democar_rental.dto.UserRegistration;
import com.rideease.democar_rental.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AuthController {
    @Autowired
    private UserService userService;

    @GetMapping("/")
    public String showWelcomePage(Model model) {
        model.addAttribute("user", new UserRegistration());
        return "welcome";
    }

    @PostMapping("/register")
    public String registerUserAccount(@ModelAttribute("user") UserRegistration registrationDto) {
        try {
            userService.registerUser(registrationDto);

            return "redirect:/?register_success";
        } catch (Exception e) {

            System.err.println("Registration failed: " + e.getMessage());
            return "redirect:/?register_error=" + e.getMessage();
        }
    }
}




