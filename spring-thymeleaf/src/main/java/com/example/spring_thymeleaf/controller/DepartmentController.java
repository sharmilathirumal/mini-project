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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
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
@EnableWebSecurity
@EnableMethodSecurity
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private EmployeeService employeeService;

    @PreAuthorize("hasAnyAuthority('ADMIN','HR')")
    @GetMapping("/add")
    public String showAddDepartment(Model model){
        model.addAttribute("department",new DepartmentDTO());
        return "add-department";
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','HR')")
    @PostMapping("/add")
    public ResponseEntity<Map<String,Object>> AddDepartment(@ModelAttribute DepartmentDTO departmentDTO){
        Map<String,Object> response = new HashMap<>();
        try{
            departmentService.CreateDepartment(departmentDTO);
            response.put("success",true);
            response.put("message","Department Added Successfully!!");
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }catch (IllegalStateException e){
            response.put("success",false);
            response.put("message",e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }catch (Exception e){
            response.put("success",false);
            response.put("message",e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

    }

    @PreAuthorize("hasAnyAuthority('ADMIN','HR')")
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id,Model model){
        DepartmentDTO department = departmentService.GetDepartmentById(id);
        model.addAttribute("department",department);
        return "update-department";
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','HR')")
    @PostMapping("/update")
    public ResponseEntity<Map<String,Object>> UpdateDepartment(@ModelAttribute DepartmentDTO departmentDTO){
        Map<String,Object> response = new HashMap<>();
        try{
            departmentService.UpdateDepartmentDetails(departmentDTO);
            response.put("success",true);
            response.put("message","Department Updated Successfully!!");
            return ResponseEntity.status(HttpStatus.OK).body(response);

        }catch (IllegalStateException e){
            response.put("success",false);
            response.put("message",e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }catch (Exception e){
            response.put("success",false);
            response.put("message",e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

    }

    //@PreAuthorize("hasAnyAuthority('ADMIN','HR')")
    @GetMapping("/get/{departmentid}")
    public DepartmentDTO GetDepartmentById(@PathVariable Long departmentid){
        return departmentService.GetDepartmentById(departmentid);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','HR','MANAGER')")
    @GetMapping("/getalldepartments")
    public List<DepartmentDTO> GetAllDeparments(){
        return departmentService.GetAllDepartments();
    }



    @PreAuthorize("hasAnyAuthority('ADMIN','HR')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> DeleteDepartment(@PathVariable Long id){
        try{
            departmentService.DeleteDepartment(id);
            return ResponseEntity.ok(Map.of("message", "Department deleted successfully"));
        }
        catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    /*@PreAuthorize("hasAnyAuthority('ADMIN','HR')")
    @GetMapping("getemployees-bydepartmentid/{departmentid}")
    public List<EmployeeDTO> AssignDepartmentToEmployee(@PathVariable Long departmentid){
         return departmentService.getAllEmployeeById(departmentid);
    }*/
}
