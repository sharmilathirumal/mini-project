package com.example.spring_thymeleaf.controller;

import com.example.spring_thymeleaf.dto.EmployeeDTO;
import com.example.spring_thymeleaf.dto.LeaveDTO;
import com.example.spring_thymeleaf.entity.Leave;
import com.example.spring_thymeleaf.enums.LeaveStatus;
import com.example.spring_thymeleaf.enums.LeaveType;
import com.example.spring_thymeleaf.service.EmployeeService;
import com.example.spring_thymeleaf.service.Impl.EmployeeServiceImpl;
import com.example.spring_thymeleaf.service.Impl.LeaveServiceImpl;
import com.example.spring_thymeleaf.service.LeaveService;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
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

   // @PreAuthorize("hasAuthority('EMPLOYEE)")
    @GetMapping("/add/{employeeId}")
    public String ApplyToLeave(@PathVariable Long employeeId,Model model){
        LeaveDTO leave = new LeaveDTO();
        leave.setEmployeeId(employeeId);
        model.addAttribute("leave",leave);
        EmployeeDTO employee = employeeService.getEmployeeById(employeeId);
        model.addAttribute("employee",employee);
        return "add-leave";

    }

    @PostMapping("/add")
    public ResponseEntity<Map<String,Object>> addLeave(LeaveDTO leaveDTO){
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
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    //@PreAuthorize("hasAuthority('MANAGER')")
   /*PutMapping("/updaterecord/{id}")
    public LeaveDTO UpdateLeaveRecord(@PathVariable Long id, @RequestParam(required = false)LeaveType leaveType,
                                  @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date startDate,
                                  @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date endDate){
       return leaveService.UpdateLeaveRecord(id,leaveType,startDate,endDate);
    }*/

    @GetMapping("/edit/{id}")
    public String ShowUpdateForm(@PathVariable Long id,Model model){
        LeaveDTO leave = leaveService.getLeaveById(id);
        EmployeeDTO employee = employeeService.getEmployeeById(leave.getEmployeeId());
        model.addAttribute("leave",leave);
        model.addAttribute("employee",employee);
        return "update-leave";
    }

    @PostMapping("/update")
    public ResponseEntity<Map<String,Object>> UpdateLeaveStatus(LeaveDTO leave){
        Map<String,Object> response = new HashMap<>();
        try{
            leaveService.updateLeaveRecord(leave);
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
    /*@PostMapping("/update")
    public ResponseEntity<Map<String,Object>> UpdateLeaveStatus(LeaveDTO leave){
        Map<String,Object> response = new HashMap<>();
        try{
            leaveService.UpdateLeaveStatus(leave);
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

    }*/
    //@PreAuthorize("hasAnyAuthority('ADMIN','HR')")
    @GetMapping("/get-all-leaves")
    public List<LeaveDTO> GetAllLeaveDetails(){

        return leaveService.getAllLeaves();
    }

    /*@PreAuthorize("hasAnyAuthority('ADMIN','HR','EMPLOYEE')")
    @GetMapping("/getleaves/{id}")
    public List<LeaveDTO> GetLeaveDetailById(@PathVariable Long id){
        return leaveService.GetLeavesById(id);
    }*/

   // @PreAuthorize("hasAuthority('EMPLOYEE')")
   /* @PutMapping("/{leaveId}/cancel")
    public String CancelLeave(@PathVariable Long id){
        leaveService.CancelLeaveRequest(id);
        return "leave cancelled successfully";
    }*/

    //@PreAuthorize("hasAnyAuthority('ADMIN','HR')")
    @DeleteMapping("delete/{leaveId}")
    public ResponseEntity<Map<String, String>> DeleteLeave(@PathVariable Long leaveId){
        Map<String, String> response = new HashMap<>();

        try {
            boolean deleted = leaveService.deleteleaveById(leaveId);
            if (deleted) {
                response.put("message", "Leave deleted successfully");
                return ResponseEntity.ok(response);
            } else {
                response.put("error", "Leave record not found");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
        } catch (IllegalStateException e) {
            response.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @GetMapping("/get/{employeeId}")
    public String GetAllLeaveOfAnEmployeeById(@PathVariable Long employeeId, Model model){
        try{
            EmployeeDTO employee = employeeService.getEmployeeById(employeeId);
            List<LeaveDTO> leave = leaveService.getLeaveDetailsOfEmployee(employeeId);
            model.addAttribute("employee",employee);
            model.addAttribute("leaves",leave);
            return "view-leave";
        }
        catch (RuntimeException e){
            model.addAttribute("error",e.getMessage());
            return "error-page";
        }

    }
}
