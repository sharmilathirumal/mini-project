package com.example.spring_thymeleaf.service.Impl;

import com.example.spring_thymeleaf.dto.DepartmentDTO;
import com.example.spring_thymeleaf.dto.EmployeeDTO;
import com.example.spring_thymeleaf.entity.Department;
import com.example.spring_thymeleaf.entity.Employee;
import com.example.spring_thymeleaf.repository.DepartmentRepository;
import com.example.spring_thymeleaf.repository.EmployeeRepository;
import com.example.spring_thymeleaf.service.DepartmentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class DepartmentServiceImpl implements DepartmentService {

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public DepartmentDTO CreateDepartment(DepartmentDTO departmentDTO) {
        return modelMapper.map(departmentRepository.save(modelMapper.map(departmentDTO,Department.class)),DepartmentDTO.class);
    }

    @Override
    public List<DepartmentDTO> GetAllDepartments() {
        List<Department> departments = departmentRepository.findAll();
        List<DepartmentDTO> departmentDTOS = new ArrayList<>();
        for(Department department:departments){
            departmentDTOS.add(modelMapper.map(department,DepartmentDTO.class));
        }
        return departmentDTOS;
    }

    @Override
    public DepartmentDTO GetDepartmentById(Long id) {
        Department department = departmentRepository.findById(id).orElseThrow(()->new RuntimeException("Department Not found"));
        return modelMapper.map(department,DepartmentDTO.class);
    }

    @Override
    public DepartmentDTO UpdateDepartmentDetails(DepartmentDTO departmentDTO) {
        Department department = departmentRepository.findById(departmentDTO.getId()).orElseThrow(()->new RuntimeException("Department id not found"));

       // List<Employee> employee = employeeRepository.findByDepartment_Id(id);
       // if(employee.size() == 0){
            department.setDepartmentName(departmentDTO.getDepartmentName());
            return modelMapper.map(departmentRepository.save(department),DepartmentDTO.class);
        //}
       //else throw new RuntimeException("");
    }

    @Override
    public void DeleteDepartment(Long id) {
        Department department = departmentRepository.findById(id).orElseThrow(()->new RuntimeException("Department not found"));
        List<Employee> employee = employeeRepository.findByDepartment_Id(id);
         if(!employee.isEmpty()) {
             throw new IllegalStateException("Cannot delete department. Employees are still assigned to this department.");
         }
        departmentRepository.deleteById(id);
       /* if(!department.getEmployees().isEmpty()){
            throw new RuntimeException("Cannot delete department with assigned employees");
        }*/

    }

   /* @Override
    public void AssignEmployeeTODepartment(Long id, List<Long> employeeids) {
        Department department = departmentRepository.findById(id).orElseThrow(()->new RuntimeException("Department not found"));
        List<Employee> employees =  employeeRepository.findAllById(employeeids);
        for(Employee employee :employees){
            employee.setDepartment(department);
        }
        employeeRepository.saveAll(employees);
         departmentRepository.save(department);
    }*/

    public List<EmployeeDTO> getAllEmployeeById(Long id){
        if(!departmentRepository.findById(id).isPresent()){
            throw new RuntimeException("Department not found");
        }
        List<Employee> employees= employeeRepository.findByDepartment_Id(id);

        if(employees.size() ==0) {
            throw new RuntimeException("No Employees Working to this Department");
        }
        List<EmployeeDTO> employeeDTOS =new ArrayList<>();
        for(Employee employee :employees){
            employeeDTOS.add(modelMapper.map(employee,EmployeeDTO.class));
        }

        return employeeDTOS;
    }
}
