package com.example.spring_thymeleaf.entity;

import com.example.spring_thymeleaf.enums.*;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
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

    @NotNull(message = "First Name is required")
    @Column(nullable = false)
    private String firstName;

    @NotNull(message = "LastName is required")
    @Column(nullable = false)
    private String lastName;

    @NotNull(message = "Email is required")
    @Column(nullable = false,unique = true)
    private String email;

    @NotNull(message = "Phone Number is required")
    @Column(nullable = false, unique = true)
    private Long phoneNo;

    @NotNull(message = "Date Of Birth is required")
    @Temporal(TemporalType.DATE)
    @Column(nullable = false)
    private LocalDate dateOfBirth;

    @NotNull(message = "Hired Date is required")
    @Temporal(TemporalType.DATE)
    @Column(nullable = false)
    private LocalDate hiredDate;

    @NotNull(message = "Designation is required")
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private EmployeeDesignation designation;

    @NotNull(message = "Salary is required")
    @Column(nullable = false)
    private BigDecimal salary;

    @NotNull(message = "Status is required")
    @Enumerated(EnumType.STRING)
    private EmployeeStatus status;

    @NotNull(message = "Gender is required")
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @NotNull(message = "Marital Status is required")
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private MaritalStatus maritalStatus;

    @OneToOne(cascade = CascadeType.ALL,orphanRemoval = true)
    @JoinColumn(name = "address_id",referencedColumnName = "id",unique = true)
    private Address address;

    @NotNull(message = "Role is required")
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToMany(mappedBy = "employee",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Attendance> attendance;

    @OneToMany(mappedBy = "employee",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Leave> leaves;

    @OneToMany(mappedBy = "employee",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Payroll> payroll;

    @ManyToOne
    @JoinColumn(name = "teamId",nullable = false)
    private Team team;

    @OneToOne(mappedBy = "employee", cascade = CascadeType.ALL, fetch = FetchType.LAZY,orphanRemoval = true)
    private User user;

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

    public List<Attendance> getAttendance() {
        return attendance;
    }


    public Address getAddress() {
        return address;
    }

    public Role getRole() {
        return role;
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

    public void setAttendance(List<Attendance> attendance) {
        this.attendance = attendance;
    }

    public void setLeaves(List<Leave> leaves) {
        this.leaves = leaves;
    }

    public void setPayroll(List<Payroll> payroll) {
        this.payroll = payroll;
    }


    public void setAddress(Address address) {
        this.address = address;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

}
