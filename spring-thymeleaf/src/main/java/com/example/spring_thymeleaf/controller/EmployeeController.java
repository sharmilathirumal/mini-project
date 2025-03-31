package com.example.spring_thymeleaf.controller;

import com.example.spring_thymeleaf.dto.AddressDTO;
import com.example.spring_thymeleaf.dto.DepartmentDTO;
import com.example.spring_thymeleaf.dto.EmployeeDTO;
import com.example.spring_thymeleaf.dto.TeamDTO;
import com.example.spring_thymeleaf.entity.Employee;
import com.example.spring_thymeleaf.service.*;
import com.example.spring_thymeleaf.service.Impl.DepartmentServiceImpl;
import com.example.spring_thymeleaf.service.Impl.TeamServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import com.example.spring_thymeleaf.service.Impl.EmployeeServiceImpl;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.beans.PropertyEditorSupport;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/employee")
@EnableWebSecurity
@EnableMethodSecurity
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private TeamService teamService;

    @Autowired
    private AuthService authService;

    @Autowired
    private AddressService addressService;
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(DepartmentDTO.class, new PropertyEditorSupport() {
            @Override
            public void setAsText(String id) {
                if (id == null || id.isEmpty()) {
                    setValue(null);
                } else {
                    DepartmentDTO department = new DepartmentDTO();
                    department.setId(Long.parseLong(id));
                    setValue(department);
                }
            }
        });

        binder.registerCustomEditor(TeamDTO.class, new PropertyEditorSupport() {
            @Override
            public void setAsText(String id) {
                if (id == null || id.isEmpty()) {
                    setValue(null);
                } else {
                    TeamDTO team = new TeamDTO();
                    team.setId(Long.parseLong(id));
                    setValue(team);
                }
            }
        });
    }

    //@PreAuthorize("hasAnyAuthority('HR','ADMIN','MANAGER')")
    @GetMapping("/view")
    //public String viewHomePage(Model model){
        public List<EmployeeDTO> viewHomePage(){
        return employeeService.getAllEmployees();
       // return "index";

    }
    @PreAuthorize("hasAnyAuthority('ADMIN','HR')")
    @GetMapping("/add")
    public String showAddEmployeeForm(Model model){
       model.addAttribute("employee",new EmployeeDTO());
       model.addAttribute("departments",departmentService.GetAllDepartments());
       model.addAttribute("teams",teamService.getAllTeam());
        return "add-employee";
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','HR')")
    @PostMapping("/add")
    public ResponseEntity<Map<String, Object>> addEmployee(@ModelAttribute EmployeeDTO employeeDto) {
        Map<String, Object> response = new HashMap<>();
        try {
            employeeService.saveEmployee(employeeDto);
            response.put("success", true);
            response.put("message", "Employee added successfully!");
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (IllegalStateException e) {
            response.put("success", false);
            response.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }catch (Exception e) {
            response.put("success", false);
            response.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }


    @PreAuthorize("hasAnyAuthority('ADMIN','HR')")
    @GetMapping("/edit/{id}")
    public String showUpdateForm(@PathVariable(value = "id") Long id,Model model){
        EmployeeDTO employee = employeeService.getEmployeeById(id);
        AddressDTO address = addressService.getAddressByEmployeeId(employee.getId());
        employee.setAddress(address);
        model.addAttribute("employee",employee);
        model.addAttribute("departments",departmentService.GetAllDepartments());
        model.addAttribute("teams",teamService.getAllTeam());
        model.addAttribute("address",address);
        return "update-employee";
        //return "redirect:/";
        //"details updated successfully";
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','HR')")
    @PostMapping("/update")
    @ResponseBody  // This makes it return JSON instead of redirecting
    public ResponseEntity<Map<String, Object>> updateEmployeeDetail(@ModelAttribute EmployeeDTO employeeDto) {
        Map<String, Object> response = new HashMap<>();
        try {
            employeeService.updateEmployeeDetails(employeeDto);
            response.put("success", true);
            response.put("message", "Employee updated successfully!!");
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }catch (IllegalStateException e){
            response.put("success", false);
            response.put("message",e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }catch (Exception e) {
            response.put("success", false);
            response.put("message",e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }


    @PreAuthorize("hasAnyAuthority('HR','ADMIN','MANAGER','EMPLOYEE')")
    @GetMapping("/get/{id}")
        public String getEmployeeByid(@PathVariable(value = "id") Long id,Model model){
        EmployeeDTO employee = employeeService.getEmployeeById(id);
        EmployeeDTO user = authService.getLoggedInEmployee();
        model.addAttribute("employee",employee);
        model.addAttribute("user",user);
        return "view-employee";
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','HR')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteEmployeeById(@PathVariable(value = "id")Long id, RedirectAttributes redirectAttributes){
        try{
            employeeService.deleteEmployeeById(id);
            return ResponseEntity.ok(Map.of("message", "Employee deleted successfully"));
        }catch (IllegalStateException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','HR')")
    @GetMapping("/team/{teamId}")
    public String getEmployeeById(@PathVariable Long teamId,Model model){
        List<EmployeeDTO> employees = employeeService.getEmployeesByTeamId(teamId);
        model.addAttribute("employees",employees);
        return "team-employees";
    }
}
