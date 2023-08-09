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
import java.time.ZonedDateTime;
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
        project.setCreated_at(ZonedDateTime.now());
        project = projectRepository.save(project);
        return projectMapper.toDto(project);
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

    @Override
    public ProjectDTO update(ProjectDTO projectDTO , Integer id){
        //tim doi tuongDB
        Optional<Project> fromDB = projectRepository.findById(id);
        fromDB.get().setId(projectDTO.getId());
        fromDB.get().setName(projectDTO.getName());
        fromDB.get().setDescription(projectDTO.getDescription());
        fromDB.get().setEnd_day(projectDTO.getEnd_day());
        fromDB.get().setStart_day(projectDTO.getStart_day());
        fromDB.get().setOperator(projectDTO.getOperator());
        fromDB.get().setOs(projectDTO.getOs());
        fromDB.get().setFramework(projectDTO.getFramework());
        fromDB.get().setLanguage(projectDTO.getLanguage());
        fromDB.get().setReference(projectDTO.getReference());
        fromDB.get().setTotal_cost(projectDTO.getTotal_cost());
        fromDB.get().setTotal_size(projectDTO.getTotal_size());
        fromDB.get().setPm_id(projectDTO.getManager_id());
        fromDB.get().setUpdated_at(ZonedDateTime.now());
       Project project = projectRepository.save(fromDB.get());
        return projectMapper.toDto(project);
    }
}
