package com.example.spring_thymeleaf.repository;

import com.example.spring_thymeleaf.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee,Long> {
    List<Employee> findByTeam_Id(Long id);
    @Query("SELECT a.date FROM Attendance a WHERE a.employee.id = :employeeId ORDER BY a.date ASC")
    List<Date> findDatesByEmployeeId(@Param("employeeId") Long employeeId);
    boolean existsByEmail(String email);
    boolean existsByPhoneNo(Long phoneNo);
    List<Employee> findByTeamIdIn(List<Long> teamIds);

    @Query("SELECT e.id from Employee e where e.team.id IN :teamIds")
    List<Long> findEmployeeIdsByTeamIds(@Param("teamIds") List<Long> teamIds);

    @Query("SELECT e.address.id from Employee e where e.id= :employeeId")
    Long findAddressIdByEmployeeId(@Param("employeeId") Long employeeId);

}
