package com.example.spring_thymeleaf.security;

import com.example.spring_thymeleaf.entity.Employee;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.List;

public class CustomUserDetails extends User {

    private Long employeeId;

    private String role;
    public CustomUserDetails(com.example.spring_thymeleaf.entity.User user, Employee employee){
        super(user.getUserName(),user.getPassword(), List.of(new SimpleGrantedAuthority(employee.getRole().toString())));
        this.employeeId=employee.getId();
        this.role=employee.getRole().toString();
    }

    public Long getEmployeeId() {
        return employeeId;
    }

    public String getRole() {
        return role;
    }
}
