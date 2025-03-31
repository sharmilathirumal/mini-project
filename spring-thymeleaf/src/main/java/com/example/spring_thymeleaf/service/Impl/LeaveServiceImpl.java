package com.example.spring_thymeleaf.service.Impl;

import com.example.spring_thymeleaf.dto.LeaveDTO;
import com.example.spring_thymeleaf.entity.Employee;
import com.example.spring_thymeleaf.entity.Leave;
import com.example.spring_thymeleaf.enums.LeaveDuration;
import com.example.spring_thymeleaf.enums.LeaveStatus;
import com.example.spring_thymeleaf.enums.LeaveType;
import com.example.spring_thymeleaf.repository.AttendanceRepository;
import com.example.spring_thymeleaf.repository.EmployeeRepository;
import com.example.spring_thymeleaf.repository.LeaveRepository;
import com.example.spring_thymeleaf.service.LeaveService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class LeaveServiceImpl implements LeaveService {
    @Autowired
    private LeaveRepository leaveRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private AttendanceRepository attendanceRepository;

    @Override
    public void applyForLeave(LeaveDTO leaveDTO) {
        Employee employee = employeeRepository.findById(leaveDTO.getEmployee().getId())
                .orElseThrow(() -> new IllegalStateException("Employee Not Found"));
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        if (leaveRepository.existsByEmployeeIdAndDate(leaveDTO.getEmployee().getId(), leaveDTO.getStartDate()) || leaveRepository.existsByEmployeeIdAndDate(leaveDTO.getEmployee().getId(), leaveDTO.getEndDate())) {
            throw new IllegalStateException("You have recorded leave for the same date, therefore, leave cannot be added.");
        }

        if (leaveDTO.getDuration().equals(LeaveDuration.FULL_DAY)) {
            if (attendanceRepository.existsByEmployeeIdAndDate(leaveDTO.getEmployee().getId(), leaveDTO.getStartDate()) || attendanceRepository.existsByEmployeeIdAndDate(leaveDTO.getEmployee().getId(), leaveDTO.getEndDate())) {
                throw new IllegalStateException("You have recorded attendance for the same date, therefore, leave cannot be added.");
            }
        }

        if (leaveDTO.getStartDate().isAfter(leaveDTO.getEndDate())) {
            throw new IllegalStateException("start date should be less than end date");
        }

        if(leaveDTO.getEndDate().isAfter(leaveDTO.getStartDate()) && leaveDTO.getDuration().equals(LeaveDuration.HALF_DAY)){
            throw new IllegalStateException("You cannot apply for half-day leave when the leave spans multiple days.");
        }
        Leave leave = modelMapper.map(leaveDTO, Leave.class);
        leave.setId(null);
        leave.setLeaveStatus(LeaveStatus.PENDING);
        leave.setEmployee(employee);
        leaveRepository.save(leave);
    }

    @Override
    public LeaveDTO updateLeaveStatus(LeaveDTO leaveDTO) {
        Leave leave = leaveRepository.findById(leaveDTO.getId()).orElseThrow(() -> new IllegalStateException("Leave not found"));
        if (!leave.getLeaveStatus().equals(LeaveStatus.PENDING)) {
            throw new IllegalStateException("Only Pending leave record can be updated");
        }
        leave.setLeaveStatus(leaveDTO.getLeaveStatus());

        leaveRepository.save(leave);
        return modelMapper.map(leave, LeaveDTO.class);
    }

    @Override
    public LeaveDTO updateLeaveRecord(LeaveDTO leaveDTO) {
        Leave leave = leaveRepository.findById(leaveDTO.getId()).orElseThrow(() -> new RuntimeException("Leave not found"));

        if (!Objects.equals(leaveDTO.getLeaveStatus(),LeaveStatus.PENDING)) {
            throw new RuntimeException("Only Pending leave record can be updated");
        }

        if (leaveDTO.getStartDate().isAfter(leaveDTO.getEndDate())) {
            throw new IllegalStateException("start date should be less than end date");
        }

        if (!Objects.equals(leaveDTO.getStartDate(),leave.getStartDate())) {
            if (leaveRepository.existsByEmployeeIdAndDate(leaveDTO.getEmployee().getId(), leaveDTO.getStartDate())) {
                throw new IllegalStateException("You have recorded leave for the start date already, therefore, leave cannot be added.");
            }
            leave.setStartDate(leaveDTO.getStartDate());
        }

        if (!Objects.equals(leaveDTO.getEndDate(),leave.getEndDate())) {
            if (leaveRepository.existsByEmployeeIdAndDate(leaveDTO.getEmployee().getId(), leaveDTO.getEndDate())) {
                throw new IllegalStateException("You have recorded leave for the end date already, therefore, leave cannot be added.");
            }
            leave.setEndDate(leaveDTO.getEndDate());
        }
        if (leaveDTO.getDuration().equals(LeaveDuration.FULL_DAY)) {
            if (attendanceRepository.existsByEmployeeIdAndDate(leaveDTO.getEmployee().getId(), leaveDTO.getStartDate()) || attendanceRepository.existsByEmployeeIdAndDate(leaveDTO.getEmployee().getId(), leaveDTO.getEndDate())) {
                throw new IllegalStateException("You have recorded attendance for the same date, therefore, leave cannot be added.");
            }
        }
        if (!Objects.equals(leaveDTO.getLeaveType(),leave.getLeaveType())){
            leave.setLeaveType(leaveDTO.getLeaveType());
        }
        if (!Objects.equals(leaveDTO.getDuration(),leave.getDuration())){
            leave.setDuration(leaveDTO.getDuration());
        }

        leave.setLeaveStatus(LeaveStatus.PENDING);
        leaveRepository.save(leave);
        return modelMapper.map(leave, LeaveDTO.class);

    }

    @Override
    public List<LeaveDTO> getAllLeaves() {
        List<Leave> leaves = leaveRepository.findAll();
        if (leaves.size() == 0) throw new RuntimeException("Employee does not have leave details");
        List<LeaveDTO> leaveDTOS = new ArrayList<>();
        for (Leave leave : leaves) {
            leaveDTOS.add(modelMapper.map(leave, LeaveDTO.class));
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

    public List<LeaveDTO> getLeaveDetailsOfEmployee(Long id) {
        List<Leave> leaves = leaveRepository.findByEmployee_Id(id);
        List<LeaveDTO> leaveDTOS = new ArrayList<>();
        for (Leave leave : leaves) {
            leaveDTOS.add(modelMapper.map(leave, LeaveDTO.class));
        }
        return leaveDTOS;
    }

    @Override
    public boolean deleteleaveById(Long id) {
        if (leaveRepository.existsById(id)) {
            leaveRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public LeaveDTO getLeaveById(Long id) {
        Optional<Leave> leave = leaveRepository.findById(id);
        if (leave.isPresent()) {
            return modelMapper.map(leave, LeaveDTO.class);
        }
        throw new RuntimeException("Leave Not Found");
    }

    public List<LeaveDTO> getAllPendingLeaveOfEmployees(List<Long> employeeIds){
        List<Leave> leaves = leaveRepository.findAllPendingLeaveByEmployeeIds(employeeIds);
        return leaves.stream().map(leave -> modelMapper.map(leave,LeaveDTO.class))
                .collect(Collectors.toList());
    }
}
