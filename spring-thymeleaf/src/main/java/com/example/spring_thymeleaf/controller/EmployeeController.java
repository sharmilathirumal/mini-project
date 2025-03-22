package com.example.spring_thymeleaf.controller;

import com.example.spring_thymeleaf.dto.DepartmentDTO;
import com.example.spring_thymeleaf.dto.EmployeeDTO;
import com.example.spring_thymeleaf.dto.TeamDTO;
import com.example.spring_thymeleaf.entity.Employee;
import com.example.spring_thymeleaf.service.DepartmentService;
import com.example.spring_thymeleaf.service.EmployeeService;
import com.example.spring_thymeleaf.service.Impl.DepartmentServiceImpl;
import com.example.spring_thymeleaf.service.Impl.TeamServiceImpl;
import com.example.spring_thymeleaf.service.TeamService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import com.example.spring_thymeleaf.service.Impl.EmployeeServiceImpl;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.beans.PropertyEditorSupport;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/employee")
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private TeamService teamService;

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

    @GetMapping("/add")
    public String showAddEmployeeForm(Model model){
       model.addAttribute("employee",new EmployeeDTO());
       model.addAttribute("departments",departmentService.GetAllDepartments());
       model.addAttribute("teams",teamService.getAllTeam());
        return "add-employee";
    }

    @PostMapping("/add")
    @ResponseBody // Ensures JSON response
    public ResponseEntity<Map<String, Object>> addEmployee(@Valid @ModelAttribute EmployeeDTO employeeDto,
                                                           BindingResult result) {
        Map<String, Object> response = new HashMap<>();

        if (result.hasErrors()) {
            response.put("success", false);
            response.put("message", "Invalid employee data. Please check your inputs.");
            return ResponseEntity.badRequest().body(response);
        }

        try {
            employeeService.saveEmployee(employeeDto);
            response.put("success", true);
            response.put("message", "Employee added successfully!");
            return ResponseEntity.ok(response); // Return JSON response
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "An unexpected error occurred: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    /*@PostMapping("/add")
    public String addEmployee(@ModelAttribute EmployeeDTO employeeDto, RedirectAttributes redirectAttributes) {
        employeeService.saveEmployee(employeeDto);
        redirectAttributes.addFlashAttribute("successMessage", "Employee added successfully!");
        return "redirect:/hradmin/dashboard";
    }*/

    //@PreAuthorize("hasAuthority('HR')")
    /*@PostMapping("/add")
    public String addEmployee(@ModelAttribute EmployeeDTO employeeDto){
        employeeService.saveEmployee(employeeDto);
        //return "redirect:/";
        return "redirect:/hradmin/dashboard";
    }*/

    @GetMapping("/edit/{id}")
    // public EmployeeDTO updateEmployeeDetail(@PathVariable(value = "id") Long id,@ModelAttribute("updatedEmployee") EmployeeDTO employeeDto){
    public String showUpdateForm(@PathVariable(value = "id") Long id,Model model){
        EmployeeDTO employee = employeeService.getEmployeeById(id);
        model.addAttribute("employee",employee);
        model.addAttribute("departments",departmentService.GetAllDepartments());
        model.addAttribute("teams",teamService.getAllTeam());
        return "update-employee";
        //return "redirect:/";
        //"details updated successfully";
    }

    @PostMapping("/update")
    @ResponseBody  // This makes it return JSON instead of redirecting
    public ResponseEntity<Map<String, Object>> updateEmployeeDetail(@ModelAttribute EmployeeDTO employeeDto) {
        Map<String, Object> response = new HashMap<>();
        try {
            employeeService.updateEmployeeDetails(employeeDto);
            response.put("success", true);
            response.put("message", "Employee updated successfully!");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error updating employee: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }


    //@PreAuthorize("hasAuthority('HR')")
    /*@PostMapping("/update")
   // public EmployeeDTO updateEmployeeDetail(@PathVariable(value = "id") Long id,@ModelAttribute("updatedEmployee") EmployeeDTO employeeDto){
        public String updateEmployeeDetail(@ModelAttribute EmployeeDTO employeeDto){
          employeeService.updateEmployeeDetails(employeeDto);
        return "redirect:/hradmin/dashboard";
         //"details updated successfully";
    }*/

    //@PreAuthorize("hasAnyAuthority('HR','ADMIN','MANAGER')")
    @GetMapping("/get/{id}")
    //public String getEmployeeByid(@PathVariable(value = "id") Long id,Model model){
        public String getEmployeeByid(@PathVariable(value = "id") Long id,Model model){
        EmployeeDTO employee = employeeService.getEmployeeById(id);
        model.addAttribute("employee",employee);
        //return "update";
        //return "get employee by id";
        return "view-employee";
    }

   /* @GetMapping("getwithattandance/{employeeid}")
    public List<EmployeeDTO> getAllEmployeeByAttandce(@PathVariable Long employeeid){
        return employeeService.getAllAttendanceRecordsOfAnEmployee(employeeid);
    }*/

    //@PreAuthorize("hasAuthority('HR')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteEmployeeById(@PathVariable(value = "id")Long id, RedirectAttributes redirectAttributes){
            employeeService.deleteEmployeeById(id);
            return ResponseEntity.ok(Map.of("message", "Employee deleted successfully"));
    }

    @GetMapping("/team/{teamId}")
    public String getEmployeeById(@PathVariable Long teamId,Model model){
        List<EmployeeDTO> employees = employeeService.getEmployeesByTeamId(teamId);
        model.addAttribute("employees",employees);
        return "team-employees";
    }
}
