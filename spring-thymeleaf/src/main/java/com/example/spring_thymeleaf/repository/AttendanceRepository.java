package com.example.spring_thymeleaf.repository;

import com.example.spring_thymeleaf.dto.AttendanceDTO;
import com.example.spring_thymeleaf.entity.Attendance;
import com.example.spring_thymeleaf.entity.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public interface AttendanceRepository extends JpaRepository<Attendance,Long> {
    public List<Attendance> findByEmployee_Id(Long id);
    @Query("SELECT a.date FROM Attendance a WHERE a.employee.id = :employeeId ORDER BY a.date ASC")
    List<Date> findDatesByEmployeeId(@Param("employeeId") Long employeeId);
    boolean existsByEmployeeIdAndDate(Long employeeId, LocalDate date);
}
