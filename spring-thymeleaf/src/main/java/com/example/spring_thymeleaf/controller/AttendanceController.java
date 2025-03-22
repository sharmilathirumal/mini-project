package com.example.spring_thymeleaf.controller;

import com.example.spring_thymeleaf.dto.AttendanceDTO;
import com.example.spring_thymeleaf.dto.EmployeeDTO;
import com.example.spring_thymeleaf.dto.TeamDTO;
import com.example.spring_thymeleaf.enums.AttendanceStatus;
import com.example.spring_thymeleaf.service.AttendanceService;
import com.example.spring_thymeleaf.service.EmployeeService;
import com.example.spring_thymeleaf.service.Impl.AttendanceServiceImpl;
import com.example.spring_thymeleaf.service.Impl.EmployeeServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.text.ParseException;
import java.util.Date;
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
    @GetMapping("/add/{employeeId}")
    public String showAddAttendanceForm(@PathVariable("employeeId") Long employeeId, Model model) {
        AttendanceDTO attendanceDTO = new AttendanceDTO();
        attendanceDTO.setEmployeeId(employeeId); // Set employeeId in DTO

        model.addAttribute("attendance", attendanceDTO);

        // Fetch and set selected employee details
        EmployeeDTO selectedEmployee = employeeService.getEmployeeById(employeeId);
        model.addAttribute("employee", selectedEmployee);

        return "add-attendance"; // Return Thymeleaf template
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

    /*@PostMapping("/add")
    public String addAttendance(@ModelAttribute AttendanceDTO attendanceDTO, RedirectAttributes redirectAttributes) {
        try {
            attendanceService.addAttendance(attendanceDTO);
            redirectAttributes.addFlashAttribute("successMessage", "Attendance added successfully!");
        } catch (IllegalStateException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }

        return "redirect:/hradmin/dashboard";
    }*/

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

    //@PreAuthorize("hasAnyAuthority('ADMIN','HR','MANAGER','EMPLOYEE')")
   @GetMapping("/get/{employeeId}")
    public String getEmployeeAttendance(@PathVariable Long employeeId, Model model){
       try{
           EmployeeDTO employee = employeeService.getEmployeeById(employeeId);
           List<AttendanceDTO> attendances =  attendanceService.getAttendanceByEmployeeId(employeeId);
           model.addAttribute("attendances",attendances);
           model.addAttribute("employee", employee);
           return "view-attendance";
       }
       catch (RuntimeException e){
           model.addAttribute("error",e.getMessage());
           return "error-page";
       }
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

    @GetMapping("/edit/{id}")
    // public EmployeeDTO updateEmployeeDetail(@PathVariable(value = "id") Long id,@ModelAttribute("updatedEmployee") EmployeeDTO employeeDto){
    public String showUpdateForm(@PathVariable(value = "id") Long id,Model model){
        AttendanceDTO attendanceDTO = attendanceService.getAttendanceById(id);
        EmployeeDTO selectedEmployee = employeeService.getEmployeeById(attendanceDTO.getEmployeeId());
        model.addAttribute("attendance", attendanceDTO);
        model.addAttribute("employee", selectedEmployee);
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
            (@RequestBody AttendanceDTO attendanceDTO){
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
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
        } catch (IllegalStateException e) {
            response.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

}
