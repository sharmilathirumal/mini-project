package com.example.spring_thymeleaf.service.Impl;

import com.example.spring_thymeleaf.dto.AttendanceDTO;
import com.example.spring_thymeleaf.dto.EmployeeDTO;
import com.example.spring_thymeleaf.entity.Attendance;
import com.example.spring_thymeleaf.entity.Employee;
import com.example.spring_thymeleaf.entity.Leave;
import com.example.spring_thymeleaf.enums.AttendanceStatus;
import com.example.spring_thymeleaf.enums.EmployeeStatus;
import com.example.spring_thymeleaf.enums.LeaveDuration;
import com.example.spring_thymeleaf.repository.AttendanceRepository;
import com.example.spring_thymeleaf.repository.EmployeeRepository;
import com.example.spring_thymeleaf.repository.LeaveRepository;
import com.example.spring_thymeleaf.service.AttendanceService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
public class AttendanceServiceImpl implements AttendanceService {

    @Autowired
    private AttendanceRepository attendanceRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private LeaveRepository leaveRepository;

    @Override
    public void addAttendance(AttendanceDTO attendanceDTO) {
        boolean isHalfDayLeave = false;
        Employee employee = employeeRepository.findById(attendanceDTO.getEmployee().getId())
                .orElseThrow(() -> new IllegalStateException("Employee not found"));
        if (attendanceRepository.existsByEmployeeIdAndDate(attendanceDTO.getEmployee().getId(), attendanceDTO.getDate())) {
            throw new IllegalStateException("You have recorded attendance for the same date, therefore, attendance cannot be added.");
        }
        Leave leave = leaveRepository.findByStartDate(attendanceDTO.getDate());
        if (leave != null) {
            if (Objects.equals(leave.getDuration(), LeaveDuration.HALF_DAY)) {
                isHalfDayLeave = true;
            }
        }

        if (!isHalfDayLeave) {
            if (leaveRepository.existsByEmployeeIdAndDate(attendanceDTO.getEmployee().getId(), attendanceDTO.getDate())) {
                throw new IllegalStateException("You have recorded leave for the same date, therefore, attendance cannot be added.");
            }
        }
        if (attendanceDTO.getDate().isAfter(LocalDate.now())) {
            throw new IllegalStateException("Future Date Cannot Be Added");
        }
        Attendance attendance = modelMapper.map(attendanceDTO, Attendance.class);
        attendance.setId(null);
        attendance.setEmployee(employee);
        attendanceRepository.save(attendance);
    }

    @Override
    public AttendanceDTO updateAttendanceRecords(AttendanceDTO attendanceDTO) {
        boolean isHalfDayLeave = false;
        Attendance attendance = attendanceRepository.findById(attendanceDTO.getId()).orElseThrow(() -> new IllegalStateException("Attendance Not Found"));
        if (!Objects.equals(attendanceDTO.getDate(), attendance.getDate())) {
            if (attendanceRepository.existsByEmployeeIdAndDate(attendanceDTO.getEmployee().getId(), attendanceDTO.getDate())) {
                throw new IllegalStateException("You have recorded attendance for the same date, therefore, attendance cannot be added.");
            }
            Leave leave = leaveRepository.findByStartDate(attendanceDTO.getDate());
            if (leave != null) {
                if (Objects.equals(leave.getDuration(), LeaveDuration.HALF_DAY)) {
                    isHalfDayLeave = true;
                }
            }

            if (!isHalfDayLeave) {
                if (leaveRepository.existsByEmployeeIdAndDate(attendanceDTO.getEmployee().getId(), attendanceDTO.getDate())) {
                    throw new IllegalStateException("You have recorded leave for the same date, therefore, attendance cannot be added.");
                }
            }
            attendance.setDate(attendanceDTO.getDate());
        }

        if (!Objects.equals(attendanceDTO.getCheckIn(), attendance.getCheckIn())) {
            attendance.setCheckIn(attendanceDTO.getCheckIn());
        }

        if (!Objects.equals(attendanceDTO.getCheckOut(), attendance.getCheckOut())) {
            attendance.setCheckOut(attendanceDTO.getCheckOut());
        }

        if (!Objects.equals(attendanceDTO.getAttendanceStatus(), attendance.getAttendanceStatus())) {
            attendance.setAttendanceStatus(attendanceDTO.getAttendanceStatus());
        }

        if (attendanceDTO.getDate().isAfter(LocalDate.now())) {
            throw new IllegalStateException("Future Date Cannot Be Added");
        }
        return modelMapper.map(attendanceRepository.save(attendance), AttendanceDTO.class);
    }

    @Override
    public AttendanceDTO checkIn(Long id) {
        Employee employee = employeeRepository.findById(id).orElseThrow(() -> new RuntimeException("Employee not found"));
        Attendance attendance = new Attendance();
        attendance.setAttendanceStatus(AttendanceStatus.WORK_FROM_OFFICE);
        attendance.setEmployee(employee);
        return modelMapper.map(attendanceRepository.save(attendance), AttendanceDTO.class);
    }

    @Override
    public AttendanceDTO checkOut(Long id) {

        Attendance attendance = attendanceRepository.findById(id).orElseThrow(() -> new RuntimeException("Employee don't have Check In details please add checkIn Details first"));

        if (attendance.getCheckOut() != null) {
            throw new RuntimeException("Check-Out Already recorded");
        }
        return modelMapper.map(attendanceRepository.save(attendance), AttendanceDTO.class);
    }

    @Override
    public List<AttendanceDTO> getAttendanceByEmployeeId(Long id) {
        Employee employee = employeeRepository.findById(id).orElseThrow(() -> new RuntimeException("Employee Not Found"));
        List<Attendance> attendanceList = attendanceRepository.findByEmployee_Id(id);
        List<AttendanceDTO> attendanceDTOS = new ArrayList<>();
        for (Attendance attendance : attendanceList) {
            AttendanceDTO dto = modelMapper.map(attendance, AttendanceDTO.class);
            //dto.setEmployee(modelMapper.map(employee, EmployeeDTO.class));
            attendanceDTOS.add(dto);
        }
        return attendanceDTOS;
    }

    @Override
    public List<AttendanceDTO> getAllAttendanceRecords() {
        List<Attendance> attendanceList = attendanceRepository.findAll();
        List<AttendanceDTO> attendanceDTOS = new ArrayList<>();
        for (Attendance attendance : attendanceList) {
            attendanceDTOS.add(modelMapper.map(attendance, AttendanceDTO.class));
        }
        return attendanceDTOS;
    }

    @Override
    public boolean deleteAttendanceRecords(Long attendanceId) {
        if (attendanceRepository.existsById(attendanceId)) {
            attendanceRepository.deleteById(attendanceId);
            return true;
        }
        return false; // Return false if attendance doesn't exist
    }

    @Override
    public AttendanceDTO getAttendanceById(Long id) {
        Attendance attendance = attendanceRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("Attendance not found with ID: " + id));
        AttendanceDTO attendanceDTO = modelMapper.map(attendance, AttendanceDTO.class);
        attendanceDTO.setEmployee(modelMapper.map(attendance.getEmployee(), EmployeeDTO.class));
        return attendanceDTO;
    }

}
