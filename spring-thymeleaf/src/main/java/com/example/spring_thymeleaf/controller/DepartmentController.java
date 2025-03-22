package com.example.spring_thymeleaf.controller;

import com.example.spring_thymeleaf.dto.DepartmentDTO;
import com.example.spring_thymeleaf.dto.EmployeeDTO;
import com.example.spring_thymeleaf.entity.Employee;
import com.example.spring_thymeleaf.service.DepartmentService;
import com.example.spring_thymeleaf.service.EmployeeService;
import com.example.spring_thymeleaf.service.Impl.DepartmentServiceImpl;
import com.example.spring_thymeleaf.service.Impl.EmployeeServiceImpl;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/department")
@CrossOrigin("*")
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private EmployeeService employeeService;

    @GetMapping("/add")
    public String showAddDepartment(Model model){
        model.addAttribute("department",new DepartmentDTO());
        return "add-department";
    }
    //@PreAuthorize("hasAnyAuthority('ADMIN','HR')")
    @PostMapping("/add")
    public String AddDepartment(@ModelAttribute DepartmentDTO departmentDTO){
          departmentService.CreateDepartment(departmentDTO);
          return "redirect:/hradmin/dashboard";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id,Model model){
        DepartmentDTO department = departmentService.GetDepartmentById(id);
        model.addAttribute("department",department);
        return "update-department";
    }
    //@PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/update")
    public String UpdateDepartment(@ModelAttribute DepartmentDTO departmentDTO){
         departmentService.UpdateDepartmentDetails(departmentDTO);
        return "redirect:/hradmin/dashboard";
    }

    //@PreAuthorize("hasAnyAuthority('ADMIN','HR')")
    @GetMapping("/get/{departmentid}")
    public DepartmentDTO GetDepartmentById(@PathVariable Long departmentid){
        return departmentService.GetDepartmentById(departmentid);
    }

   // @PreAuthorize("hasAnyAuthority('ADMIN','HR','MANAGER','EMPLOYEE')")
    @GetMapping("/getalldepartments")
    public List<DepartmentDTO> GetAllDeparments(){
        return departmentService.GetAllDepartments();
    }



    //@PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> DeleteDepartment(@PathVariable Long id){
        try{
            departmentService.DeleteDepartment(id);
            return ResponseEntity.ok(Map.of("message", "Department deleted successfully"));
        }
        catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    //@PreAuthorize("hasAnyAuthority('ADMIN','HR')")
    @GetMapping("getemployees-bydepartmentid/{departmentid}")
    public List<EmployeeDTO> AssignDepartmentToEmployee(@PathVariable Long departmentid){
         return departmentService.getAllEmployeeById(departmentid);
    }
}
