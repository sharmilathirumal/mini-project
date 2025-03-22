package com.example.spring_thymeleaf.service;

import com.example.spring_thymeleaf.dto.TeamDTO;

import java.text.ParseException;
import java.util.List;

public interface TeamService {

    void addTeam(TeamDTO teamDTO) throws ParseException;

    void deleteTeam(Long id);

    TeamDTO updateTeam(TeamDTO teamDTO);

    List<TeamDTO> getAllTeam();

    TeamDTO getTeamById(Long id);
}
