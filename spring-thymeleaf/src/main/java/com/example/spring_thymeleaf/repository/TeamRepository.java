package com.example.spring_thymeleaf.repository;

import com.example.spring_thymeleaf.entity.Attendance;
import com.example.spring_thymeleaf.entity.Employee;
import com.example.spring_thymeleaf.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

@Repository
public interface TeamRepository extends JpaRepository<Team,Long> {
    boolean existsByName(String name);
    List<Team> findByDepartment_Id(Long id);
    @Query("SELECT t.id FROM Team t WHERE t.manager.id = :managerId")
    List<Long> findTeamIdsByManagerId(@Param("managerId") Long managerId);
}
