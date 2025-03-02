package com.example.spring_thymeleaf.controller;

import com.example.spring_thymeleaf.dto.PayrollDTO;
import com.example.spring_thymeleaf.service.Impl.PayrollServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("payroll")
@CrossOrigin("*")
public class PayrollController {
    @Autowired
    private PayrollServiceImpl payrollService;

    @GetMapping("/add")
    public String ShowAddPayrollForm(Model model){
        model.addAttribute("payroll",new PayrollDTO());
        return "add-payroll";
    }
    //@PreAuthorize("hasAnyAuthority('ADMIN','HR')")
    @PostMapping("/add")
    public String Add(@ModelAttribute PayrollDTO payrollDTO){
         payrollService.AddPayroll(payrollDTO);
         return "redirect:/admin/dashboard";
    }

    /*(//@PreAuthorize("hasAnyAuthority('ADMIN','HR')")
    @PutMapping("/update/{id}")
    public PayrollDTO Update(@PathVariable Long id,@RequestBody PayrollDTO payrollDTO){
        return payrollService.UpdatePayroll(id,payrollDTO);
    }*/

    //@PreAuthorize(("hasAnyAuthority('ADMIN','HR','EMPLOYEE')"))
    @GetMapping("/get/{employeeId}")
    public String GetAllPayrollByEmployeeId(@PathVariable Long employeeId,Model model){
         List<PayrollDTO> payroll=payrollService.GetPayrollByID(employeeId);
        model.addAttribute("payroll",payroll);
        return "view-payroll";
    }

    //@PreAuthorize(("hasAnyAuthority('ADMIN','HR')"))
    @GetMapping("/getallpayroll")
    public List<PayrollDTO> GetAllPayrollDetails(){
        return payrollService.GetAllPayrollDetails();
    }

    //@PreAuthorize(("hasAnyAuthority('ADMIN','HR','EMPLOYEE')"))
    @DeleteMapping("/delete/{payrollId}")
    public void DeletePayrollById(@PathVariable Long payrollId){
         payrollService.DeletePayroll(payrollId);
    }
}
