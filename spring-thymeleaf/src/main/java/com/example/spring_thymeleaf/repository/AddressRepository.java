package com.example.spring_thymeleaf.repository;

import com.example.spring_thymeleaf.entity.Address;
import com.example.spring_thymeleaf.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address,Long> {
    //Address findByEmployee_Id(Long id);
}
