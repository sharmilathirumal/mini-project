package com.example.spring_thymeleaf.controller;

import com.example.spring_thymeleaf.dto.AttendanceDTO;
import com.example.spring_thymeleaf.enums.AttendanceStatus;
import com.example.spring_thymeleaf.service.Impl.AttendanceServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/attendance")
@CrossOrigin("*")
public class AttendanceController {
    @Autowired
    private AttendanceServiceImpl attendanceService;

    //@PreAuthorize("hasAuthority('EMPLOYEE')")
   @PostMapping("/checkin/{employeeId}")
    public AttendanceDTO EmployeeCheckIn(@PathVariable Long employeeId){
        return attendanceService.checkIn(employeeId);
    }

   // @PreAuthorize("hasAuthority('EMPLOYEE')")
   @PostMapping("/checkout/{attendanceId}")
    public AttendanceDTO EmployeeCheckOut(@PathVariable Long attendanceId){
        return attendanceService.checkOut(attendanceId);
    }

    //@PreAuthorize("hasAnyAuthority('ADMIN','HR','MANAGER','EMPLOYEE')")
   @GetMapping("/get/{employeeId}")
    public String getEmployeeAttendance(@PathVariable Long employeeId, Model model){
       List<AttendanceDTO> attendances =  attendanceService.getAttendanceByEmployeeId(employeeId);
         model.addAttribute("attendances",attendances);
         return "view-attendance";
    }

    //@PreAuthorize("hasAnyAuthority('ADMIN','HR')")
    @GetMapping("get-all-records/allattendancerecords")
    public List<AttendanceDTO> getAllAttendanceDetails(){
        return attendanceService.getAllAttendanceRecords();
    }

    /*//@PreAuthorize("hasAnyAuthority('ADMIN','HR')")
    @PutMapping("update-record/{attendanceId}")
    public AttendanceDTO updateAttendanceDetails(@PathVariable Long attendanceId,@RequestBody AttendanceDTO attendanceDTO){
        return attendanceService.updateAttendanceRecords(attendanceId,attendanceDTO);
    }*/

    @PutMapping("update-record/{attendanceId}")
    public AttendanceDTO updateAttendanceDetails
            (@PathVariable Long attendanceId,
             @RequestParam(required = false)@DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date date,
             @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)Date check_In,
             @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)Date check_out,
             @RequestParam(required = false)AttendanceStatus status){
        return attendanceService.updateAttendanceRecords(attendanceId,date,check_In,check_out,status);
    }

    //@PreAuthorize("hasAnyAuthority('ADMIN','HR')")
    @DeleteMapping("delete-record/{attendanceId}")
    public void deleteAttendacneById(@PathVariable Long attendanceId){
        attendanceService.deleteAttendanceRecords(attendanceId);
    }
}
