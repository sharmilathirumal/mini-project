package com.example.spring_thymeleaf.controller;

import com.example.spring_thymeleaf.dto.*;
import com.example.spring_thymeleaf.service.*;
import com.example.spring_thymeleaf.service.Impl.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Controller
@RequestMapping("/hradmin")
public class HrAdminDashboardController {
    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private AttendanceService attendanceService;

    @Autowired
    private PayrollService payrollService;

    @Autowired
    private PerformanceService performanceService;

    @Autowired
    private LeaveService leaveServicel;

    @Autowired
    private TeamService teamService;

    @Autowired
    private UserService userService;

    @Autowired
    private AuthService authService;

    @GetMapping("/dashboard")
    public String adminDashBoard(Model model){
        EmployeeDTO employee =employeeService.getEmployeeById(authService.getLoggedInEmployee().getId());
        List<EmployeeDTO> employees = employeeService.getAllEmployees();
        employees.removeIf(loggedemployee -> loggedemployee.getId().equals(employee.getId()));
        List<DepartmentDTO> departments =departmentService.GetAllDepartments();
        List<TeamDTO> teams =teamService.getAllTeam();
        List<UserDTO> users = userService.getAllUsers();
        model.addAttribute("employees",employees);
        model.addAttribute("departments",departments);
        model.addAttribute("teams",teams);
        model.addAttribute("users",users);
        model.addAttribute("employee",employee);
        return "hrandadmindashboard";
    }

}
