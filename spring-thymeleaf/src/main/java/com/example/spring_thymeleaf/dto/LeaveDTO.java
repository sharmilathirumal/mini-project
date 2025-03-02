package com.example.spring_thymeleaf.dto;

import com.example.spring_thymeleaf.enums.LeaveStatus;
import lombok.Data;
import java.util.Date;

@Data
public class LeaveDTO {
    private Long id;
    private Long employeeId;
    private String leaveType;  // Enum as String
    private Date startDate;
    private Date endDate;
    private LeaveStatus leaveStatus;  // Enum as String

    public Long getId() {
        return id;
    }

    public Long getEmployeeId() {
        return employeeId;
    }

    public String getLeaveType() {
        return leaveType;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public LeaveStatus getLeaveStatus() {
        return leaveStatus;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    public void setLeaveType(String leaveType) {
        this.leaveType = leaveType;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public void setLeaveStatus(LeaveStatus leaveStatus) {
        this.leaveStatus = leaveStatus;
    }
}
