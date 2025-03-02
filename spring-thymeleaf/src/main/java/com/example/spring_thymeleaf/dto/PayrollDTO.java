package com.example.spring_thymeleaf.dto;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class PayrollDTO {
    private Long id;
    private Long employeeId;
    private Double salary;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date payDate;

    public Long getId() {
        return id;
    }

    public Long getEmployeeId() {
        return employeeId;
    }

    public Double getSalary() {
        return salary;
    }

    public Date getPayDate() {
        return payDate;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
    }

    public void setPayDate(Date payDate) {
        this.payDate = payDate;
    }
}
