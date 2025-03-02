package com.example.spring_thymeleaf.repository;

import com.example.spring_thymeleaf.entity.Leave;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface LeaveRepository extends JpaRepository<Leave,Long> {
     List<Leave> findByEmployee_Id(Long id);

    @Query("SELECT l.startDate from Leave l where l.employee.id = :employeeId")
    List<Date> findAllStartDateById(@Param("employeeId") Long id);

    @Query("SELECT l.endDate from Leave l where l.employee.id = :employeeId")
    List<Date> findAllEndDateById(@Param("employeeId") Long id);
}
