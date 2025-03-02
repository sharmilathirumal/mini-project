package com.example.spring_thymeleaf.service.Impl;

import com.example.spring_thymeleaf.dto.EmployeeDTO;
import com.example.spring_thymeleaf.entity.*;
import com.example.spring_thymeleaf.repository.AttendanceRepository;
import com.example.spring_thymeleaf.repository.DepartmentRepository;
import com.example.spring_thymeleaf.repository.EmployeeRepository;
import com.example.spring_thymeleaf.service.EmployeeService;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private DepartmentRepository departmentRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private AttendanceRepository attendanceRepository;

    @Override
    public void saveEmployee(EmployeeDTO employeeDto) {
        Optional<Department> dep_id = departmentRepository.findById(employeeDto.getDepartmentId());
        if(dep_id.isPresent()){
            Employee employee = modelMapper.map(employeeDto,Employee.class);
            employee.setId(null); // Ensure a new employee is created (not updated!)
            //employee.getDepartment().setId(employeeDto.getDepartmentId());
            employee.setDepartment(dep_id.get());
            employeeRepository.save(employee);

        }
        else {
            throw new RuntimeException("Department not found,Please add Department first");
        }

    }

    @Override
    public List<EmployeeDTO> getAllEmployees() {
        List<Employee> employees = employeeRepository.findAll();
        List<EmployeeDTO> employeeDTOS = new ArrayList<>();
        for(Employee employee :employees){
           employeeDTOS.add(modelMapper.map(employee,EmployeeDTO.class));
        }
        return employeeDTOS;
    }

    @Override
    public EmployeeDTO getEmployeeById(Long id) {
            //return modelMapper.map(employeeRepository.findById(id),EmployeeDto.class);
        Optional<Employee> optionaleEmployee =employeeRepository.findById(id);
        Employee employee = null;
        if(optionaleEmployee.isPresent()){
            employee = optionaleEmployee.get();
        }else{
            throw new RuntimeException("Employee not found with this id" +" "+id);
        }

        return modelMapper.map(employee, EmployeeDTO.class);
    }

    @Override
    public EmployeeDTO updateEmployeeDetails(EmployeeDTO employeeDto) {
        Employee employee = employeeRepository.findById(employeeDto.getId()).orElseThrow(()->new EntityNotFoundException("Employee not found with this id "+" " +employeeDto.getId()+" so you can't upadate details"));
        if(!employee.getFirstName().equals(employeeDto.getFirstName())){
            employee.setFirstName(employeeDto.getFirstName());
        }
        if(!employee.getLastName().equals(employeeDto.getLastName())){
            employee.setLastName(employeeDto.getLastName());
        }
        if(!employee.getEmail().equals(employeeDto.getEmail())){
            employee.setEmail(employeeDto.getEmail());
        }

        if(employee.getPhoneNo() !=employeeDto.getPhoneNo()){
            employee.setPhoneNo(employeeDto.getPhoneNo());
        }

        if(!employee.getDateOfBirth().equals(employeeDto.getDateOfBirth())){
            employee.setDateOfBirth(employeeDto.getDateOfBirth());
        }

        if(!employee.getHiredDate().equals(employeeDto.getHiredDate())){
            employee.setHiredDate(employeeDto.getHiredDate());
        }

        if(!employee.getDesignation().equals(employeeDto.getDesignation())){
            employee.setDesignation(employeeDto.getDesignation());
        }

        if(employee.getSalary() != employeeDto.getSalary()){
            employee.setSalary(employeeDto.getSalary());
        }

        if(!employee.getStatus().equals(employeeDto.getStatus())){
            employee.setStatus(employeeDto.getStatus());
        }

        if(employee.getDepartment().getId() != employeeDto.getDepartmentId()){
            Optional<Department> dep_id =departmentRepository.findById(employeeDto.getDepartmentId());
            if(dep_id.isPresent()){
                employee.setDepartment(dep_id.get());
            }
            else{
                throw new RuntimeException("Department not found, Please check the department you want to update is already present in department table");
            }
        }
       // Department department = departmentRepository.findById(employeeDto.getDepartmentId()).orElseThrow(()->new RuntimeException("Department not found"));
        //employee.setDepartment(department);

       Employee updatedEmployee = employeeRepository.save(employee);
        return modelMapper.map(updatedEmployee, EmployeeDTO.class);
        /*
            Optional<Employee> optionalEmployee = employeeRepository.findById(id)
            if(optionalEmployee.isPresent()){
            Employee employee =optionalEmployee.get();
            employee.setFirst_name(employeeDto.getFirst_name());
            employee.setLast_name(employeeDto.getLast_name());
            employee.setEmail(employeeDto.getEmail());
            employeeRepository.save(employee);
            return modelMapper.map(employee,EmployeeDto.class) ;
        }else{
            throw new EntityNotFoundException("No Employee available with this id"+"  "+id);
        }*/
    }

    @Override
    public void deleteEmployeeById(Long id) {
        Employee employee = employeeRepository.findById(id).orElseThrow(()-> new EntityNotFoundException("Employee not found with this id"+" "+ id));
        employeeRepository.deleteById(id);
        /*Optional<Employee> optionalEmployee =employeeRepository.findById(id);
        if(optionalEmployee.isPresent()){
            employeeRepository.deleteById(id);
        }
        else{
            throw new RuntimeException("Employee not found with this id" +" "+id +" "+"So You Cant delete employee");
        }*/
    }

    /*@Override
    public List<EmployeeDTO> getAllAttendanceRecordsOfAnEmployee(Long id) {
            List<Employee> employees = attendanceRepository.findByEmployee_Id(id);
            List<EmployeeDTO> employeeDTOS = new ArrayList<>();
            for(Employee employee :employees){
                employeeDTOS.add(modelMapper.map(employee,EmployeeDTO.class));
            }
            return employeeDTOS;
    }*/

    /*@Override
   public List<EmployeeDTO> getAllEmployeeWithDepartment(){
        List<EmployeeDTO> employeeDTOS = employeeRepository.findAllEmployeeWithDepartment();
        /*List<EmployeeDTO> employeeDTOS = new ArrayList<>();// = employeeRepository.findAllEmployeeWithDepartment();
        for(Employee employee:employees){
            EmployeeDTO employeeDTO = new EmployeeDTO();
            employeeDTO.setFirstName(employee.);
        }
         return employeeDTOS;
    }*/
}
