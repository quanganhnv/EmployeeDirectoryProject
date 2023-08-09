package com.Employee_Directory_Project.service;

import com.Employee_Directory_Project.service.dto.Project_MemDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface Project_MemService {

    List<Project_MemDTO> findAllByProject_name(String textSearch);

    List<Project_MemDTO> findAll();

    Project_MemDTO findAllById(Integer id);

    Optional<Project_MemDTO> findOne(Integer id);

    ///basic implement
    Project_MemDTO save(Project_MemDTO project_MemDTO);
    void delete(Integer id);

    Project_MemDTO update(Project_MemDTO project_MemDTO ,Integer id);
}
