package com.example.spring_thymeleaf.dto;
import com.example.spring_thymeleaf.enums.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Getter
@Setter
public class EmployeeDTO {
    private Long id;
    private Title title;
    private String firstName;
    private String lastName;
    private String email;
    private Long phoneNo;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateOfBirth;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate hiredDate;
    private EmployeeDesignation designation;
    private BigDecimal salary;
    private EmployeeStatus status;
    private Gender gender;
    private MaritalStatus maritalStatus;
    private Country country;
    private DepartmentDTO department;
    private Role role;
    private TeamDTO team;
    private AddressDTO address;
    private String employmentPeriod;
    private int age;

    public EmployeeDTO(Long id, String firstName, String lastName, String email, EmployeeDesignation designation) {
        this.id =id;
        this.firstName=firstName;
        this.lastName=lastName;
        this.email=email;
        this.designation=designation;
    }

    public EmployeeDTO() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public Long getPhoneNo() {
        return phoneNo;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public LocalDate getHiredDate() {
        return hiredDate;
    }

    public EmployeeDesignation getDesignation() {
        return designation;
    }

    public BigDecimal getSalary() {
        return salary;
    }

    public EmployeeStatus getStatus() {
        return status;
    }


    /* public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }*/

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhoneNo(Long phoneNo) {
        this.phoneNo = phoneNo;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public void setHiredDate(LocalDate hiredDate) {
        this.hiredDate = hiredDate;
    }

    public void setDesignation(EmployeeDesignation designation) {
        this.designation = designation;
    }

    public void setSalary(BigDecimal salary) {
        this.salary = salary;
    }

    public void setStatus(EmployeeStatus status) {
        this.status = status;
    }

    /* public EmployeeDTO(Long id, String firstName, String lastName, String email,
                       Date hiredDate, String departmentName){
        this.id =id;
        this.firstName =firstName;
        this.lastName =lastName;
        this.email =email;
        this.hiredDate =hiredDate;
        this.departmentName =departmentName;
    }*/

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public AddressDTO getAddress() {
        return address;
    }

    public void setAddress(AddressDTO address) {
        this.address = address;
    }

    public String getEmploymentPeriod() {
        return employmentPeriod;
    }

    public void setEmploymentPeriod(String employmentPeriod) {
        this.employmentPeriod = employmentPeriod;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public MaritalStatus getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(MaritalStatus maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public Title getTitle() {
        return title;
    }

    public void setTitle(Title title) {
        this.title = title;
    }

    public DepartmentDTO getDepartment() {
        return department;
    }

    public void setDepartment(DepartmentDTO department) {
        this.department = department;
    }

    public TeamDTO getTeam() {
        return team;
    }

    public void setTeam(TeamDTO team) {
        this.team = team;
    }
}
