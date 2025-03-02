package com.example.spring_thymeleaf.controller;

import com.example.spring_thymeleaf.dto.EmployeeDTO;
import com.example.spring_thymeleaf.entity.Employee;
import com.example.spring_thymeleaf.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.example.spring_thymeleaf.service.Impl.EmployeeServiceImpl;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/employees")
public class EmployeeController {
    @Autowired
    private EmployeeServiceImpl employeeService;

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
        return "add-employee";
    }
    //@PreAuthorize("hasAuthority('HR')")
    @PostMapping("/add")
    public String addEmployee(@ModelAttribute EmployeeDTO employeeDto){
        employeeService.saveEmployee(employeeDto);
        //return "redirect:/";
        return "redirect:/admin/dashboard";
    }

    @GetMapping("/edit/{id}")
    // public EmployeeDTO updateEmployeeDetail(@PathVariable(value = "id") Long id,@ModelAttribute("updatedEmployee") EmployeeDTO employeeDto){
    public String showUpdateForm(@PathVariable(value = "id") Long id,Model model){
        EmployeeDTO employee = employeeService.getEmployeeById(id);
        model.addAttribute("employee",employee);
        return "update-employee";
        //return "redirect:/";
        //"details updated successfully";
    }

    //@PreAuthorize("hasAuthority('HR')")
    @PostMapping("/update")
   // public EmployeeDTO updateEmployeeDetail(@PathVariable(value = "id") Long id,@ModelAttribute("updatedEmployee") EmployeeDTO employeeDto){
        public String updateEmployeeDetail(@ModelAttribute EmployeeDTO employeeDto){
          employeeService.updateEmployeeDetails(employeeDto);
        return "redirect:/admin/dashboard";
         //"details updated successfully";
    }

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
    @GetMapping("/delete/{id}")
    public String deleteEmployeeById(@PathVariable(value = "id")Long id, RedirectAttributes redirectAttributes){
        employeeService.deleteEmployeeById(id);
        redirectAttributes.addFlashAttribute("successMessage","Employee Deleted Successfully");
        //return "redirect:/";
        return "redirect:/admin/dashboard";
    }
}
