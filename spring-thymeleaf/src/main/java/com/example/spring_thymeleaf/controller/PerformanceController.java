package com.example.spring_thymeleaf.controller;

import com.example.spring_thymeleaf.dto.EmployeeDTO;
import com.example.spring_thymeleaf.dto.PerformanceDTO;
//import com.example.spring_thymeleaf.entity.User;
import com.example.spring_thymeleaf.enums.Role;
import com.example.spring_thymeleaf.service.AuthService;
import com.example.spring_thymeleaf.service.EmployeeService;
import com.example.spring_thymeleaf.service.Impl.EmployeeServiceImpl;
import com.example.spring_thymeleaf.service.Impl.PerformanceServiceImpl;
import com.example.spring_thymeleaf.service.PerformanceService;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.access.prepost.PreAuthorize;
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
@RequestMapping("performance")
@CrossOrigin("*")
@EnableWebSecurity
@EnableMethodSecurity
public class PerformanceController {

    @Autowired
    private PerformanceService performanceService;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private AuthService authService;


    @GetMapping("/add/{employeeId}")
    public String showForm(@PathVariable Long employeeId,Model model){
        EmployeeDTO employee = employeeService.getEmployeeById(employeeId);
        EmployeeDTO user = authService.getLoggedInEmployee();
        model.addAttribute("performance",new PerformanceDTO());
        model.addAttribute("employee",employee);
        model.addAttribute("user",user);
        return "add-performance";
    }

    @PostMapping("/add")
    public ResponseEntity<Map<String,Object>> addPerformance(@ModelAttribute PerformanceDTO performanceDTO){
        Map<String,Object> response = new HashMap<>();
        try{
            performanceService.addPerformance(performanceDTO);
            response.put("success",true);
            response.put("message","Performance Added Successfully!!");
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }catch (IllegalStateException e){
            response.put("success",false);
            response.put("message",e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        } catch (Exception e){
            response.put("success",false);
            response.put("message",e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }
    @PreAuthorize("hasAuthority('MANAGER')")
    @PutMapping("/updateperformance/{payroll_id}")
    public PerformanceDTO updatePerformance(@PathVariable Long payroll_id, @RequestBody PerformanceDTO performanceDTO){
        return performanceService.updatePerformance(payroll_id,performanceDTO);
    }

    @PreAuthorize("hasAnyAuthority('MANAGER','HR','ADMIN')")
    @GetMapping("/getallperformance")
    public List<PerformanceDTO> getAllPerformanceDetail(){
        return performanceService.getAllPerformanceDetails();
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','HR','MANAGER','EMPLOYEE')")
    @GetMapping("/get/{employeeId}")
    public String getPerformanceByEmployeeId(@PathVariable Long employeeId, Model model){
            EmployeeDTO employee = employeeService.getEmployeeById(employeeId);
            List<PerformanceDTO> performance = performanceService.getPerformanceById(employeeId);
            EmployeeDTO user = authService.getLoggedInEmployee();
            model.addAttribute("employee",employee);
            model.addAttribute("performances",performance);
            model.addAttribute("user",user);
            return "view-performance";

    }

    @PreAuthorize("hasAuthority('MANAGER')")
    @DeleteMapping("/deleteperformance/{id}")
    public void deletePerformance(@PathVariable Long id){
        performanceService.deletePerformance(id);
    }
}
