package com.example.spring_thymeleaf.service;

import com.example.spring_thymeleaf.dto.UserDTO;

import java.util.List;

public interface UserService {
     UserDTO createUser(UserDTO userDTO);
     void updatePassword(UserDTO userDTO);
     UserDTO getUserById(Long id);
     List<UserDTO> getAllUsers();
     UserDTO getByUserName(String username);
}
