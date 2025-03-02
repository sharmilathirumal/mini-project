package com.example.spring_thymeleaf.service;

import com.example.spring_thymeleaf.dto.PerformanceDTO;
import com.example.spring_thymeleaf.entity.Performance;

import java.util.List;

public interface PerformanceService {
    PerformanceDTO AddPerformance(PerformanceDTO performanceDTO);//check wheather the employee is exist or not
    PerformanceDTO UpdatePerformance(Long id, PerformanceDTO performanceDTO);//check wheather the employee is exist or not
    List<PerformanceDTO> GetPerformanceById(Long id);//check wheather the employee is exist
    List<PerformanceDTO> GetAllPerformanceDetails();
    void DeletePerformance(Long id);

}
