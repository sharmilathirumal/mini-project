package com.example.spring_thymeleaf.service.Impl;

import com.example.spring_thymeleaf.dto.PerformanceDTO;
import com.example.spring_thymeleaf.entity.Employee;
import com.example.spring_thymeleaf.entity.Performance;
import com.example.spring_thymeleaf.repository.EmployeeRepository;
import com.example.spring_thymeleaf.repository.PerformanceRepository;
import com.example.spring_thymeleaf.service.PerformanceService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Year;
import java.time.ZoneId;
import java.util.*;

@Service
public class PerformanceServiceImpl implements PerformanceService {

    @Autowired
    private PerformanceRepository performanceRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public PerformanceDTO AddPerformance(PerformanceDTO performanceDTO) {
        Employee employee = employeeRepository.findById(performanceDTO.getEmployeeId()).orElseThrow(()->new RuntimeException("Employee Not Found"));
        int currentYear = Year.now().getValue();

        if(performanceRepository.countByEmployeeAndYear(employee,currentYear) >=2){
            throw new RuntimeException("Performance review can only be added twice per year.");
        }

        if(performanceDTO.getPerformanceScore()<1 || performanceDTO.getPerformanceScore()>10){
            throw new RuntimeException("Performance score must be between 1 and 10.");
        }

        if(performanceDTO.getReviewDate().after(new Date())){
            throw new RuntimeException("Review date cannot be in the future.");
        }
        Performance performance = modelMapper.map(performanceDTO,Performance.class);
        performance.setEmployee(employee);
        performance.setId(null);
        return modelMapper.map(performanceRepository.save(performance),PerformanceDTO.class);
    }

    @Override
    public PerformanceDTO UpdatePerformance(Long id, PerformanceDTO performanceDTO) {
        Performance performance = performanceRepository.findById(id).orElseThrow(()->new RuntimeException("Performance not Found"));
        if(!Objects.equals(performance.getReviewDate(),performanceDTO.getReviewDate())){
            performance.setReviewDate(performanceDTO.getReviewDate());
        }
        if(!Objects.equals(performance.getPerformanceScore(),performanceDTO.getPerformanceScore())){
            performance.setPerformanceScore(performanceDTO.getPerformanceScore());
        }
        if(!Objects.equals(performance.getComment(),performanceDTO.getComment())){
            performance.setComment(performanceDTO.getComment());
        }
        return modelMapper.map(performanceRepository.save(modelMapper.map(performance,Performance.class)),PerformanceDTO.class);
    }

    @Override
    public List<PerformanceDTO> GetPerformanceById(Long id) {
        if(employeeRepository.findById(id).isEmpty()){
            throw new RuntimeException("Employee Not Found");
        }
        //Performance performance = performanceRepository.findById(id).orElseThrow(()->new RuntimeException("Performance not found"));
        List<Performance> performances = performanceRepository.findByEmployee_Id(id);
        List<PerformanceDTO> performanceDTOS = new ArrayList<>();
        for(Performance performance:performances){
            performanceDTOS.add(modelMapper.map(performance,PerformanceDTO.class));
        }
        return performanceDTOS;
    }

    @Override
    public List<PerformanceDTO> GetAllPerformanceDetails() {
        List<Performance> performances =performanceRepository.findAll();
        List<PerformanceDTO> performanceDTOS =new ArrayList<>();
        for(Performance performance : performances){
           performanceDTOS.add(modelMapper.map(performance,PerformanceDTO.class));
        }
        return performanceDTOS;
    }

    @Override
    public void DeletePerformance(Long id) {
        Optional<Performance> performance = performanceRepository.findById(id);
        if(performance.isPresent()){
            performanceRepository.deleteById(id);
        }else{
            throw new RuntimeException("performance not found");
        }
    }
}
