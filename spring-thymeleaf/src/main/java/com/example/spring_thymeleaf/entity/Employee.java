package com.example.spring_thymeleaf.entity;

import com.example.spring_thymeleaf.enums.EmployeeStatus;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Getter
@Setter
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false,unique = true)
    private String email;

    @Column(nullable = false)
    private Long phoneNo;

    @Temporal(TemporalType.DATE)
    @Column(nullable = true)
    private Date dateOfBirth;

    @Temporal(TemporalType.DATE)
    @Column(nullable = false)
    private Date hiredDate;

    @Column(nullable = false)
    private String designation;

    @Column(nullable = false)
    private Double salary;

    @Enumerated(EnumType.STRING)
    private EmployeeStatus status;

    @ManyToOne
    @JoinColumn(name = "department_id",nullable = false)
    private Department department;

    @OneToMany(mappedBy = "employee",cascade = CascadeType.ALL)
    private List<Attendance> attendance;

    @OneToMany(mappedBy = "employee",cascade = CascadeType.ALL)
    private List<Leave> leaves;

    @OneToMany(mappedBy = "employee",cascade = CascadeType.ALL)
    private List<Payroll> payroll;

    public String getFirstName() {
        return firstName;
    }

    public Long getId() {
        return id;
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

    public List<Attendance> getAttendance() {
        return attendance;
    }

    public List<Leave> getLeaves() {
        return leaves;
    }

    public List<Payroll> getPayroll() {
        return payroll;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    private Long getDepartmentId(Long id){
        return  department.getId();
    }

    private void setDepartmentId(Long id){
          department.setId(id);
    }

    public void setAttendance(List<Attendance> attendance) {
        this.attendance = attendance;
    }

    public void setLeaves(List<Leave> leaves) {
        this.leaves = leaves;
    }

    public void setPayroll(List<Payroll> payroll) {
        this.payroll = payroll;
    }
}
