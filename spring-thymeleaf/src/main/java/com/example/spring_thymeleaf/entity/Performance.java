package com.example.spring_thymeleaf.entity;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Performance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "employee_id",nullable = false)
    private Employee employee;

    @Column(nullable = false)
    private Date reviewDate;

    @Column(nullable = false)
    @Min(1)
    @Max(10)
    private int performanceScore;

    @Column(nullable = true)
    private String comment;

    public Long getId() {
        return id;
    }

    public Employee getEmployee() {
        return employee;
    }

    public Date getReviewDate() {
        return reviewDate;
    }

    public @Min(1) @Max(10) int getPerformanceScore() {
        return performanceScore;
    }

    public String getComment() {
        return comment;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }


    public void setReviewDate(Date reviewDate) {
        this.reviewDate = reviewDate;
    }

    public void setPerformanceScore(@Min(1) @Max(10) int performanceScore) {
        this.performanceScore = performanceScore;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
