package com.example.spring_thymeleaf.entity;

import com.example.spring_thymeleaf.enums.AttendanceStatus;
import com.example.spring_thymeleaf.enums.EmployeeStatus;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Attendance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    private Date date;

    @Temporal(TemporalType.TIMESTAMP)
    private Date checkIn;

    @Temporal(TemporalType.TIMESTAMP)
    private Date checkOut;

    @Enumerated(EnumType.STRING)
    private AttendanceStatus attendanceStatus;

    @ManyToOne()
    @JoinColumn(name ="employee_id",nullable = false)
    private Employee employee;

    public Employee getEmployee() {
        return employee;
    }

    public Date getDate() {
        return date;
    }

    public Date getCheckIn() {
        return checkIn;
    }

    public Date getCheckOut() {
        return checkOut;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setCheckIn(Date checkIn) {
        this.checkIn = checkIn;
    }

    public void setCheckOut(Date checkOut) {
        this.checkOut = checkOut;
    }

    public Long getId() {
        return id;
    }

    public AttendanceStatus getAttendanceStatus() {
        return attendanceStatus;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setAttendanceStatus(AttendanceStatus attendanceStatus) {
        this.attendanceStatus = attendanceStatus;
    }
}
