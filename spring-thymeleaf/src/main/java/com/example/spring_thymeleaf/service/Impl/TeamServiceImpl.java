package com.example.spring_thymeleaf.service.Impl;

import com.example.spring_thymeleaf.dto.DepartmentDTO;
import com.example.spring_thymeleaf.dto.EmployeeDTO;
import com.example.spring_thymeleaf.dto.TeamDTO;
import com.example.spring_thymeleaf.entity.Department;
import com.example.spring_thymeleaf.entity.Employee;
import com.example.spring_thymeleaf.entity.Team;
import com.example.spring_thymeleaf.enums.EmployeeDesignation;
import com.example.spring_thymeleaf.enums.Role;
import com.example.spring_thymeleaf.enums.TeamStatus;
import com.example.spring_thymeleaf.repository.DepartmentRepository;
import com.example.spring_thymeleaf.repository.EmployeeRepository;
import com.example.spring_thymeleaf.repository.TeamRepository;
import com.example.spring_thymeleaf.service.TeamService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class TeamServiceImpl implements TeamService {

    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private DepartmentRepository departmentRepository;
    @Autowired
    private TeamRepository teamRepository;
    @Autowired
    private ModelMapper modelMapper;


    @Override
    public void addTeam(TeamDTO teamDTO) throws ParseException {
        //  Optional<Employee> emp = employeeRepository.findById(teamDTO.getManagerId());
        Employee employee = employeeRepository.findById(teamDTO.getManager().getId()).orElseThrow(() -> new IllegalStateException("Manager Not Found"));
        if (!employee.getRole().equals(Role.MANAGER)) {
            throw new IllegalStateException("Only Manager Can be added...");
        }
        if(teamRepository.existsByName(teamDTO.getName())){
            throw new IllegalStateException("Team Name Already Exists,Add different Name");
        }
        Department department = departmentRepository.findById(teamDTO.getDepartment().getId()).orElseThrow(() -> new IllegalStateException("Department Not Found"));
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date today = sdf.parse(sdf.format(new Date())); // Remove time part

        if (teamDTO.getCreatedDate().isAfter(LocalDate.now())) {
            throw new IllegalStateException("Create Date Should Not be a Future Date");
        }

      /*  if(teamDTO.getDepartment().equals("Select Department")){
            throw new IllegalStateException("Please select Department");
        }

        if(teamDTO.getManager().equals("Select Manager")){
            throw new IllegalStateException("Please select a Manager");
        }*/

        Team team = modelMapper.map(teamDTO, Team.class);
        team.setId(null);
        team.setManager(employee);
        team.setDepartment(department);
        teamRepository.save(team);
    }

    @Override
    public void deleteTeam(Long id) {
        Team team = teamRepository.findById(id).orElseThrow(() -> new IllegalStateException("Team Not Found"));
        if(team.getStatus().equals(TeamStatus.ACTIVE)){
            throw  new IllegalStateException("Team is still active, can't able to delete");
        }
        List<Employee> employees = employeeRepository.findByTeam_Id(id);
        if (!employees.isEmpty()) {
            throw new IllegalStateException("Cannot delete team. Employees are still assigned to this team.");
        }
        teamRepository.deleteById(id);

    }

    @Override
    public TeamDTO updateTeam(TeamDTO teamDTO) {

        Team team = teamRepository.findById(teamDTO.getId()).orElseThrow(() -> new IllegalStateException("Team Not Found"));
        if (!Objects.equals(team.getManager().getId(), teamDTO.getManager().getId())) {
            Employee employee = employeeRepository.findById(teamDTO.getManager().getId()).orElseThrow(() -> new IllegalStateException("Manager Not Found"));
            if (employee.getRole() != Role.MANAGER) {
                throw new IllegalStateException("Only Manager Can be added...");
            }
            team.setManager(employee);
        }

        if(!Objects.equals(teamDTO.getCreatedDate(),team.getCreatedDate())){
            if(teamDTO.getCreatedDate().isAfter(LocalDate.now())){
                throw new IllegalStateException("Create date should not be future date");
            }
            team.setCreatedDate(teamDTO.getCreatedDate());
        }

        if(Objects.equals(teamDTO.getStatus(), TeamStatus.ARCHIVED)||Objects.equals(teamDTO.getStatus(),TeamStatus.INACTIVE)){
            List<Employee> employees = employeeRepository.findByTeam_Id(teamDTO.getId());
            if (!employees.isEmpty()) {
                throw new IllegalStateException("Cannot Change Status to"+" "+teamDTO.getStatus()+". Employees are still assigned to this team.");
            }
        }

        if (!Objects.equals(team.getDepartment().getId(), teamDTO.getDepartment().getId())) {
            Department department = departmentRepository.findById(teamDTO.getDepartment().getId()).orElseThrow(() -> new IllegalStateException("Department Not Found"));
            team.setDepartment(department);
        }
        if (!Objects.equals(team.getName(), teamDTO.getName())) {
            if(teamRepository.existsByName(teamDTO.getName())){
                throw new IllegalStateException("Team Name Already Exists,Add different Name");
            }
            team.setName(teamDTO.getName());
        }

        if (!Objects.equals(team.getStatus(), teamDTO.getStatus())) {
            team.setStatus(teamDTO.getStatus());
        }

        if (!Objects.equals(team.getDescription(), teamDTO.getDescription())) {
            team.setDescription(teamDTO.getDescription());
        }
        teamRepository.save(team);
        return modelMapper.map(team, TeamDTO.class);
    }


    @Override
    public List<TeamDTO> getAllTeam() {
        List<Team> teams = teamRepository.findAll();

        return teams.stream().map(team -> {
            TeamDTO teamDTO = modelMapper.map(team, TeamDTO.class);

            // Let ModelMapper handle EmployeeDTO and DepartmentDTO mapping automatically
            if (team.getManager() != null) {
                teamDTO.setManager(modelMapper.map(team.getManager(), EmployeeDTO.class));
            }
            if (team.getDepartment() != null) {
                teamDTO.setDepartment(modelMapper.map(team.getDepartment(), DepartmentDTO.class));
            }

            return teamDTO;
        }).collect(Collectors.toList());
    }

    public TeamDTO getTeamById(Long id) {
        Team team = teamRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Team Not Found"));

        TeamDTO teamDTO = modelMapper.map(team, TeamDTO.class);

        // Let ModelMapper handle EmployeeDTO and DepartmentDTO mapping automatically
        if (team.getManager() != null) {
            teamDTO.setManager(modelMapper.map(team.getManager(), EmployeeDTO.class));
        }
        if (team.getDepartment() != null) {
            teamDTO.setDepartment(modelMapper.map(team.getDepartment(), DepartmentDTO.class));
        }

        return teamDTO;
    }

    public List<Long> getTeamByManager(Long id){
       return teamRepository.findTeamIdsByManagerId(id);
    }
}
