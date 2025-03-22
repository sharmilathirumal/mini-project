package com.example.spring_thymeleaf.service;

import com.example.spring_thymeleaf.dto.EmployeeDTO;
import com.example.spring_thymeleaf.entity.Employee;

import java.util.List;

public interface EmployeeService {
    void saveEmployee(EmployeeDTO employeeDto);

    List<EmployeeDTO> getAllEmployees();

    EmployeeDTO getEmployeeById(Long id);

    EmployeeDTO updateEmployeeDetails(EmployeeDTO employeeDto);

    void deleteEmployeeById(Long id);

    List<EmployeeDTO> getEmployeesByTeamId(Long id);
    //List<EmployeeDTO> getAllEmployeeWithDepartment();

}
