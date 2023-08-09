package com.Employee_Directory_Project.service.mapper.impl;

import com.Employee_Directory_Project.entities.Employee;
import com.Employee_Directory_Project.entities.Project;
import com.Employee_Directory_Project.entities.Project_Mem;
import com.Employee_Directory_Project.repository.EmployeeRepository;
import com.Employee_Directory_Project.repository.ProjectRepository;
import com.Employee_Directory_Project.service.dto.Project_MemDTO;
import com.Employee_Directory_Project.service.mapper.Project_MemMapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.springframework.data.repository.util.ClassUtils.ifPresent;

@Component
public class Project_MemMapperImpl implements Project_MemMapper {
    EmployeeRepository employeeRepository ;
    ProjectRepository projectRepository;

    public Project_MemMapperImpl(EmployeeRepository employeeRepository, ProjectRepository projectRepository) {
        this.employeeRepository = employeeRepository;
        this.projectRepository = projectRepository;
    }

    @Override
    public Project_Mem toEntity(Project_MemDTO dto) {
        if (dto == null) {
            return null;
        }
        Project_Mem project_Mem = new Project_Mem();


        project_Mem.setId(dto.getId());
        project_Mem.setOperator(dto.getOperator());
        project_Mem.setStatus(dto.getStatus());
        project_Mem.setDescription(dto.getDescription());
        project_Mem.setStart_day(dto.getStart_day());
        project_Mem.setEnd_day(dto.getEnd_day());
        project_Mem.setEmployee_id(dto.getEmployee_id());
        project_Mem.setProject_id(dto.getProject_id());
        project_Mem.setUpdated_at(dto.getUpdated_at());
        project_Mem.setCreated_at(dto.getCreated_at());

        return project_Mem;
    }

    @Override
    public Project_MemDTO toDto(Project_Mem entity) {
        if (entity == null) {
            return null;
        }

        Project_MemDTO project_MemDTO = new Project_MemDTO();

        project_MemDTO.setId(entity.getId());
        project_MemDTO.setOperator(entity.getOperator());
        project_MemDTO.setStatus(entity.getStatus());
        project_MemDTO.setDescription(entity.getDescription());
        project_MemDTO.setStart_day(entity.getStart_day());
        project_MemDTO.setEnd_day(entity.getEnd_day());
        project_MemDTO.setEmployee_id(entity.getEmployee_id());
        project_MemDTO.setProject_id(entity.getProject_id());
        project_MemDTO.setUpdated_at(entity.getUpdated_at());
        project_MemDTO.setCreated_at(entity.getCreated_at());

        Employee employee = entity.getEmployee();
        if(employee == null){
            Optional<Employee> employeeOptional = employeeRepository.findById(entity.getEmployee_id());
            employeeOptional.ifPresent(value -> project_MemDTO.setEmployee_name(value.getFullName()));
            employeeOptional.ifPresent(value -> project_MemDTO.setEmployee_department(value.getDepartment().getName()));
            employeeOptional.ifPresent(value -> project_MemDTO.setEmployee_identity(value.getIdentity_number()));
        }
        else {
            project_MemDTO.setEmployee_name(employee.getFullName());
            project_MemDTO.setEmployee_identity(employee.getIdentity_number());
            project_MemDTO.setEmployee_department(employee.getDepartment().getName());
        }

        Project project = entity.getProject();
        if(project == null){
            Optional<Project> projectOptional = projectRepository.findById(entity.getProject_id());
            projectOptional.ifPresent(value -> project_MemDTO.setProject_name(value.getName()));
            projectOptional.ifPresent(value -> project_MemDTO.setProject_language(value.getLanguage()));
            projectOptional.ifPresent(value -> project_MemDTO.setProject_framework(value.getFramework()));
        }
        else {
            project_MemDTO.setProject_name(project.getName());
            project_MemDTO.setProject_language(project.getLanguage());
            project_MemDTO.setProject_framework(project.getFramework());
        }


        return project_MemDTO;
    }

    @Override
    public List<Project_Mem> toEntity(List<Project_MemDTO> dtoList) {
        if (dtoList == null) {
            return null;
        }

        List<Project_Mem> list = new ArrayList<>(dtoList.size());
        for (Project_MemDTO project_MemDTO : dtoList) {
            list.add(toEntity(project_MemDTO));
        }

        return list;
    }

    @Override
    public List<Project_MemDTO> toDto(List<Project_Mem> entityList) {
        if (entityList == null) {
            return null;
        }

        List<Project_MemDTO> list = new ArrayList<Project_MemDTO>(entityList.size());
        for (Project_Mem project_Mem : entityList) {
            list.add(toDto(project_Mem));
        }
        return list;
    }



}
