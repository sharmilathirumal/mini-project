package com.example.spring_thymeleaf.controller;

import com.example.spring_thymeleaf.dto.*;
import com.example.spring_thymeleaf.entity.User;
import com.example.spring_thymeleaf.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/manager")
@EnableWebSecurity
@EnableMethodSecurity
public class ManagerDashboardController {
    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private AttendanceService attendanceService;

    @Autowired
    private PerformanceService performanceService;

    @Autowired
    private UserService userService;

    @Autowired
    private TeamService teamService;

    @Autowired
    private LeaveService leaveService;

    @Autowired
    private AuthService authService;

    @GetMapping("/dashboard")
    public String adminDashBoard(Model model){
        EmployeeDTO manager = employeeService.getEmployeeById(authService.getLoggedInEmployee().getId());
        List<Long> teamIds = teamService.getTeamByManager(manager.getId());
        List<EmployeeDTO> employees = employeeService.getAllEmployeesUnderManager(manager.getId(), teamIds);
        List<Long> employeeIds = employeeService.getEmployeeIdsByTeamIds(teamIds);
        employees.removeIf(employee -> employee.getId().equals(manager.getId()));
        List<LeaveDTO> leaves = leaveService.getAllPendingLeaveOfEmployees(employeeIds);
        model.addAttribute("employees",employees);
        model.addAttribute("manager",manager);
        model.addAttribute("leaves",leaves);
        return "managerdashboard";
    }
}
