package com.example.spring_thymeleaf.repository;

import com.example.spring_thymeleaf.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentRepository extends JpaRepository<Department,Long> {
}
