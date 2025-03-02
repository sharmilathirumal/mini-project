package com.example.spring_thymeleaf.repository;

import com.example.spring_thymeleaf.dto.EmployeeDTO;
import com.example.spring_thymeleaf.entity.Department;
import com.example.spring_thymeleaf.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee,Long> {
    public List<Employee> findByDepartment_Id(Long id);

    /*@Query("SELECT new com.example.spring_thymeleaf.dto.EmployeeDTO("+
            "e.id,e.firstName,e.lastName,e.email,e.hiredDate,d.departmentName)"+
            "FROM Employee e Join Department d on e.department.id = d.id")
    List<EmployeeDTO> findAllEmployeeWithDepartment();*/
}
