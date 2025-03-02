package com.example.spring_thymeleaf.dto;

import com.example.spring_thymeleaf.enums.AttendanceStatus;
import lombok.Data;
import java.util.Date;

@Data
public class AttendanceDTO {
    private Long id;
    private Long employeeId;  // Reference to Employee
    private Date date;
    private Date checkIn;
    private Date checkOut;
    private AttendanceStatus attendanceStatus;
   // private String firstName;
  //  private String lastName;
    //private String email;

    public Long getEmployeeId() {
        return employeeId;
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

    public AttendanceStatus getAttendanceStatus() {
        return attendanceStatus;
    }

    public Long getId() {
        return id;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
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

    public void setId(Long id) {
        this.id = id;
    }

    public void setAttendanceStatus(AttendanceStatus attendanceStatus) {
        this.attendanceStatus = attendanceStatus;
    }
}
