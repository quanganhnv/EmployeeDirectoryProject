package com.Employee_Directory_Project.service;

import com.Employee_Directory_Project.service.dto.SkillDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface SkillService {
    SkillDTO save(SkillDTO skillDTO);

    Page<SkillDTO> findAll(Integer employee_id, Pageable pageable);

    Optional<SkillDTO> findOne(Integer employee_id, Integer idSkill);

    void delete(Integer id);
}
