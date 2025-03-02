package com.example.spring_thymeleaf.dto;

import lombok.Data;
import java.util.Date;

@Data
public class PerformanceDTO {
    private int id;
    private Long employeeId;
    private Date reviewDate;
    private int performanceScore;
    private String comment;

    public int getId() {
        return id;
    }

    public Long getEmployeeId() {
        return employeeId;
    }

    public Date getReviewDate() {
        return reviewDate;
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

    public void setReviewDate(Date reviewDate) {
        this.reviewDate = reviewDate;
    }

    public void setPerformanceScore(int performanceScore) {
        this.performanceScore = performanceScore;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
