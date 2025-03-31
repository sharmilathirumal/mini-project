package com.example.spring_thymeleaf.controller;

import com.example.spring_thymeleaf.dto.EmployeeDTO;
import com.example.spring_thymeleaf.dto.LeaveDTO;
import com.example.spring_thymeleaf.service.AuthService;
import com.example.spring_thymeleaf.service.EmployeeService;
import com.example.spring_thymeleaf.service.LeaveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("leave")
@CrossOrigin("*")
public class LeaveController {
    @Autowired
    private LeaveService leaveService;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private AuthService authService;

    @GetMapping("/add/{employeeId}")
    public String showAddForm(@PathVariable Long employeeId,Model model){
        LeaveDTO leave = new LeaveDTO();
        EmployeeDTO employee = employeeService.getEmployeeById(employeeId);
        leave.setEmployee(employee);
        model.addAttribute("leave",leave);
        model.addAttribute("employee",employee);
        return "add-leave";

    }


    @PostMapping("/add")
    public ResponseEntity<Map<String,Object>> addLeave(@ModelAttribute LeaveDTO leaveDTO){
        Map<String,Object> response = new HashMap<>();

        try{
            leaveService.applyForLeave(leaveDTO);
            response.put("success",true);
            response.put("message","Leave Added successfully");
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }catch (IllegalStateException e){
            response.put("success",false);
            response.put("message",e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }catch (Exception e){
            response.put("success",false);
            response.put("message","An Unexpected Error Occurred"+e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }


    @GetMapping("/edit/{id}")
    public String ShowUpdateForm(@PathVariable Long id,Model model){
        LeaveDTO leave = leaveService.getLeaveById(id);
        EmployeeDTO employee = employeeService.getEmployeeById(leave.getEmployee().getId());
        EmployeeDTO user = authService.getLoggedInEmployee();
        model.addAttribute("leave",leave);
        model.addAttribute("employee",employee);
        model.addAttribute("user",user);
        return "update-leave";
    }


    @PostMapping("/update")
    public ResponseEntity<Map<String,Object>> UpdateLeave(@ModelAttribute LeaveDTO leave){
        Map<String,Object> response = new HashMap<>();
        try{
            leaveService.updateLeaveRecord(leave);
            response.put("success",true);
            response.put("message","Leave Record Updated successfully");
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }catch (IllegalStateException e){
            response.put("success",false);
            response.put("message",e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }catch (Exception e){
            response.put("success",false);
            response.put("message",e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

    }

    @PreAuthorize("hasAuthority('MANAGER')")
    @GetMapping("/editStatus/{id}")
    public String ShowStatusUpdateForm(@PathVariable Long id,Model model){
        LeaveDTO leave = leaveService.getLeaveById(id);
        EmployeeDTO employee = employeeService.getEmployeeById(leave.getEmployee().getId());
        model.addAttribute("leave",leave);
        model.addAttribute("employee",employee);
        return "update-leavestatus";
    }

    @PreAuthorize("hasAuthority('MANAGER')")
    @PostMapping("/updateStatus")
    public ResponseEntity<Map<String,Object>> UpdateLeaveStatus(LeaveDTO leave){
        Map<String,Object> response = new HashMap<>();
        try{
            leaveService.updateLeaveStatus(leave);
            response.put("success",true);
            response.put("success",true);
            response.put("message","Leave Status Updated successfully");
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }catch (IllegalStateException e){
            response.put("success",false);
            response.put("message",e.getMessage());
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }catch (Exception e){
            response.put("success",false);
            response.put("message",e.getMessage());
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }

    }
    //@PreAuthorize("hasAnyAuthority('ADMIN','HR')")
    @GetMapping("/get-all-leaves")
    public List<LeaveDTO> GetAllLeaveDetails(){

        return leaveService.getAllLeaves();
    }


    @DeleteMapping("/delete/{leaveId}")
    public ResponseEntity<Map<String, String>> DeleteLeave(@PathVariable Long leaveId){
        Map<String, String> response = new HashMap<>();

        try {
            leaveService.deleteleaveById(leaveId);
                response.put("message", "Leave deleted successfully");
                return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (IllegalStateException e) {
            response.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }catch (Exception e){
            response.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','HR','MANAGER','EMPLOYEE')")
    @GetMapping("/get/{employeeId}")
    public String GetAllLeaveOfAnEmployeeById(@PathVariable Long employeeId, Model model){
        try{
            EmployeeDTO employee = employeeService.getEmployeeById(employeeId);
            List<LeaveDTO> leave = leaveService.getLeaveDetailsOfEmployee(employeeId);
            EmployeeDTO user = authService.getLoggedInEmployee();
            model.addAttribute("employee",employee);
            model.addAttribute("leaves",leave);
            model.addAttribute("user",user);
            return "view-leave";
        }
        catch (RuntimeException e){
            model.addAttribute("error",e.getMessage());
            return "error-page";
        }

    }
}
