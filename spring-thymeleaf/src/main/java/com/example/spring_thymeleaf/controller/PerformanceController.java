package com.example.spring_thymeleaf.controller;

import com.example.spring_thymeleaf.dto.PerformanceDTO;
//import com.example.spring_thymeleaf.entity.User;
import com.example.spring_thymeleaf.service.Impl.PerformanceServiceImpl;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("performance")
@CrossOrigin("*")
public class PerformanceController {

    @Autowired
    private PerformanceServiceImpl performanceService;

    //@PreAuthorize("hasAuthority('MANAGER')")
    @PostMapping("/addperformance")
    public PerformanceDTO Add(@RequestBody PerformanceDTO performanceDTO){
        return performanceService.AddPerformance(performanceDTO);
    }

   // @PreAuthorize("hasAuthority('MANAGER')")
    @PutMapping("/updateperformance/{payroll_id}")
    public PerformanceDTO UpdatePerformance(@PathVariable Long payroll_id, @RequestBody PerformanceDTO performanceDTO){
        return performanceService.UpdatePerformance(payroll_id,performanceDTO);
    }

    //@PreAuthorize("hasAnyAuthority('MANAGER','HR')")
    @GetMapping("/getallperformance")
    public List<PerformanceDTO> GetAllPerformanceDetail(){
        return performanceService.GetAllPerformanceDetails();
    }

    //@PreAuthorize("hasAuthority('EMPLOYEE')")
    @GetMapping("/get/{employeeId}")
    public String GetPerformanceByEmployeeId(@PathVariable Long employeeId, Model model){
        List<PerformanceDTO> performance = performanceService.GetPerformanceById(employeeId);
        model.addAttribute("performance",performance);
        return "view-performance";
    }

    //@PreAuthorize("hasAuthority('MANAGER')")
    @DeleteMapping("/deleteperformance/{payroll_id}")
    public void DeletePerformance(@PathVariable Long payroll_id){
        performanceService.DeletePerformance(payroll_id);
    }
}
