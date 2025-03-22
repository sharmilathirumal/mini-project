package com.example.spring_thymeleaf.service;

import com.example.spring_thymeleaf.dto.PayrollDTO;

import java.util.List;

public interface PayrollService {
    PayrollDTO addPayroll(PayrollDTO payrollDTO);
    //PayrollDTO UpdatePayroll(Long id, PayrollDTO payrollDTO);-->NOT Happen In REAL World
    List<PayrollDTO> getPayrollByID(Long id);
    List<PayrollDTO> getAllPayrollDetails();
    void deletePayroll(Long id);
}
