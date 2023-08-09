package com.Employee_Directory_Project.service;

import com.Employee_Directory_Project.service.dto.ExperienceDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface ExperienceService {
    ExperienceDTO save(ExperienceDTO experienceDTO);

    Page<ExperienceDTO> findAllByNameContainingIgnoreCaseAndEmployee_id(String textSearch,Integer id,Pageable pageable);

    List<ExperienceDTO> findAllByEmployee_id(int id);


    Optional<ExperienceDTO> findOne(Integer id);

    void delete(Integer id);

    ExperienceDTO findAllById(Integer id);

    ExperienceDTO update(ExperienceDTO experienceDTO ,Integer id);
}
