package com.example.spring_thymeleaf.controller;

import com.example.spring_thymeleaf.dto.UserDTO;
import com.example.spring_thymeleaf.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public String showLoginPage() {
        return "login"; // Must match "login.html" in templates
    }

    @GetMapping("/dashboard")
    public String redirectBasedOnRole(Authentication authentication) {
        String username = authentication.getName();
        UserDTO user = userService.getByUserName(username);

        if (user == null) {
            return "redirect:/login?error=user_not_found";
        }

        String role = user.getRole().toString();

        if (role == null) {
            return "redirect:/login?error=role_missing";
        }

        switch (role) {
            case "ADMIN":
            case "HR":
                return "redirect:/hradmin/dashboard";
            case "MANAGER":
                return "redirect:/manager/dashboard";
            default:
                return "redirect:/employee/get/" + user.getEmployee().getId();
        }
    }
}
