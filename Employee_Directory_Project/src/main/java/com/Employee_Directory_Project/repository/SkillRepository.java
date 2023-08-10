package com.Employee_Directory_Project.repository;

import com.Employee_Directory_Project.entities.Skill;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SkillRepository extends JpaRepository<Skill, Integer> {
    Page<Skill> findAllByEmployee_id(Integer employee_id, Pageable pageable);
    Optional<Skill> findOneByEmployee_idAndId(Integer employee_id, Integer idSkill);
}
