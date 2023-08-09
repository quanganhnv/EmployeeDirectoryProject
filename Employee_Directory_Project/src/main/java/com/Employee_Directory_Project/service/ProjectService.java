package com.Employee_Directory_Project.service;


import com.Employee_Directory_Project.entities.Project;
import com.Employee_Directory_Project.service.dto.ProjectDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
public interface ProjectService {
    ProjectDTO save(ProjectDTO projectDTO);

    //  Page<ProjectDTO> findAll(String textSearch, Pageable pageable);

    Page<ProjectDTO> findAllByNameContainingIgnoreCase( String textSearch,Pageable pageable);

    List<ProjectDTO> findAll();

    Optional<ProjectDTO> findOne(Integer id);

    void delete(Integer id);

    ProjectDTO findAllById(Integer id);

    ProjectDTO update(ProjectDTO projectDTO ,Integer id);
}
