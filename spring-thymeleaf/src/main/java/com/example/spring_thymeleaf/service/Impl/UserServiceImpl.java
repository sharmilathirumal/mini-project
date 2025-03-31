package com.example.spring_thymeleaf.service.Impl;

import com.example.spring_thymeleaf.dto.UserDTO;
import com.example.spring_thymeleaf.entity.Employee;
import com.example.spring_thymeleaf.entity.User;
import com.example.spring_thymeleaf.repository.EmployeeRepository;
import com.example.spring_thymeleaf.repository.UserRepository;
import com.example.spring_thymeleaf.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public UserDTO createUser(UserDTO userDTO) {
        Employee employee = employeeRepository.findById(userDTO.getEmployee().getId())
                .orElseThrow(() -> new IllegalStateException("Employee Not Found"));

        if(userRepository.existsByEmployeeId(userDTO.getEmployee().getId())){
            throw new IllegalStateException("Employee already have credential");
        }
        if (userRepository.existsByUserName(userDTO.getUserName())) {
            throw new IllegalStateException("Username Already Exists, Please Choose Another One");
        }

        String password = userDTO.getPassword();

        if (password.length() < 15 ||
                !password.matches(".*[!@#$%^&*(),.?\":{}|<>].*") ||
                !password.matches(".*[0-9].*") ||
                !password.matches(".*[A-Za-z].*")) {
            throw new IllegalStateException("The password must consist of a minimum of 15 characters and must contain at least one letter, one numeral, and one special character.");
        }

        if(!Objects.equals(userDTO.getPassword(),userDTO.getConfirmPassword())){
            throw new IllegalStateException("Your password and confirm password doesn't match");
        }
        User user = new User();
        user.setUserName(userDTO.getUserName());
        user.setPassword(passwordEncoder.encode(password)); // Hash password before saving
        //user.setPassword(password);
        user.setEmployee(employee);
        user.setRole(employee.getRole());
        userRepository.save(user);

        return modelMapper.map(user, UserDTO.class);
    }

    @Override
    public void updatePassword(UserDTO userDTO) {
        User user = userRepository.findById(userDTO.getId()).orElseThrow(()-> new IllegalStateException("User Not Found"));
        String newPassword =userDTO.getPassword();
        String confirmPassword = userDTO.getConfirmPassword();
        if(passwordEncoder.matches(newPassword,user.getPassword())){
            throw  new IllegalStateException("The new password must not be the same as the old password.");
        }
        if (newPassword.length() < 15 ||
                !newPassword.matches(".*[!@#$%^&*(),.?\":{}|<>].*") ||
                !newPassword.matches(".*[0-9].*") ||
                !newPassword.matches(".*[A-Za-z].*")) {
            throw new IllegalStateException("The password must consist of a minimum of 15 characters and must contain at least one letter, one numeral, and one special character.");
        }

        if(!Objects.equals(newPassword,confirmPassword)){
            throw new IllegalStateException("Your password and confirm password doesn't match");
        }
        user.setPassword(passwordEncoder.encode(newPassword));

        //user.setPassword(newPassword);
        userRepository.save(user);
    }

    @Override
    public UserDTO getUserById(Long id) {
        User user = userRepository.findById(id).orElseThrow(()->new IllegalStateException("User Not Found"));
        return modelMapper.map(user,UserDTO.class);
    }

    public List<UserDTO> getAllUsers(){
        List<User> users = userRepository.findAll();
        List<UserDTO> userDTOS = new ArrayList<>();
        for(User user:users){
            userDTOS.add(modelMapper.map(user,UserDTO.class));
        }

        return userDTOS;
    }

    @Override
    public UserDTO getByUserName(String username) {
        User user =userRepository.findByUserName(username).orElseThrow(()->new UsernameNotFoundException("User Not Found"));
        return modelMapper.map(user, UserDTO.class);
    }

}
