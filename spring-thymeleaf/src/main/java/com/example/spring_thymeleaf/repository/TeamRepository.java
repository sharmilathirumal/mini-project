package com.example.spring_thymeleaf.repository;

import com.example.spring_thymeleaf.entity.Attendance;
import com.example.spring_thymeleaf.entity.Employee;
import com.example.spring_thymeleaf.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TeamRepository extends JpaRepository<Team,Long> {
    Team findByDepartment_Id(Long id);
}
