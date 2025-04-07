package com.example.spring_thymeleaf.repository;

import com.example.spring_thymeleaf.entity.Attendance;
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

    @Query("SELECT COUNT(l) > 0 FROM Leave l WHERE l.employee.id = :employeeId AND :date BETWEEN l.startDate AND l.endDate")
    boolean existsByEmployeeIdAndDate(@Param("employeeId") Long employeeId, @Param("date") LocalDate date);

    @Query("SELECT l from Leave l WHERE l.employee.id IN :employeeIds and l.leaveStatus ='PENDING'")
    List<Leave> findAllPendingLeaveByEmployeeIds(@Param("employeeIds")List<Long> employeeIds);

    Leave findByStartDate(LocalDate date);
}
