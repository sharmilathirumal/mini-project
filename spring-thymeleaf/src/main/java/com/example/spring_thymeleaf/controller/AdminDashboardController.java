package com.example.spring_thymeleaf.controller;

import com.example.spring_thymeleaf.dto.*;
import com.example.spring_thymeleaf.service.Impl.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminDashboardController {
    @Autowired
    private EmployeeServiceImpl employeeService;

    @Autowired
    private DepartmentServiceImpl departmentService;

    @Autowired
    private AttendanceServiceImpl attendanceService;

    @Autowired
    private PayrollServiceImpl payrollService;

    @Autowired
    private PerformanceServiceImpl performanceService;

    @Autowired
    private LeaveServiceImpl leaveServicel;

    @GetMapping("/dashboard")
    public String adminDashBoard(Model model){
        List<EmployeeDTO> employees = employeeService.getAllEmployees();
        //List<EmployeeDTO> employees = employeeService.getAllEmployeeWithDepartment();
        List<DepartmentDTO> departments =departmentService.GetAllDepartments();
        List<AttendanceDTO> attendances =attendanceService.getAllAttendanceRecords();
        List<LeaveDTO> leaves =leaveServicel.GetAllLeaves();
        List<PayrollDTO> payrolls = payrollService.GetAllPayrollDetails();
        List<PerformanceDTO> performances =performanceService.GetAllPerformanceDetails();
        model.addAttribute("employees",employees);
        model.addAttribute("departments",departments);
        model.addAttribute("attendances",attendances);
        model.addAttribute("leaves",leaves);
        model.addAttribute("payrolls",payrolls);
        model.addAttribute("performances",performances);
        return "admindashboard";
    }

}
