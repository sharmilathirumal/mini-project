package com.example.spring_thymeleaf.service;

import com.example.spring_thymeleaf.dto.AttendanceDTO;
import com.example.spring_thymeleaf.entity.Attendance;
import com.example.spring_thymeleaf.enums.AttendanceStatus;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

public interface AttendanceService {

    AttendanceDTO checkIn(Long id) throws ParseException;
    AttendanceDTO checkOut(Long id) throws ParseException;
    List<AttendanceDTO> getAttendanceByEmployeeId(Long id);
    List<AttendanceDTO> getAllAttendanceRecords();
    AttendanceDTO updateAttendanceRecords(Long id, Date date, Date checkIn, Date checkOut, AttendanceStatus status);
    void deleteAttendanceRecords(Long id);
}
