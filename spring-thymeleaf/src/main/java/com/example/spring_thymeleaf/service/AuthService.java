package com.example.spring_thymeleaf.service;

import com.example.spring_thymeleaf.dto.EmployeeDTO;

public interface AuthService {
    EmployeeDTO getLoggedInEmployee();
}
