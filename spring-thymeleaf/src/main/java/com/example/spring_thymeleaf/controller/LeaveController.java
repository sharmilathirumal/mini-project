package com.example.spring_thymeleaf.controller;

import com.example.spring_thymeleaf.dto.LeaveDTO;
import com.example.spring_thymeleaf.entity.Leave;
import com.example.spring_thymeleaf.enums.LeaveStatus;
import com.example.spring_thymeleaf.enums.LeaveType;
import com.example.spring_thymeleaf.service.Impl.LeaveServiceImpl;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("leave")
@CrossOrigin("*")
public class LeaveController {
    @Autowired
    private LeaveServiceImpl leaveService;

   // @PreAuthorize("hasAuthority('EMPLOYEE)")
    @PostMapping("/apply/{employeeid}")
    public LeaveDTO ApplyToLeave(@PathVariable Long employeeid,@RequestParam LeaveType type,@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date startDate,
                             @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date endDate){
      return leaveService.ApplyForLeave(employeeid,endDate,type,startDate);
    }

    //@PreAuthorize("hasAuthority('MANAGER')")
    @PutMapping("/updaterecord/{id}")
    public LeaveDTO UpdateLeaveRecord(@PathVariable Long id, @RequestParam(required = false)LeaveType leaveType,
                                  @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date startDate,
                                  @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date endDate){
       return leaveService.UpdateLeaveRecord(id,leaveType,startDate,endDate);
    }

    @GetMapping("/edit/{id}")
    public String ShowUpdateForm(@PathVariable Long id,Model model){
        LeaveDTO leave = leaveService.getLeaveById(id);
        model.addAttribute("leave",leave);
        return "update-leave-status";
    }

    @PostMapping("/update")
    public String UpdateLeaveStatus(@ModelAttribute LeaveDTO leave){
          leaveService.UpdateLeaveStatus(leave);
            return "redirect:/admin/dashboard";
    }

    //@PreAuthorize("hasAnyAuthority('ADMIN','HR')")
    @GetMapping("/get-all-leaves")
    public List<LeaveDTO> GetAllLeaveDetails(){

        return leaveService.GetAllLeaves();
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
    public String DeleteLeave(@PathVariable Long leaveId){
        leaveService.DeleteleaveById(leaveId);
        return  "Leave record deleted successfully";
    }

    @GetMapping("/get/{employeeId}")
    public String GetAllLeaveOfAnEmployeeById(@PathVariable Long employeeId, Model model){
        List<LeaveDTO> leave = leaveService.GetLeaveDetailsOfEmployee(employeeId);
        model.addAttribute("leave",leave);
        return "view-leave";
    }
}
