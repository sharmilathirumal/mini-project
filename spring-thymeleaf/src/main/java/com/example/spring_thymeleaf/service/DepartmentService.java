package com.example.spring_thymeleaf.service;

import com.example.spring_thymeleaf.dto.DepartmentDTO;
import com.example.spring_thymeleaf.dto.EmployeeDTO;
import com.example.spring_thymeleaf.entity.Department;
import com.example.spring_thymeleaf.entity.Employee;

import java.util.List;

public interface DepartmentService {

    DepartmentDTO CreateDepartment(DepartmentDTO departmentDTO);
    List<DepartmentDTO> GetAllDepartments();
    DepartmentDTO GetDepartmentById(Long id);
    DepartmentDTO UpdateDepartmentDetails(DepartmentDTO departmentDTO);
    void DeleteDepartment(Long id);
    //List<EmployeeDTO> getAllEmployeeById(Long id);
}
