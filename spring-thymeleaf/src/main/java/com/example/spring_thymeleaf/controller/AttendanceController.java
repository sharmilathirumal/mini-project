package com.example.spring_thymeleaf.controller;

import com.example.spring_thymeleaf.dto.AttendanceDTO;
import com.example.spring_thymeleaf.dto.EmployeeDTO;
import com.example.spring_thymeleaf.dto.UserDTO;
import com.example.spring_thymeleaf.entity.User;
import com.example.spring_thymeleaf.service.AttendanceService;
import com.example.spring_thymeleaf.service.AuthService;
import com.example.spring_thymeleaf.service.EmployeeService;
import com.example.spring_thymeleaf.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("attendance")
@CrossOrigin("*")
public class AttendanceController {
    @Autowired
    private AttendanceService attendanceService;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private UserService userService;

    @Autowired
    private AuthService authService;

    @GetMapping("/add/{employeeId}")
    public String showAddAttendanceForm(@PathVariable("employeeId") Long employeeId, Model model) {
        EmployeeDTO user = authService.getLoggedInEmployee();
        AttendanceDTO attendanceDTO = new AttendanceDTO();
        attendanceDTO.setEmployee(employeeService.getEmployeeById(employeeId));
        EmployeeDTO selectedEmployee = employeeService.getEmployeeById(employeeId);
        model.addAttribute("user",user);
        model.addAttribute("attendance", attendanceDTO);
        model.addAttribute("employee", selectedEmployee);
        return "add-attendance";
    }

    @PostMapping("/add")
    public ResponseEntity<Map<String, Object>> addAttendance(@ModelAttribute AttendanceDTO attendanceDTO) {
        Map<String, Object> response = new HashMap<>();

        try {
            attendanceService.addAttendance(attendanceDTO);
            response.put("success", true);
            response.put("message", "Attendance added successfully!");
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (IllegalStateException e) {
            response.put("success", false);
            response.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "An unexpected error occurred.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }


    //@PreAuthorize("hasAuthority('EMPLOYEE')")
    @PostMapping("/checkin/{employeeId}")
    public AttendanceDTO EmployeeCheckIn(@PathVariable Long employeeId) throws ParseException {
        return attendanceService.checkIn(employeeId);
    }

    // @PreAuthorize("hasAuthority('EMPLOYEE')")
    @PostMapping("/checkout/{attendanceId}")
    public AttendanceDTO EmployeeCheckOut(@PathVariable Long attendanceId) throws ParseException {
        return attendanceService.checkOut(attendanceId);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','HR','MANAGER','EMPLOYEE')")
    @GetMapping("/get/{employeeId}")
    public String getEmployeeAttendance(@PathVariable Long employeeId, Model model) {
        EmployeeDTO user = authService.getLoggedInEmployee();
        EmployeeDTO employee = employeeService.getEmployeeById(employeeId);
        List<AttendanceDTO> attendances = attendanceService.getAttendanceByEmployeeId(employeeId);
        model.addAttribute("attendances", attendances);
        model.addAttribute("user",user);
        model.addAttribute("employee", employee);
        return "view-attendance";
    }

    //@PreAuthorize("hasAnyAuthority('ADMIN','HR')")
    @GetMapping("get-all-records/allattendancerecords")
    public List<AttendanceDTO> getAllAttendanceDetails() {
        return attendanceService.getAllAttendanceRecords();
    }

    @GetMapping("/edit/{id}")
    public String showUpdateForm(@PathVariable(value = "id") Long id, Model model) {
        EmployeeDTO user = authService.getLoggedInEmployee();
        AttendanceDTO attendanceDTO = attendanceService.getAttendanceById(id);
        EmployeeDTO selectedEmployee = employeeService.getEmployeeById(attendanceDTO.getEmployee().getId());
        model.addAttribute("attendance", attendanceDTO);
        model.addAttribute("employee", selectedEmployee);
        model.addAttribute("user",user);
        return "update-attendance";
    }

    @PostMapping("/update")
    public ResponseEntity<Map<String, Object>> updateAttendance(@ModelAttribute AttendanceDTO attendanceDTO) {
        Map<String, Object> response = new HashMap<>();

        try {
            attendanceService.updateAttendanceRecords(attendanceDTO);
            response.put("success", true);
            response.put("message", "Attendance updated successfully!");
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (IllegalStateException e) {
            response.put("success", false);
            response.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "An unexpected error occurred.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PutMapping("update/{attendanceId}")
    public AttendanceDTO updateAttendanceDetails
            (@RequestBody AttendanceDTO attendanceDTO) {
        return attendanceService.updateAttendanceRecords(attendanceDTO);
    }


    @DeleteMapping("delete/{attendanceId}")
    public ResponseEntity<Map<String, String>> deleteAttendance(@PathVariable Long attendanceId) {
        Map<String, String> response = new HashMap<>();

        try {
            boolean deleted = attendanceService.deleteAttendanceRecords(attendanceId);
            if (deleted) {
                response.put("message", "Attendance deleted successfully");
                return ResponseEntity.ok(response);
            } else {
                response.put("error", "Attendance record not found");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }
        } catch (IllegalStateException e) {
            response.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

}
