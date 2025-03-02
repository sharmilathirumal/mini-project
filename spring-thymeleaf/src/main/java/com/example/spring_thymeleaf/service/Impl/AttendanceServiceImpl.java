package com.example.spring_thymeleaf.service.Impl;

import com.example.spring_thymeleaf.dto.AttendanceDTO;
import com.example.spring_thymeleaf.entity.Attendance;
import com.example.spring_thymeleaf.entity.Employee;
import com.example.spring_thymeleaf.enums.AttendanceStatus;
import com.example.spring_thymeleaf.enums.EmployeeStatus;
import com.example.spring_thymeleaf.repository.AttendanceRepository;
import com.example.spring_thymeleaf.repository.EmployeeRepository;
import com.example.spring_thymeleaf.service.AttendanceService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class AttendanceServiceImpl implements AttendanceService {

    @Autowired
    private AttendanceRepository attendanceRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public AttendanceDTO checkIn(Long id){
        Employee employee = employeeRepository.findById(id).orElseThrow(() -> new RuntimeException("Employee not found"));
        Attendance attendance = new Attendance();
        /*SimpleDateFormat dateFormate = new SimpleDateFormat("dd-MM-yyyy");
       / SimpleDateFormat dateTimeFormate = new SimpleDateFormat("dd-MM-yyyy HH:mm");

        attendance.setEmployee(employee);
        String date = dateFormate.format(new Date());
        attendance.setDate(dateFormate.parse(date));
        String dateTime = dateTimeFormate.format(new Date());
        attendance.setCheckIn(dateTimeFormate.parse(dateTime));
        attendance.setAttendanceStatus(AttendanceStatus.PRESENT);*/
        attendance.setDate(new Date());
        attendance.setCheckIn(new Date());
        attendance.setAttendanceStatus(AttendanceStatus.WORK_FROM_OFFICE);
        attendance.setEmployee(employee);
        return modelMapper.map(attendanceRepository.save(attendance),AttendanceDTO.class);
    }

    @Override
    public AttendanceDTO checkOut(Long id){
       // List<Employee> employee = attendanceRepository.findByEmployee_Id();
        //Long emp_id =employee.
        Attendance attendance = attendanceRepository.findById(id).orElseThrow(()->new RuntimeException("Employee don't have Check In details please add checkIn Details first"));

        if(attendance.getCheckOut()!=null){
            throw new RuntimeException("Check-Out Already recorded");
        }
       /* SimpleDateFormat dateTime = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        String check_OutDate = dateTime.format(new Date());
        attendance.setCheckOut(dateTime.parse(check_OutDate));*/
        attendance.setCheckOut(new Date());
        return modelMapper.map(attendanceRepository.save(attendance),AttendanceDTO.class);
    }

    @Override
    public List<AttendanceDTO> getAttendanceByEmployeeId(Long id) {
        Employee employee = employeeRepository.findById(id).orElseThrow(()->new RuntimeException("Employee Not Found"));
        List<Attendance> attendanceList = attendanceRepository.findByEmployee_Id(id);
        List<AttendanceDTO> attendanceDTOS = new ArrayList<>();
        for(Attendance attendance :attendanceList){
            attendanceDTOS.add(modelMapper.map(attendance,AttendanceDTO.class));
        }
        return attendanceDTOS;
    }

    @Override
    public List<AttendanceDTO> getAllAttendanceRecords() {
      List<Attendance> attendanceList = attendanceRepository.findAll();
      List<AttendanceDTO> attendanceDTOS = new ArrayList<>();
      for(Attendance attendance : attendanceList){
          attendanceDTOS.add(modelMapper.map(attendance,AttendanceDTO.class));
      }
      return attendanceDTOS;
    }

    /*@Override
    public AttendanceDTO updateAttendanceRecords(Long id, AttendanceDTO attendanceDTO) {
        Attendance attendance = attendanceRepository.findById(id).orElseThrow(()->new RuntimeException("Attendance Record Not Found"));

        if(attendance.getDate() !=attendanceDTO.getDate()){
            attendance.setDate(attendanceDTO.getDate());
        }

        if(attendance.getCheckIn() !=attendanceDTO.getCheckIn()){
            attendance.setCheckIn(attendanceDTO.getCheckIn());
        }
        if(attendance.getCheckOut() !=attendanceDTO.getCheckOut()){
            attendance.setCheckOut(attendanceDTO.getCheckOut());
        }
        if(!attendance.getAttendanceStatus().equals(attendanceDTO.getAttendanceStatus())){
            attendance.setAttendanceStatus(attendanceDTO.getAttendanceStatus());
        }
        return modelMapper.map(attendanceRepository.save(attendance),AttendanceDTO.class);
    }*/

    public AttendanceDTO updateAttendanceRecords
            (Long attendance_id,Date date,Date check_In,Date check_Out,AttendanceStatus status ) {
        Attendance attendance = attendanceRepository.findById(attendance_id).orElseThrow(()->new RuntimeException("Attendance Not Found"));
            if(date != null){
                attendance.setDate(date);
            }

            if(check_In != null){
                attendance.setCheckIn(check_In);
            }

            if(check_Out !=null){
                attendance.setCheckOut(check_Out);
            }

            if(status !=null){
                attendance.setAttendanceStatus(status);
            }
            return modelMapper.map(attendanceRepository.save(attendance),AttendanceDTO.class);
    }
    @Override
    public void deleteAttendanceRecords(Long id) {
        Attendance attendance = attendanceRepository.findById(id).orElseThrow(()->new RuntimeException("Attendance Record Not Found"));
        attendanceRepository.deleteById(id);
    }

}
