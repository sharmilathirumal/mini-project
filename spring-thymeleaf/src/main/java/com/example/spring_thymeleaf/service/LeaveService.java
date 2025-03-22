package com.example.spring_thymeleaf.service;

import com.example.spring_thymeleaf.dto.LeaveDTO;
import com.example.spring_thymeleaf.entity.Leave;
import com.example.spring_thymeleaf.enums.LeaveStatus;
import com.example.spring_thymeleaf.enums.LeaveType;

import java.util.Date;
import java.util.List;

public interface LeaveService {
     void applyForLeave(LeaveDTO leaveDTO);
     LeaveDTO updateLeaveStatus(LeaveDTO leaveDTO);
     LeaveDTO updateLeaveRecord(LeaveDTO leaveDTO);
     List<LeaveDTO> getAllLeaves();
     //List<LeaveDTO> GetLeavesById(Long id);
     List<LeaveDTO> getLeaveDetailsOfEmployee(Long id);
    // void CancelLeaveRequest(Long id);
     boolean  deleteleaveById(Long id);
    LeaveDTO getLeaveById(Long id);
}
