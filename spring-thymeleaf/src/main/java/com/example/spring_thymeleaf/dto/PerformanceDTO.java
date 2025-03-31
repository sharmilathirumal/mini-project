package com.example.spring_thymeleaf.dto;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.Date;

@Data
public class PerformanceDTO {
    private int id;
    private Long employeeId;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate reviewDate;
    private int performanceScore;
    private String comment;

    public int getId() {
        return id;
    }

    public Long getEmployeeId() {
        return employeeId;
    }

    public int getPerformanceScore() {
        return performanceScore;
    }

    public String getComment() {
        return comment;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    public void setPerformanceScore(int performanceScore) {
        this.performanceScore = performanceScore;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public LocalDate getReviewDate() {
        return reviewDate;
    }

    public void setReviewDate(LocalDate reviewDate) {
        this.reviewDate = reviewDate;
    }
}
