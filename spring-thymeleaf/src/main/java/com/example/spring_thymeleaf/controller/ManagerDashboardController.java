package com.example.spring_thymeleaf.controller;

import com.example.spring_thymeleaf.dto.DepartmentDTO;
import com.example.spring_thymeleaf.dto.EmployeeDTO;
import com.example.spring_thymeleaf.dto.TeamDTO;
import com.example.spring_thymeleaf.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/manager")
public class ManagerDashboardController {
    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private AttendanceService attendanceService;

    @Autowired
    private PerformanceService performanceService;

    @Autowired
    private LeaveService leaveServicel;

    @GetMapping("/dashboard")
    public String adminDashBoard(Model model){
        List<EmployeeDTO> employees = employeeService.getAllEmployees();
        model.addAttribute("employees",employees);
        return "managerdashboard";
    }
}
