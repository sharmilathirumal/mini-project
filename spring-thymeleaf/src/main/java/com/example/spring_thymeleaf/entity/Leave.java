package com.example.spring_thymeleaf.entity;

import com.example.spring_thymeleaf.enums.LeaveStatus;
import com.example.spring_thymeleaf.enums.LeaveType;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Data
@Table(name = "leave_request")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Leave {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name ="employee_id",nullable = false)
    private Employee employee;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private LeaveType leaveType;

    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    private Date startDate;

    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    private Date endDate;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private LeaveStatus leaveStatus = LeaveStatus.PENDING;


    public Long getId() {
        return id;
    }

    public Employee getEmployee() {
        return employee;
    }

    public LeaveType getLeaveType() {
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

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public void setLeaveType(LeaveType leaveType) {
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
