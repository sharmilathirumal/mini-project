package com.example.spring_thymeleaf.repository;

import com.example.spring_thymeleaf.entity.Employee;
import com.example.spring_thymeleaf.entity.Performance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface PerformanceRepository extends JpaRepository<Performance,Long> {
    List<Performance> findByEmployee_Id(Long id);

    @Query("SELECT COUNT(p) FROM Performance p WHERE p.employee = :employee AND YEAR(p.reviewDate) = :year")
    long countByEmployeeAndYear(@Param("employee") Employee employee, @Param("year") int year);
}
