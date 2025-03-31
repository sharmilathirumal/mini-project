package com.example.spring_thymeleaf.service.Impl;

import com.example.spring_thymeleaf.dto.PayrollDTO;
import com.example.spring_thymeleaf.entity.Employee;
import com.example.spring_thymeleaf.entity.Payroll;
import com.example.spring_thymeleaf.repository.EmployeeRepository;
import com.example.spring_thymeleaf.repository.PayrollRepository;
import com.example.spring_thymeleaf.service.PayrollService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PayrollServiceImpl implements PayrollService {

    @Autowired
    private PayrollRepository payrollRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public PayrollDTO addPayroll(PayrollDTO payrollDTO) {
        Employee employee = employeeRepository.findById(payrollDTO.getEmployeeId()).orElseThrow(()->new IllegalStateException("Employee Not Found"));
        LocalDate payrollDate = payrollDTO.getPayDate();
        if (payrollRepository.countByEmployeeAndYearAndMonth(employee, payrollDate.getYear(), payrollDate.getMonthValue()) > 0) {
            throw new IllegalStateException("A payroll record for this employee has already been created for the current month.");
        }
        /*if(payrollDTO.getSalary()<=0 ||payrollDTO.getSalary()<15000){
                throw new IllegalStateException("The salary must exceed zero, with our minimum salary commencing at 15,000.");
        }*/
        payrollDTO.setSalary(employee.getSalary());

        if(payrollDTO.getDeduction().compareTo(BigDecimal.valueOf(7000))>0){
            throw new IllegalStateException(("All employee deductions must not exceed 7,000."));
        }

        if(payrollDTO.getPayDate().isAfter(LocalDate.now())){
            throw new IllegalStateException("Future date cannot be added");
        }

        Payroll payroll =modelMapper.map(payrollDTO,Payroll.class);
        payroll.setId(null);
        payroll.setEmployee(employee);
        payroll.setNetPay(payrollDTO.getSalary().subtract(payrollDTO.getDeduction()));
         return modelMapper.map(payrollRepository.save(payroll),PayrollDTO.class);
    }

   /* @Override
    public PayrollDTO UpdatePayroll(Long id, PayrollDTO payrollDTO) {
        Payroll payroll = payrollRepository.findById(id).orElseThrow(()->new RuntimeException("Payroll not Found"));
        if(payroll.getSalary()!=payrollDTO.getSalary()){
            payroll.setSalary(payrollDTO.getSalary());
        }
        if(payroll.getPayDate()!=payrollDTO.getPayDate()){
            payroll.setPayDate(payrollDTO.getPayDate());
        }
        payrollRepository.save(payroll);
        return modelMapper.map(payroll, PayrollDTO.class);
    }*/

    @Override
    public List<PayrollDTO> getPayrollByID(Long id) {
        if(employeeRepository.findById(id).isEmpty()){
            throw new IllegalStateException("Employee Not Found");
        }
       List<Payroll> payrolls = payrollRepository.findByEmployeeId(id);

        return payrolls.stream().map(payroll -> modelMapper.map(payroll,PayrollDTO.class)).collect(Collectors.toList());
    }

    @Override
    public List<PayrollDTO> getAllPayrollDetails() {
        List<Payroll> payrolls = payrollRepository.findAll();
        List<PayrollDTO> payrollDTOS =new ArrayList<>();
        for(Payroll payroll :payrolls){
            payrollDTOS.add(modelMapper.map(payroll,PayrollDTO.class));
        }
        return payrollDTOS;
    }

    @Override
    public void deletePayroll(Long id) {
        payrollRepository.findById(id).orElseThrow(()->new RuntimeException("Payroll not found"));
        payrollRepository.deleteById(id);
    }
}
