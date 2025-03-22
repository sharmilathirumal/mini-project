package com.example.spring_thymeleaf.repository;

import com.example.spring_thymeleaf.entity.Leave;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public interface LeaveRepository extends JpaRepository<Leave,Long> {
     List<Leave> findByEmployee_Id(Long id);

    @Query("SELECT l.startDate from Leave l where l.employee.id = :employeeId")
    List<LocalDate> findAllStartDateById(@Param("employeeId") Long id);

    @Query("SELECT l.endDate from Leave l where l.employee.id = :employeeId")
    List<LocalDate> findAllEndDateById(@Param("employeeId") Long id);

    @Query(value = """
    WITH RECURSIVE date_series AS (
        SELECT start_date AS leave_date, end_date FROM leave WHERE employee_id = :employeeId
        UNION ALL
        SELECT DATE_ADD(leave_date, INTERVAL 1 DAY), end_date
        FROM date_series
        WHERE leave_date < end_date
    )
    SELECT leave_date FROM date_series;
""", nativeQuery = true)
    List<LocalDate> findAllLoggedLeaveDates(@Param("employeeId") Long employeeId);

    @Query("SELECT COUNT(l) > 0 FROM Leave l WHERE l.employee.id = :employeeId AND :date BETWEEN l.startDate AND l.endDate")
    boolean existsByEmployeeIdAndDate(@Param("employeeId") Long employeeId, @Param("date") LocalDate date);


}
