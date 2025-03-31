package com.example.spring_thymeleaf.service.Impl;

import com.example.spring_thymeleaf.dto.EmployeeDTO;
import com.example.spring_thymeleaf.entity.*;
import com.example.spring_thymeleaf.enums.*;
import com.example.spring_thymeleaf.repository.*;
import com.example.spring_thymeleaf.security.SecurityUtil;
import com.example.spring_thymeleaf.service.AddressService;
import com.example.spring_thymeleaf.service.EmployeeService;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.Map;
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
    private AddressService addressService;
    @Autowired
    private TeamRepository teamRepository;
    List<EmployeeDesignation> managerRole = List.of(EmployeeDesignation.EXPERT_SOFTWARE_DEVELOPMENT_ENGINEER, EmployeeDesignation.HR_MANAGER, EmployeeDesignation.HR_DIRECTOR, EmployeeDesignation.OPERATIONS_MANAGER,
            EmployeeDesignation.DIRECTOR_OF_ADMINISTRATION);

    @Override
    public void saveEmployee(EmployeeDTO employeeDto) {
        Team team = teamRepository.findById(employeeDto.getTeam().getId()).orElseThrow(() -> new IllegalStateException("Team Not Found"));

        if (employeeDto.getDateOfBirth().isAfter(LocalDate.now().minusYears(21))) {
            throw new IllegalStateException("Employee date of birth should be appropriate , Employee age should be at least 21");
        }
        if (employeeDto.getRole() == Role.MANAGER && !managerRole.contains(employeeDto.getDesignation())) {
            throw new IllegalStateException("Employee doesn't have appropriate designation to be a Manager");
        }
        if (employeeDto.getHiredDate().isAfter(LocalDate.now())) {
            throw new IllegalStateException("Hired date should not be future date");
        }
        if (employeeRepository.existsByEmail(employeeDto.getEmail())) {
            throw new IllegalStateException("Email is already taken");
        }
        if (employeeRepository.existsByPhoneNo(employeeDto.getPhoneNo())) {
            throw new IllegalStateException("Phone number is already in use");
        }
        if(employeeDto.getPhoneNo().toString().length() !=10){
            throw new IllegalStateException("Phone number should be at a 10 digit number");
        }
        if (employeeDto.getSalary().compareTo(BigDecimal.valueOf(15000)) < 0) {
            throw new IllegalStateException("Salary must be greater than or equal to 15000");
        }

        Employee employee = modelMapper.map(employeeDto, Employee.class);
        employee.setId(null);
        employee.setTeam(team);
        employee.setStatus(EmployeeStatus.PRESENT);
        if (employeeDto.getAddress() != null) {
            addressService.addAddress(employeeDto.getAddress());
        }
        employeeRepository.save(employee);

    }

    @Override
    public EmployeeDTO updateEmployeeDetails(EmployeeDTO employeeDto) {
        Employee employee = employeeRepository.findById(employeeDto.getId())
                .orElseThrow(() -> new IllegalStateException("Employee Not Found !!"));

        if (!Objects.equals(employee.getFirstName(), employeeDto.getFirstName())) {
            employee.setFirstName(employeeDto.getFirstName());
        }
        if (!Objects.equals(employee.getLastName(), employeeDto.getLastName())) {
            employee.setLastName(employeeDto.getLastName());
        }
        if (!Objects.equals(employee.getEmail(), employeeDto.getEmail())) {
            if (employeeRepository.existsByEmail(employeeDto.getEmail())) {
                throw new IllegalStateException("Email is already taken");
            }
            employee.setEmail(employeeDto.getEmail());
        }
        if (!Objects.equals(employee.getPhoneNo(), employeeDto.getPhoneNo())) {
            if (employeeRepository.existsByPhoneNo(employeeDto.getPhoneNo())) {
                throw new IllegalStateException("Phone number is already in use");
            }
            if(employeeDto.getPhoneNo().toString().length() !=10){
                throw new IllegalStateException("Phone number should be at a 10 digit number");
            }
            employee.setPhoneNo(employeeDto.getPhoneNo());
        }
        if (!Objects.equals(employee.getDateOfBirth(), employeeDto.getDateOfBirth())) {
            if (employeeDto.getDateOfBirth().isAfter(LocalDate.now().minusYears(21))) {
                throw new IllegalStateException("Employee date of birth should be appropriate , Employee age should be at least 21");
            }
            employee.setDateOfBirth(employeeDto.getDateOfBirth());
        }
        if (!Objects.equals(employee.getGender(), employeeDto.getGender())) {
            employee.setGender(employeeDto.getGender());
        }
        if (!Objects.equals(employee.getMaritalStatus(), employeeDto.getMaritalStatus())) {
            employee.setMaritalStatus(employeeDto.getMaritalStatus());
        }
        if (!Objects.equals(employee.getHiredDate(), employeeDto.getHiredDate())) {
            if (employeeDto.getHiredDate().isAfter(LocalDate.now())) {
                throw new IllegalStateException("Hired date should not be future date");
            }
            employee.setHiredDate(employeeDto.getHiredDate());
        }
        if (!Objects.equals(employee.getDesignation(), employeeDto.getDesignation())) {
            employee.setDesignation(employeeDto.getDesignation());
        }
        if (!Objects.equals(employee.getSalary(), employeeDto.getSalary())) {
            if (employeeDto.getSalary().compareTo(BigDecimal.valueOf(15000)) < 0) {
                throw new IllegalStateException("Salary must be greater than or equal to 15000");
            }
            employee.setSalary(employeeDto.getSalary());
        }
        if (!Objects.equals(employee.getStatus(), employeeDto.getStatus())) {

            employee.setStatus(employeeDto.getStatus());
        }

        if (!Objects.equals(employee.getRole(), employeeDto.getRole())) {
            if (employeeDto.getRole() == Role.MANAGER && !managerRole.contains(employeeDto.getDesignation())) {
                throw new IllegalStateException("Employee doesn't have appropriate designation to be a Manager");
            }
            employee.setRole(employeeDto.getRole());
        }

        if (employeeDto.getTeam() != null && employeeDto.getTeam().getId() != null) {
            if (employee.getTeam() == null || !Objects.equals(employee.getTeam().getId(), employeeDto.getTeam().getId())) {
                Team team = teamRepository.findById(employeeDto.getTeam().getId())
                        .orElseThrow(() -> new RuntimeException("Team Not Found"));
                employee.setTeam(team);
            }
        }

        addressService.updateAddress(employeeDto.getAddress());

        Employee updatedEmployee = employeeRepository.save(employee);
        return modelMapper.map(updatedEmployee, EmployeeDTO.class);
    }

    @Override
    public List<EmployeeDTO> getAllEmployees() {
        return employeeRepository.findAll().stream()
                .map(employee -> {
                    EmployeeDTO employeeDTO = modelMapper.map(employee, EmployeeDTO.class);
                    Period period = Period.between(employee.getHiredDate(), LocalDate.now());
                    employeeDTO.setEmploymentPeriod(period.getYears() + " years, " + period.getMonths() + " months");
                    return employeeDTO;
                })
                .collect(Collectors.toList());
    }


    @Override
    public EmployeeDTO getEmployeeById(Long id) {
        //return modelMapper.map(employeeRepository.findById(id),EmployeeDto.class);
        Employee employee = employeeRepository.findById(id).orElseThrow(() -> new RuntimeException("Employee Not Found"));
        EmployeeDTO employeeDTO = modelMapper.map(employee, EmployeeDTO.class);
        if (employee.getGender() != null) {
            switch (employee.getGender()) {
                case MALE -> employeeDTO.setTitle(Title.Mr);
                case FEMALE -> employeeDTO.setTitle(
                        employee.getMaritalStatus() == MaritalStatus.MARRIED ? Title.Mrs : Title.Miss
                );
                default -> employeeDTO.setTitle(Title.Mx);
            }
        }
        //employeeDTO.setDepartment(modelMapper.map(employee.getDepartment(), DepartmentDTO.class));
        // employeeDTO.setTeam(modelMapper.map(employee.getTeam(), TeamDTO.class));
        Period period = Period.between(employee.getHiredDate(), LocalDate.now());
        employeeDTO.setEmploymentPeriod(period.getYears() + " years, " + period.getMonths() + " months");
        // LocalDate birthDate =LocalDate.of(employeeDTO.getDateOfBirth())
        period = Period.between(employeeDTO.getDateOfBirth(), LocalDate.now());
        employeeDTO.setAge(period.getYears());
        employeeDTO.setAge(LocalDate.now().getYear() - employee.getDateOfBirth().getYear());
        return employeeDTO;
    }

    @Override
    public void deleteEmployeeById(Long id) {
        Employee employee = employeeRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Employee Not Found!!"));
        if(employee.getStatus().equals(EmployeeStatus.PRESENT)){
            throw new IllegalStateException("Employee still working in our organisation,can't able to delete this employee");
        }
        employeeRepository.deleteById(id);
    }

    public List<EmployeeDTO> getEmployeesByTeamId(Long id) {
        List<Employee> employees = employeeRepository.findByTeam_Id(id);
        return employees.stream().map(employee -> new EmployeeDTO(
                employee.getId(),
                employee.getFirstName(),
                employee.getLastName(),
                employee.getEmail(),
                employee.getDesignation()
        )).collect(Collectors.toList());
    }

    public List<EmployeeDTO> getAllEmployeesUnderManager(Long id, List<Long> teamIds) {
        List<Employee> employees = employeeRepository.findByTeamIdIn(teamIds);

        return employees.stream()
                .map(employee -> modelMapper.map(employee, EmployeeDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<Long> getEmployeeIdsByTeamIds(List<Long> teamIds){
        return employeeRepository.findEmployeeIdsByTeamIds(teamIds);
    }

}
