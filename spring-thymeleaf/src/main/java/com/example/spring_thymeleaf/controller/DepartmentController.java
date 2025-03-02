package com.example.spring_thymeleaf.controller;

import com.example.spring_thymeleaf.dto.DepartmentDTO;
import com.example.spring_thymeleaf.dto.EmployeeDTO;
import com.example.spring_thymeleaf.service.DepartmentService;
import com.example.spring_thymeleaf.service.Impl.DepartmentServiceImpl;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/department")
@CrossOrigin("*")
public class DepartmentController {

    @Autowired
    private DepartmentServiceImpl departmentService;

    @GetMapping("/add")
    public String showAddDepartment(Model model){
        model.addAttribute("department",new DepartmentDTO());
        return "add-department";
    }
    //@PreAuthorize("hasAnyAuthority('ADMIN','HR')")
    @PostMapping("/add")
    public String AddDepartment(@ModelAttribute DepartmentDTO departmentDTO){
          departmentService.CreateDepartment(departmentDTO);
          return "redirect:/admin/dashboard";
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
        return "redirect:/admin/dashboard";
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
    @GetMapping("/delete/{id}")
    public String DeleteDepartment(@PathVariable Long id, RedirectAttributes redirectAttributes){
        departmentService.DeleteDepartment(id);
        redirectAttributes.addFlashAttribute("successMessage","Department Deleted Successfully");
        return "redirect:/admin/dashboard";
    }

    //@PreAuthorize("hasAnyAuthority('ADMIN','HR')")
    @GetMapping("getemployees-bydepartmentid/{departmentid}")
    public List<EmployeeDTO> AssignDepartmentToEmployee(@PathVariable Long departmentid){
         return departmentService.getAllEmployeeById(departmentid);
    }
}
