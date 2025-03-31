package com.example.spring_thymeleaf.controller;

import com.example.spring_thymeleaf.dto.EmployeeDTO;
import com.example.spring_thymeleaf.dto.UserDTO;
import com.example.spring_thymeleaf.service.EmployeeService;
import com.example.spring_thymeleaf.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@CrossOrigin("*")
@RequestMapping("user")
@EnableWebSecurity
@EnableMethodSecurity
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private EmployeeService employeeService;

    @PreAuthorize("hasAnyAuthority('ADMIN','HR')")
    @GetMapping("/add")
    public String showAddForm(Model model){
        UserDTO userDTO = new UserDTO();
        model.addAttribute("user",userDTO);
        model.addAttribute("employees",employeeService.getAllEmployees());
        return "add-user";
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','HR')")
    @PostMapping("/add")
    public ResponseEntity<Map<String,Object>> addUser(UserDTO userDTO){
        Map<String,Object> response = new HashMap<>();
        try{
            userService.createUser(userDTO);
            response.put("success",true);
            response.put("message","User Added Successfully");
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }catch (IllegalStateException e){
            response.put("success",false);
            response.put("message",e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }catch (Exception e){
            response.put("success",true);
            response.put("message",e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

    }

    @PreAuthorize("hasAnyAuthority('ADMIN','HR')")
    @GetMapping("edit/{id}")
    public String showUpdatePasswordForm(@PathVariable Long id,Model model){
        UserDTO userDTO = userService.getUserById(id);
        model.addAttribute("user",userDTO);
        return "update-password";
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','HR')")
    @PostMapping("update")
    public ResponseEntity<Map<String,Object>> updatePassword(UserDTO userDTO){
        Map<String,Object> response = new HashMap<>();
        try {
            userService.updatePassword(userDTO);
            response.put("success",true);
            response.put("message","Password Updated Successfully");
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
    @GetMapping("/get")
    public List<UserDTO> getAllUser(){
        return userService.getAllUsers();
    }
}
