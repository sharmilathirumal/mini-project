package com.example.spring_thymeleaf.entity;


import com.example.spring_thymeleaf.enums.TeamName;
import com.example.spring_thymeleaf.enums.TeamStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Team {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @ManyToOne
    @JoinColumn(name = "department_id", nullable = false)
    private Department department; // Team belongs to a Department

    @Column(nullable = false)
    private String description; // Brief about the team’s function

    @Temporal(TemporalType.DATE)
    @Column(nullable = false)
    private LocalDate createdDate; // When the team was formed

    @Enumerated(EnumType.STRING)
    private TeamStatus status; // ACTIVE, INACTIVE, ARCHIVED

    @OneToMany(mappedBy = "team")
    private List<Employee> employee; // Employees in the team

   // @Column(nullable = false)
    @ManyToOne
    @JoinColumn(name = "manager_id",nullable = false)
    private Employee manager;

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }


    public Department getDepartment() {
        return department;
    }

    public String getDescription() {
        return description;
    }

    public TeamStatus getStatus() {
        return status;
    }

    public List<Employee> getEmployee() {
        return employee;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }


    public void setDepartment(Department department) {
        this.department = department;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public void setStatus(TeamStatus status) {
        this.status = status;
    }

    public void setEmployee(List<Employee> employee) {
        this.employee = employee;
    }

    public Employee getManager() {
        return manager;
    }

    public void setManager(Employee manager) {
        this.manager = manager;
    }

    public LocalDate getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
    }
}
