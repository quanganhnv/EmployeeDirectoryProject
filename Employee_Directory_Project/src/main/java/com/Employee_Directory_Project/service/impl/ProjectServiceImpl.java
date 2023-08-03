package com.Employee_Directory_Project.service.impl;

import com.Employee_Directory_Project.entities.Project;
import com.Employee_Directory_Project.repository.ProjectRepository;
import com.Employee_Directory_Project.service.ProjectService;
import com.Employee_Directory_Project.service.dto.ProjectDTO;
import com.Employee_Directory_Project.service.mapper.ProjectMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.Id;
import java.util.List;
import java.util.Optional;
@Service
public class ProjectServiceImpl implements ProjectService {
    private final ProjectRepository projectRepository;
    private final ProjectMapper projectMapper;

    public ProjectServiceImpl(ProjectRepository projectRepository, ProjectMapper projectMapper) {
        this.projectRepository = projectRepository;
        this.projectMapper = projectMapper;
    }

    @Override
    public ProjectDTO save(ProjectDTO projectDTO){
        Project project = projectMapper.toEntity(projectDTO);
        project = projectRepository.save(project);
        return projectMapper.toDto(projectRepository.findAllById(project.getId()));
    }

    @Override
    public Page<ProjectDTO> findAllByNameContainingIgnoreCase(String textSearch,Pageable pageable) {
        return projectRepository.findAllByNameContainingIgnoreCase(textSearch,pageable).map(projectMapper::toDto);
    }

    @Override
    public Optional<ProjectDTO> findOne(Integer id) {
        return Optional.empty();
    }

    @Override
    public List<ProjectDTO> findAll(){
        return projectMapper.toDto(projectRepository.findAll());
    }
    @Override
    public void delete(Integer id) {
        projectRepository.deleteById(id);
    }
    @Override
    public ProjectDTO findAllById(Integer id){
        return projectMapper.toDto(projectRepository.findAllById(id));
    }


}
