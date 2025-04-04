package com.example.spring_thymeleaf.controller;

import com.example.spring_thymeleaf.dto.*;
import com.example.spring_thymeleaf.service.DepartmentService;
import com.example.spring_thymeleaf.service.EmployeeService;
import com.example.spring_thymeleaf.service.Impl.EmployeeServiceImpl;
import com.example.spring_thymeleaf.service.Impl.TeamServiceImpl;
import com.example.spring_thymeleaf.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.beans.PropertyEditorSupport;
import java.text.ParseException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("team")
@CrossOrigin("*")
@EnableWebSecurity
@EnableMethodSecurity
public class TeamController {
    @Autowired
    private TeamService teamService;
    @Autowired
    private DepartmentService departmentService;
    @Autowired
    private EmployeeService employeeService;

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

        binder.registerCustomEditor(EmployeeDTO.class, new PropertyEditorSupport() {
            @Override
            public void setAsText(String id) {
                if (id == null || id.isEmpty()) {
                    setValue(null);
                } else {
                    EmployeeDTO manager = new EmployeeDTO();
                    manager.setId(Long.parseLong(id));
                    setValue(manager);
                }
            }
        });
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','HR','MANAGER')")
    @GetMapping("/add")
    public String showAddEmployeeForm(Model model){
        model.addAttribute("team",new TeamDTO());
        model.addAttribute("employees",employeeService.getAllEmployees());
        model.addAttribute("departments",departmentService.GetAllDepartments());
        return "add-team";
    }
    @PreAuthorize("hasAnyAuthority('ADMIN','HR','MANAGER')")
    @PostMapping("/add")
    public ResponseEntity<Map<String,Object>> addTeam(@ModelAttribute TeamDTO teamDTO) throws ParseException {
        Map<String,Object> response = new HashMap<>();
        try{
            teamService.addTeam(teamDTO);
            response.put("success",true);
            response.put("message","Team Added Successfully");
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
    @PreAuthorize("hasAnyAuthority('ADMIN','HR','MANAGER')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteTeam(@PathVariable Long id) {
        try {
            teamService.deleteTeam(id);
            return ResponseEntity.ok(Map.of("message", "Team deleted successfully"));
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','HR','MANAGER')")
    @GetMapping("/get")
    public List<TeamDTO> getAllTeam(){
        return teamService.getAllTeam();
    }

    @GetMapping("/get/{id}")
    public TeamDTO getTeamById(@PathVariable Long id){
        return teamService.getTeamById(id);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','HR','MANAGER')")
    @GetMapping("/edit/{id}")
    public String showUpdateForm(@PathVariable Long id,Model model){
        TeamDTO teamDTO = teamService.getTeamById(id);
        model.addAttribute("team", teamDTO);
        model.addAttribute("employees",employeeService.getAllEmployees());
        model.addAttribute("departments",departmentService.GetAllDepartments());
        return "update-team";
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','HR','MANAGER')")
    @PostMapping("/update")
    public ResponseEntity<Map<String,Object>> updateTeam(TeamDTO teamDTO){
        Map<String,Object> response = new HashMap<>();
        try{
            teamService.updateTeam(teamDTO);
            response.put("success",true);
            response.put("message","Team Updated Successfully");
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

}
