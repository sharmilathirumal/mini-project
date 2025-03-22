package com.example.spring_thymeleaf.repository;

import com.example.spring_thymeleaf.dto.EmployeeDTO;
import com.example.spring_thymeleaf.entity.Department;
import com.example.spring_thymeleaf.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee,Long> {
    List<Employee> findByDepartment_Id(Long id);
    List<Employee> findByTeam_Id(Long id);
    @Query("SELECT a.date FROM Attendance a WHERE a.employee.id = :employeeId ORDER BY a.date ASC")
    List<Date> findDatesByEmployeeId(@Param("employeeId") Long employeeId);
    /*@Query("SELECT new com.example.spring_thymeleaf.dto.EmployeeDTO("+
            "e.id,e.firstName,e.lastName,e.email,e.hiredDate,d.departmentName)"+
            "FROM Employee e Join Department d on e.department.id = d.id")
    List<EmployeeDTO> findAllEmployeeWithDepartment();*/
}
