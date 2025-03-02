package com.example.spring_thymeleaf.service;

import com.example.spring_thymeleaf.dto.PayrollDTO;

import java.util.List;

public interface PayrollService {
    PayrollDTO AddPayroll(PayrollDTO payrollDTO);
    //PayrollDTO UpdatePayroll(Long id, PayrollDTO payrollDTO);-->NOT Happen In REAL World
    List<PayrollDTO> GetPayrollByID(Long id);
    List<PayrollDTO> GetAllPayrollDetails();
    void DeletePayroll(Long id);
}
