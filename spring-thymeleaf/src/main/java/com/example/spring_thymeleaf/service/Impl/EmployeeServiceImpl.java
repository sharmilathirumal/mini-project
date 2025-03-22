package com.example.spring_thymeleaf.service.Impl;

import com.example.spring_thymeleaf.dto.DepartmentDTO;
import com.example.spring_thymeleaf.dto.EmployeeDTO;
import com.example.spring_thymeleaf.dto.TeamDTO;
import com.example.spring_thymeleaf.entity.*;
import com.example.spring_thymeleaf.enums.MaritalStatus;
import com.example.spring_thymeleaf.enums.Title;
import com.example.spring_thymeleaf.repository.*;
import com.example.spring_thymeleaf.service.EmployeeService;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.Objects;
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
    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private TeamRepository teamRepository;

    @Override
    public void saveEmployee(EmployeeDTO employeeDto) {
            Department department = departmentRepository.findById(employeeDto.getDepartment().getId()).orElseThrow(()->new RuntimeException("Department Not Found"));
            Team team = teamRepository.findById(employeeDto.getTeam().getId()).orElseThrow(()->new RuntimeException("Team Not Found"));
            Employee employee = modelMapper.map(employeeDto,Employee.class);
            employee.setId(null);
            employee.setDepartment(department);
            employee.setTeam(team);
            if(employeeDto.getAddress() !=null){
                Address address = modelMapper.map(employeeDto.getAddress(),Address.class);
                address.setId(null);
                addressRepository.save(address);
                employee.setAddress(address);
            }
            employeeRepository.save(employee);

    }

    @Override
    public List<EmployeeDTO> getAllEmployees() {
        List<Employee> employees = employeeRepository.findAll();

        return employees.stream().map(employee -> {
            EmployeeDTO employeeDTO = modelMapper.map(employee, EmployeeDTO.class);
                //employeeDTO.setDepartment(modelMapper.map(employee.getDepartment(), DepartmentDTO.class));
               // employeeDTO.setTeam(modelMapper.map(employee.getTeam(), TeamDTO.class));
            Period period = Period.between(employee.getHiredDate() , LocalDate.now());
            employeeDTO.setEmploymentPeriod(period.getYears() + " years, " + period.getMonths() + " months");
            return employeeDTO;
        }).collect(Collectors.toList());
    }


    @Override
    public EmployeeDTO getEmployeeById(Long id) {
            //return modelMapper.map(employeeRepository.findById(id),EmployeeDto.class);
        Employee employee = employeeRepository.findById(id).orElseThrow(()->new RuntimeException("Employee Not Found"));
        EmployeeDTO employeeDTO = modelMapper.map(employee, EmployeeDTO.class);
        if(employee.getGender() !=null){
            switch (employee.getGender()){
                case MALE -> employeeDTO.setTitle(Title.Mr);
                case FEMALE -> employeeDTO.setTitle(
                        employee.getMaritalStatus() ==MaritalStatus.MARRIED ? Title.Mrs : Title.Miss
                );
                default -> employeeDTO.setTitle(Title.Mx);
            }
        }
        //employeeDTO.setDepartment(modelMapper.map(employee.getDepartment(), DepartmentDTO.class));
        // employeeDTO.setTeam(modelMapper.map(employee.getTeam(), TeamDTO.class));
        Period period = Period.between(employee.getHiredDate() , LocalDate.now());
        employeeDTO.setEmploymentPeriod(period.getYears() + " years, " + period.getMonths() + " months");
       // LocalDate birthDate =LocalDate.of(employeeDTO.getDateOfBirth())
        period =Period.between(employeeDTO.getDateOfBirth(),LocalDate.now());
        employeeDTO.setAge(period.getYears());
        employeeDTO.setAge(LocalDate.now().getYear()-employee.getDateOfBirth().getYear());
        return employeeDTO;
    }

    @Override
    public EmployeeDTO updateEmployeeDetails(EmployeeDTO employeeDto) {
        Employee employee = employeeRepository.findById(employeeDto.getId())
                .orElseThrow(() -> new EntityNotFoundException("Employee not found with id " + employeeDto.getId()));

        if (!Objects.equals(employee.getFirstName(), employeeDto.getFirstName())) {
            employee.setFirstName(employeeDto.getFirstName());
        }
        if (!Objects.equals(employee.getLastName(), employeeDto.getLastName())) {
            employee.setLastName(employeeDto.getLastName());
        }
        if (!Objects.equals(employee.getEmail(), employeeDto.getEmail())) {
            employee.setEmail(employeeDto.getEmail());
        }
        if (!Objects.equals(employee.getPhoneNo(), employeeDto.getPhoneNo())) {
            employee.setPhoneNo(employeeDto.getPhoneNo());
        }
        if (!Objects.equals(employee.getDateOfBirth(), employeeDto.getDateOfBirth())) {
            employee.setDateOfBirth(employeeDto.getDateOfBirth());
        }
        if (!Objects.equals(employee.getGender(), employeeDto.getGender())) {
            employee.setGender(employeeDto.getGender());
        }
        if (!Objects.equals(employee.getMaritalStatus(), employeeDto.getMaritalStatus())) {
            employee.setMaritalStatus(employeeDto.getMaritalStatus());
        }
        if (!Objects.equals(employee.getHiredDate(), employeeDto.getHiredDate())) {
            employee.setHiredDate(employeeDto.getHiredDate());
        }
        if (!Objects.equals(employee.getDesignation(), employeeDto.getDesignation())) {
            employee.setDesignation(employeeDto.getDesignation());
        }
        if (!Objects.equals(employee.getSalary(), employeeDto.getSalary())) {
            employee.setSalary(employeeDto.getSalary());
        }
        if (!Objects.equals(employee.getStatus(), employeeDto.getStatus())) {
            employee.setStatus(employeeDto.getStatus());
        }

        // Handling Department update
        if (employeeDto.getDepartment() != null && employeeDto.getDepartment().getId() != null) {
            if (employee.getDepartment() == null || !Objects.equals(employee.getDepartment().getId(), employeeDto.getDepartment().getId())) {
                Department department = departmentRepository.findById(employeeDto.getDepartment().getId())
                        .orElseThrow(() -> new RuntimeException("Department Not Found"));
                employee.setDepartment(department);
            }
        }

        // Handling Role update
        if (!Objects.equals(employee.getRole(), employeeDto.getRole())) {
            employee.setRole(employeeDto.getRole());
        }

        // Handling Team update
        if (employeeDto.getTeam() != null && employeeDto.getTeam().getId() != null) {
            if (employee.getTeam() == null || !Objects.equals(employee.getTeam().getId(), employeeDto.getTeam().getId())) {
                Team team = teamRepository.findById(employeeDto.getTeam().getId())
                        .orElseThrow(() -> new RuntimeException("Team Not Found"));
                employee.setTeam(team);
            }
        }

        // Handling Address update
        if (employeeDto.getAddress() != null) {
            Address address = modelMapper.map(employeeDto.getAddress(), Address.class);
            addressRepository.save(address);
            employee.setAddress(address);
        }

        Employee updatedEmployee = employeeRepository.save(employee);
        return modelMapper.map(updatedEmployee, EmployeeDTO.class);
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

    public List<EmployeeDTO> getEmployeesByTeamId(Long id){
        List<Employee> employees = employeeRepository.findByTeam_Id(id);
        return employees.stream().map(employee -> new EmployeeDTO(
                employee.getId(),
                employee.getFirstName(),
                employee.getLastName(),
                employee.getEmail(),
                employee.getDesignation()
        )).collect(Collectors.toList());
    }

    public List<EmployeeDTO> getEmployeesByDepartment(Long id){
        List<Employee> employees = employeeRepository.findByDepartment_Id(id);
        return employees.stream().map(employee -> new EmployeeDTO(
                employee.getId(),
                employee.getFirstName(),
                employee.getLastName(),
                employee.getEmail(),
                employee.getDesignation()
        )).collect(Collectors.toList());
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
