package com.example.spring_thymeleaf.dto;
import com.example.spring_thymeleaf.enums.EmployeeStatus;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.Date;

@Data
@Getter
@Setter
public class EmployeeDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private Long phoneNo;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dateOfBirth;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date hiredDate;
    private String designation;
    private Double salary;
    private EmployeeStatus status;
    private Long departmentId;
  //  private String departmentName;

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

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public Date getHiredDate() {
        return hiredDate;
    }

    public String getDesignation() {
        return designation;
    }

    public Double getSalary() {
        return salary;
    }

    public EmployeeStatus getStatus() {
        return status;
    }

    public Long getDepartmentId() {
        return departmentId;
    }

   /* public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }*/

    public void setDepartmentId(Long departmentId) {
        this.departmentId = departmentId;
    }

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

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public void setHiredDate(Date hiredDate) {
        this.hiredDate = hiredDate;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public void setSalary(Double salary) {
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
}
