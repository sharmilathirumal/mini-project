package com.example.spring_thymeleaf.controller;

import com.example.spring_thymeleaf.dto.EmployeeDTO;
import com.example.spring_thymeleaf.dto.PayrollDTO;
import com.example.spring_thymeleaf.service.AuthService;
import com.example.spring_thymeleaf.service.EmployeeService;
import com.example.spring_thymeleaf.service.Impl.EmployeeServiceImpl;
import com.example.spring_thymeleaf.service.Impl.PayrollServiceImpl;
import com.example.spring_thymeleaf.service.PayrollService;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.access.prepost.PreAuthorize;
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
@RequestMapping("payroll")
@CrossOrigin("*")
@EnableWebSecurity
@EnableMethodSecurity
public class PayrollController {
    @Autowired
    private PayrollService payrollService;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private AuthService authService;

    @PreAuthorize("hasAnyAuthority('ADMIN','HR')")
    @GetMapping("/add/{employeeId}")
    public String ShowAddPayrollForm(@PathVariable Long employeeId,Model model){
        EmployeeDTO user = authService.getLoggedInEmployee();
        model.addAttribute("payroll",new PayrollDTO());
        model.addAttribute("employee",employeeService.getEmployeeById(employeeId));
        model.addAttribute("user",user);
        return "add-payroll";
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','HR')")
    @PostMapping("/add")
    public ResponseEntity<Map<String,Object>> add(@ModelAttribute PayrollDTO payrollDTO){
        Map<String,Object> response = new HashMap<>();
        try{
            payrollService.addPayroll(payrollDTO);
            response.put("success",true);
            response.put("message","Payroll Added to Employee Successfully");
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (IllegalStateException e) {
            response.put("success",false);
            response.put("message",e.getMessage());
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }catch (Exception e){
            response.put("success",false);
            response.put("message",e.getMessage());
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }

    }


    @PreAuthorize("hasAnyAuthority('ADMIN','HR','MANAGER','EMPLOYEE')")
    @GetMapping("/get/{employeeId}")
    public String getAllPayrollByEmployeeId(@PathVariable Long employeeId,Model model){
            EmployeeDTO employee = employeeService.getEmployeeById(employeeId);
            EmployeeDTO user = authService.getLoggedInEmployee();
            List<PayrollDTO> payroll=payrollService.getPayrollByID(employeeId);
            model.addAttribute("employee",employee);
            model.addAttribute("payrolls",payroll);
            model.addAttribute("user",user);
            return "view-payroll";
    }

    //@PreAuthorize(("hasAnyAuthority('ADMIN','HR')"))
    @GetMapping("/getallpayroll")
    public List<PayrollDTO> getAllPayrollDetails(){
        return payrollService.getAllPayrollDetails();
    }

    //@PreAuthorize(("hasAnyAuthority('ADMIN','HR','EMPLOYEE')"))
    @DeleteMapping("/delete/{payrollId}")
    public void deletePayrollById(@PathVariable Long payrollId){
         payrollService.deletePayroll(payrollId);
    }
}
