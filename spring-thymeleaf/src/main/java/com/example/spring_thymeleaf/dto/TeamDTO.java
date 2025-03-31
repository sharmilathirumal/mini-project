package com.example.spring_thymeleaf.dto;

import com.example.spring_thymeleaf.entity.Department;
import com.example.spring_thymeleaf.entity.Employee;
import com.example.spring_thymeleaf.enums.TeamName;
import com.example.spring_thymeleaf.enums.TeamStatus;
import jakarta.persistence.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public class TeamDTO {
    private Long id;

    private String name;

    private EmployeeDTO manager; // Team Manager

    private DepartmentDTO department; // Team belongs to a Department

    private String description; // Brief about the team’s function
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate createdDate; // When the team was formed

    private TeamStatus status; // ACTIVE, INACTIVE, ARCHIVED
// Employees in the team

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public TeamStatus getStatus() {
        return status;
    }


    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setStatus(TeamStatus status) {
        this.status = status;
    }

    public EmployeeDTO getManager() {
        return manager;
    }

    public void setManager(EmployeeDTO manager) {
        this.manager = manager;
    }

    public DepartmentDTO getDepartment() {
        return department;
    }

    public void setDepartment(DepartmentDTO department) {
        this.department = department;
    }

    public LocalDate getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
    }
}
