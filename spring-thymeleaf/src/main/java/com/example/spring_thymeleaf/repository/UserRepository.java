package com.example.spring_thymeleaf.repository;

import com.example.spring_thymeleaf.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {
    boolean existsByUserName(String userName);
    boolean existsByEmployeeId(Long id);
    Optional<User> findByUserName(String username);

}
