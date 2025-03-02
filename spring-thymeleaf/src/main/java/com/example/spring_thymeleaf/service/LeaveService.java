package com.example.spring_thymeleaf.service;

import com.example.spring_thymeleaf.dto.LeaveDTO;
import com.example.spring_thymeleaf.entity.Leave;
import com.example.spring_thymeleaf.enums.LeaveStatus;
import com.example.spring_thymeleaf.enums.LeaveType;

import java.util.Date;
import java.util.List;

public interface LeaveService {
     LeaveDTO ApplyForLeave(Long emp_id,Date endDate,LeaveType type,Date startDate);
     LeaveDTO UpdateLeaveStatus(LeaveDTO leaveDTO);
     LeaveDTO UpdateLeaveRecord(Long id, LeaveType leaveType, Date startDate, Date endDate);
     List<LeaveDTO> GetAllLeaves();
     //List<LeaveDTO> GetLeavesById(Long id);
     List<LeaveDTO> GetLeaveDetailsOfEmployee(Long id);
    // void CancelLeaveRequest(Long id);
     void  DeleteleaveById(Long id);
}
