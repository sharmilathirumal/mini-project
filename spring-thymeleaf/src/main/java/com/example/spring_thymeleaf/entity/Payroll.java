package com.example.spring_thymeleaf.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Payroll {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;

    public Long getId() {
        return id;
    }

    public Employee getEmployee() {
        return employee;
    }

    @Column(nullable = false)
    private Double salary;

    @Column(nullable = false)
    private Date payDate;

    public Date getPayDate() {
        return payDate;
    }

    public Double getSalary() {
        return salary;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public void setPayDate(Date payDate) {
        this.payDate = payDate;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
    }


}
