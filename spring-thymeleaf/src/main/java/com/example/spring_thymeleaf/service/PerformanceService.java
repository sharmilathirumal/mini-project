package com.example.spring_thymeleaf.service;

import com.example.spring_thymeleaf.dto.PerformanceDTO;
import com.example.spring_thymeleaf.entity.Performance;

import java.util.List;

public interface PerformanceService {
    PerformanceDTO addPerformance(PerformanceDTO performanceDTO);
    PerformanceDTO updatePerformance(Long id, PerformanceDTO performanceDTO);
    List<PerformanceDTO> getPerformanceById(Long id);
    List<PerformanceDTO> getAllPerformanceDetails();
    void deletePerformance(Long id);

}
