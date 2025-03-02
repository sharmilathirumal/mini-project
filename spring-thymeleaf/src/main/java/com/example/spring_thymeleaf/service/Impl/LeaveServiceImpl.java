package com.example.spring_thymeleaf.service.Impl;

import com.example.spring_thymeleaf.dto.LeaveDTO;
import com.example.spring_thymeleaf.entity.Employee;
import com.example.spring_thymeleaf.entity.Leave;
import com.example.spring_thymeleaf.enums.LeaveStatus;
import com.example.spring_thymeleaf.enums.LeaveType;
import com.example.spring_thymeleaf.repository.EmployeeRepository;
import com.example.spring_thymeleaf.repository.LeaveRepository;
import com.example.spring_thymeleaf.service.LeaveService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class LeaveServiceImpl implements LeaveService {
    @Autowired
    private LeaveRepository leaveRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public LeaveDTO ApplyForLeave(Long emp_id,Date endDate,LeaveType type,Date startDate){
        Employee employee = employeeRepository.findById(emp_id).orElseThrow(()->new RuntimeException("Employee Not Found"));
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        List<Date> start_Date = leaveRepository.findAllStartDateById(emp_id);
        List<Date> end_Date = leaveRepository.findAllEndDateById(emp_id);

        for(Date date : start_Date) {
            if(sdf.format(date).equals(sdf.format(startDate))) {
                throw new RuntimeException("Employee already logged with this start date, please check and try again.");
            }
        }

        for(Date date : end_Date) {
            if(sdf.format(date).equals(sdf.format(endDate))) {
                throw new RuntimeException("Employee already logged with this end date, please check and try again.");
            }
        }
        //String s = sdf.format(startDate);
        Leave leave = new Leave();
        if(startDate.after(endDate)){
            throw new RuntimeException("start date should be less than end date");
        }
        leave.setEmployee(employee);
        leave.setLeaveType(type);
        leave.setStartDate(startDate);
        leave.setEndDate(endDate);
        leave.setLeaveStatus(LeaveStatus.PENDING);
        leaveRepository.save(leave);
        return modelMapper.map(leave,LeaveDTO.class);
    }

    @Override
    public LeaveDTO UpdateLeaveStatus(LeaveDTO leaveDTO) {
        Leave leave = leaveRepository.findById(leaveDTO.getId()).orElseThrow(()->new RuntimeException("Leave not found"));
        if(!leave.getLeaveStatus().equals(LeaveStatus.PENDING)){
            throw new RuntimeException("Only Pending leave record can be updated");
        }
            leave.setLeaveStatus(leaveDTO.getLeaveStatus());

        leaveRepository.save(leave);
       return modelMapper.map(leave,LeaveDTO.class);
    }

    @Override
    public LeaveDTO UpdateLeaveRecord(Long id, LeaveType leaveType, Date startDate, Date endDate) {
            Leave leave = leaveRepository.findById(id).orElseThrow(()->new RuntimeException("Leave not found"));
            Long emp_id = leave.getEmployee().getId();
            if(!leave.getLeaveStatus().equals(LeaveStatus.PENDING)){
                throw new RuntimeException("Only Pending leave record can be updated");
            }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        List<Date> start_Date = leaveRepository.findAllStartDateById(emp_id);
        List<Date> end_Date = leaveRepository.findAllEndDateById(emp_id);
        for(Date date : start_Date) {
            if(sdf.format(date).equals(sdf.format(startDate))) {
                throw new RuntimeException("Employee already logged with this start date, please check and try again.");
            }
        }

        for(Date date : end_Date) {
            if(sdf.format(date).equals(sdf.format(endDate))) {
                throw new RuntimeException("Employee already logged with this end date, please check and try again.");
            }
        }
            if(startDate ==null && endDate !=null){
                if(leave.getStartDate().after(endDate)) throw new RuntimeException("start date should not be higher than end date");
             }
            if(leaveType !=null){
                leave.setLeaveType(leaveType);
            }
            if(startDate !=null){
                if(startDate.after(endDate)) throw new RuntimeException("start date should not be higher than end date");
                leave.setStartDate(startDate);
            }
            if(endDate != null){
                leave.setEndDate(endDate);
            }

            /*if(status !=null){
                leave.setLeaveStatus(status);
            }*/
            //leave.setLeaveStatus(leave);
             leaveRepository.save(leave);
             return modelMapper.map(leave,LeaveDTO.class);
    }

    @Override
    public List<LeaveDTO> GetAllLeaves() {
        List<Leave> leaves = leaveRepository.findAll();
        if(leaves.size() ==0) throw new RuntimeException("Employee does not have leave details");
        List<LeaveDTO> leaveDTOS = new ArrayList<>();
        for(Leave leave :leaves){
           leaveDTOS.add(modelMapper.map(leave,LeaveDTO.class));
        }
        return leaveDTOS;
    }

    /*@Override
    public List<LeaveDTO> GetLeavesById(Long id) {
        return List.of();
    }*/

   /* @Override
    public List<LeaveDTO> GetLeavesById(Long id) {
        //Optional<Employee> employee = employeeRepository.findById(id);
        List<Leave> leaves = leaveRepository.findByEmployeeId(id);
        List<LeaveDTO> leaveDTOS = new ArrayList<>();
        for(Leave leave :leaves){
            leaveDTOS.add(modelMapper.map(leave,LeaveDTO.class));
        }
        return leaveDTOS;
    }*/

   /* @Override
    public void CancelLeaveRequest(Long id) {
        Leave leave = leaveRepository.findById(id).orElseThrow(()->new RuntimeException("Leave not found"));
        if(leave.getLeaveStatus().equals(LeaveStatus. PENDING)){
            leave.setLeaveStatus(Le);
        }
        else throw  new RuntimeException("Only Pending Leave can be canceled");
    }*/

    public List<LeaveDTO> GetLeaveDetailsOfEmployee(Long id){
        List<Leave> leaves = leaveRepository.findByEmployee_Id(id);
        List<LeaveDTO> leaveDTOS = new ArrayList<>();
        for(Leave leave :leaves){
            leaveDTOS.add(modelMapper.map(leave,LeaveDTO.class));
        }
        return leaveDTOS;
    }

    @Override
    public void DeleteleaveById(Long id) {
        Leave l = leaveRepository.findById(id).orElseThrow(()->new RuntimeException("Leave not found"));
       /* Optional<Leave> leave=leaveRepository.findById(id);
        if(leave.isPresent()){
            leaveRepository.deleteById(id);
        }
        else throw new RuntimeException("Leave not found");*/
        leaveRepository.deleteById(id);
    }

    public LeaveDTO getLeaveById(Long id){
        Optional<Leave> leave= leaveRepository.findById(id);
        if(leave.isPresent()){
            return modelMapper.map(leave,LeaveDTO.class);
        }
        throw new RuntimeException("Leave Not Found");
    }
}
