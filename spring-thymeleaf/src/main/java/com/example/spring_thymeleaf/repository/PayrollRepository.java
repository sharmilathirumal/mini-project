package com.example.spring_thymeleaf.repository;

import com.example.spring_thymeleaf.entity.Employee;
import com.example.spring_thymeleaf.entity.Payroll;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface PayrollRepository extends JpaRepository<Payroll,Long> {
     List<Payroll> findByEmployeeId(Long id);
     @Query("SELECT COUNT(p) FROM Payroll p WHERE p.employee=:employee AND FUNCTION('YEAR', p.payDate) = :year AND FUNCTION('MONTH', p.payDate) = :month")
     long countByEmployeeAndYearAndMonth(@Param("employee") Employee employee, @Param("year") int year, @Param("month") int month);

}
