package com.example.spring_thymeleaf.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.HashMap;
import java.util.Map;

public class SecurityUtil {
    public static Map<String, Object> getLoggedInUserDetails() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated() || !(authentication.getPrincipal() instanceof CustomUserDetails)) {
            return new HashMap<>(); // Return empty map instead of null
        }

        CustomUserDetails user = (CustomUserDetails) authentication.getPrincipal();

        Map<String, Object> userDetailsMap = new HashMap<>();
        userDetailsMap.put("username", user.getUsername());
        userDetailsMap.put("role", user.getRole());
        userDetailsMap.put("employeeId", user.getEmployeeId());

        return userDetailsMap;
    }
}
