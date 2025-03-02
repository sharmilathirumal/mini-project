package com.example.spring_thymeleaf.repository;

import com.example.spring_thymeleaf.dto.AttendanceDTO;
import com.example.spring_thymeleaf.entity.Attendance;
import com.example.spring_thymeleaf.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AttendanceRepository extends JpaRepository<Attendance,Long> {
    public List<Attendance> findByEmployee_Id(Long id);

   // public List<AttendanceDTO>
}
