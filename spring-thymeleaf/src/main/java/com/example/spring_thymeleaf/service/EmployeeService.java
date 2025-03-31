package com.example.spring_thymeleaf.service;

import com.example.spring_thymeleaf.dto.EmployeeDTO;
import com.example.spring_thymeleaf.entity.Employee;

import java.util.List;

public interface EmployeeService {
    void saveEmployee(EmployeeDTO employeeDto);

    List<EmployeeDTO> getAllEmployees();

    EmployeeDTO getEmployeeById(Long employeeId);

    EmployeeDTO updateEmployeeDetails(EmployeeDTO employeeDto);

    void deleteEmployeeById(Long employeeId);

    List<EmployeeDTO> getEmployeesByTeamId(Long teamId);

    List<EmployeeDTO> getAllEmployeesUnderManager(Long id,List<Long> teamIds);

    List<Long> getEmployeeIdsByTeamIds(List<Long> teamIds);
}
