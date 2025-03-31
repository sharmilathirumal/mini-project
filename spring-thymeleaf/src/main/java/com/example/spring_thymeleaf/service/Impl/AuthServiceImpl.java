package com.example.spring_thymeleaf.service.impl;

import com.example.spring_thymeleaf.dto.UserDTO;
import com.example.spring_thymeleaf.dto.EmployeeDTO;
import com.example.spring_thymeleaf.service.AuthService;
import com.example.spring_thymeleaf.service.UserService;
import com.example.spring_thymeleaf.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private UserService userService;

    @Autowired
    private EmployeeService employeeService;

    @Override
    public EmployeeDTO getLoggedInEmployee() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        UserDTO user = userService.getByUserName(userName);
        return employeeService.getEmployeeById(user.getEmployee().getId());
    }
}
