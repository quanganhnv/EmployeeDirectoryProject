package com.Employee_Directory_Project.service.mapper.impl;

import com.Employee_Directory_Project.entities.Project;
import com.Employee_Directory_Project.entities.Employee;
import com.Employee_Directory_Project.service.EmployeeService;
import com.Employee_Directory_Project.service.dto.ProjectDTO;
import com.Employee_Directory_Project.service.mapper.ProjectMapper;
import com.Employee_Directory_Project.service.mapper.ProjectMapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ProjectMapperImpl implements ProjectMapper {

    @Override
    public Project toEntity(ProjectDTO dto) {
        if (dto == null) {
            return null;
        }
        Project project = new Project();
        project.setId(dto.getId());
        project.setName(dto.getName());
        project.setDescription(dto.getDescription());
        project.setEnd_day(dto.getEnd_day());
        project.setStart_day(dto.getStart_day());
        project.setOperator(dto.getOperator());
        project.setOs(dto.getOs());
        project.setFramework(dto.getFramework());
        project.setLanguage(dto.getLanguage());
        project.setReference(dto.getReference());
        project.setTotal_cost(dto.getTotal_cost());
        project.setTotal_size(dto.getTotal_size());
        project.setPm_id(dto.getPm_id());

        return project;
    }

    @Override
    public ProjectDTO toDto(Project entity) {
        if (entity == null) {
            return null;
        }

        ProjectDTO projectDTO = new ProjectDTO();

        projectDTO.setId(entity.getId());
        projectDTO.setName(entity.getName());
        projectDTO.setDescription(entity.getDescription());
        projectDTO.setEnd_day(entity.getEnd_day());
        projectDTO.setStart_day(entity.getStart_day());
        projectDTO.setOperator(entity.getOperator());
        projectDTO.setOs(entity.getOs());
        projectDTO.setFramework(entity.getFramework());
        projectDTO.setLanguage(entity.getLanguage());
        projectDTO.setReference(entity.getReference());
        projectDTO.setTotal_cost(entity.getTotal_cost());
        projectDTO.setTotal_size(entity.getTotal_size());
        projectDTO.setPm_id(entity.getPm_id());

        Employee employee = entity.getEmployee();
        if(employee != null){
            projectDTO.setPm_name(employee.getFullname());
        }

        return projectDTO;
    }

    @Override
    public List<Project> toEntity(List<ProjectDTO> dtoList) {
        if (dtoList == null) {
            return null;
        }

        List<Project> list = new ArrayList<>(dtoList.size());
        for (ProjectDTO projectDTO : dtoList) {
            list.add(toEntity(projectDTO));
        }

        return list;
    }

    @Override
    public List<ProjectDTO> toDto(List<Project> entityList) {
        if (entityList == null) {
            return null;
        }

        List<ProjectDTO> list = new ArrayList<ProjectDTO>(entityList.size());
        for (Project project : entityList) {
            list.add(toDto(project));
        }
        return list;
    }
}
