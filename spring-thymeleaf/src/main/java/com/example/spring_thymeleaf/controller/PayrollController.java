package com.example.spring_thymeleaf.controller;

import com.example.spring_thymeleaf.dto.EmployeeDTO;
import com.example.spring_thymeleaf.dto.PayrollDTO;
import com.example.spring_thymeleaf.service.EmployeeService;
import com.example.spring_thymeleaf.service.Impl.EmployeeServiceImpl;
import com.example.spring_thymeleaf.service.Impl.PayrollServiceImpl;
import com.example.spring_thymeleaf.service.PayrollService;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("payroll")
@CrossOrigin("*")
public class PayrollController {
    @Autowired
    private PayrollService payrollService;

    @Autowired
    private EmployeeService employeeService;

    @GetMapping("/add/{employeeId}")
    public String ShowAddPayrollForm(@PathVariable Long employeeId,Model model){
        model.addAttribute("payroll",new PayrollDTO());
        model.addAttribute("employee",employeeService.getEmployeeById(employeeId));
        return "add-payroll";
    }
    //@PreAuthorize("hasAnyAuthority('ADMIN','HR')")
    @PostMapping("/add")
    public ResponseEntity<Map<String,Object>> Add(@ModelAttribute PayrollDTO payrollDTO){
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

    /*(//@PreAuthorize("hasAnyAuthority('ADMIN','HR')")
    @PutMapping("/update/{id}")
    public PayrollDTO Update(@PathVariable Long id,@RequestBody PayrollDTO payrollDTO){
        return payrollService.UpdatePayroll(id,payrollDTO);
    }*/

    //@PreAuthorize(("hasAnyAuthority('ADMIN','HR','EMPLOYEE')"))
    @GetMapping("/get/{employeeId}")
    public String GetAllPayrollByEmployeeId(@PathVariable Long employeeId,Model model){
        try{
            EmployeeDTO employee = employeeService.getEmployeeById(employeeId);
            List<PayrollDTO> payroll=payrollService.getPayrollByID(employeeId);
            model.addAttribute("employee",employee);
            model.addAttribute("payrolls",payroll);
            return "view-payroll";
        }
        catch (Exception e){
            model.addAttribute("error",e.getMessage());
            return "error-page";
        }

    }

    //@PreAuthorize(("hasAnyAuthority('ADMIN','HR')"))
    @GetMapping("/getallpayroll")
    public List<PayrollDTO> GetAllPayrollDetails(){
        return payrollService.getAllPayrollDetails();
    }

    //@PreAuthorize(("hasAnyAuthority('ADMIN','HR','EMPLOYEE')"))
    @DeleteMapping("/delete/{payrollId}")
    public void DeletePayrollById(@PathVariable Long payrollId){
         payrollService.deletePayroll(payrollId);
    }
}
