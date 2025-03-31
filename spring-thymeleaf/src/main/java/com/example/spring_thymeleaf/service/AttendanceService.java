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
    AttendanceDTO updateAttendanceRecords(AttendanceDTO attendanceDTO);
    boolean deleteAttendanceRecords(Long id);
    void addAttendance(AttendanceDTO attendanceDTO);
    AttendanceDTO getAttendanceById(Long id);
}
