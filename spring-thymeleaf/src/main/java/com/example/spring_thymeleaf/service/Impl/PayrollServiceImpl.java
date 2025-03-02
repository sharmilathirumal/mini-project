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

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

@Service
public class PayrollServiceImpl implements PayrollService {

    @Autowired
    private PayrollRepository payrollRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public PayrollDTO AddPayroll(PayrollDTO payrollDTO) {
        Employee employee = employeeRepository.findById(payrollDTO.getEmployeeId()).orElseThrow(()->new RuntimeException("You can't add payroll to this employee because Employee Not Found"));
        LocalDate payrollDate = payrollDTO.getPayDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        int year = payrollDate.getYear();
        int month = payrollDate.getMonthValue();
        if(payrollRepository.existsByEmployeeAndYearAndMonth(employee,year,month)){
            throw new RuntimeException("Payroll record already exists for this employee");
        }
        if(payrollDTO.getSalary()<=0){
            throw new RuntimeException("salary must be greater that zero");
        }
        Payroll payroll =modelMapper.map(payrollDTO,Payroll.class);
        payroll.setEmployee(employee);
        payroll.setId(null);
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
    public List<PayrollDTO> GetPayrollByID(Long id) {
        if(employeeRepository.findById(id).isEmpty()){
            throw new RuntimeException("Employee Not Found");
        }
       List<Payroll> payrolls = payrollRepository.findByEmployeeId(id);
        if(payrolls.isEmpty()){
            throw new RuntimeException("this employee have no payroll data");
        }
       List<PayrollDTO> payrollDTOS =new ArrayList<>();
       for(Payroll payroll : payrolls){
           payrollDTOS.add(modelMapper.map(payroll,PayrollDTO.class));
       }
        return payrollDTOS;
    }

    @Override
    public List<PayrollDTO> GetAllPayrollDetails() {
        List<Payroll> payrolls = payrollRepository.findAll();
        List<PayrollDTO> payrollDTOS =new ArrayList<>();
        for(Payroll payroll :payrolls){
            payrollDTOS.add(modelMapper.map(payroll,PayrollDTO.class));
        }
        return payrollDTOS;
    }

    @Override
    public void DeletePayroll(Long id) {
        payrollRepository.findById(id).orElseThrow(()->new RuntimeException("Payroll not found"));
        payrollRepository.deleteById(id);
    }
}
