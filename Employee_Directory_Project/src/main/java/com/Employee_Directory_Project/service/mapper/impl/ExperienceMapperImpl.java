package com.Employee_Directory_Project.service.mapper.impl;

import com.Employee_Directory_Project.entities.Employee;
import com.Employee_Directory_Project.entities.Experience;
import com.Employee_Directory_Project.repository.EmployeeRepository;
import com.Employee_Directory_Project.service.dto.ExperienceDTO;
import com.Employee_Directory_Project.service.mapper.ExperienceMapper;
import com.Employee_Directory_Project.service.mapper.ProjectMapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Component
public class ExperienceMapperImpl implements ExperienceMapper {

    EmployeeRepository employeeRepository ;

    public ExperienceMapperImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public Experience toEntity(ExperienceDTO dto) {
        if (dto == null) {
            return null;
        }
        Experience experience = new Experience();
        experience.setId(dto.getId());
        experience.setName(dto.getName());
        experience.setDescription(dto.getDescription());
        experience.setEnd_day(dto.getEnd_day());
        experience.setStart_day(dto.getStart_day());
        experience.setOperator(dto.getOperator());
        experience.setOs(dto.getOs());
        experience.setFramework(dto.getFramework());
        experience.setLanguage(dto.getLanguage());
        experience.setReference(dto.getReference());
        experience.setTotal_cost(dto.getTotal_cost());
        experience.setTotal_size(dto.getTotal_size());
        experience.setUpdated_at(dto.getUpdated_at());
        experience.setCreated_at(dto.getCreated_at());
        experience.setEmployee_id(dto.getEmployee_id());

        return experience;
    }

    @Override
    public ExperienceDTO toDto(Experience entity) {
        if (entity == null) {
            return null;
        }

        ExperienceDTO experienceDTO = new ExperienceDTO();
        experienceDTO.setId(entity.getId());
        experienceDTO.setName(entity.getName());
        experienceDTO.setDescription(entity.getDescription());
        experienceDTO.setEnd_day(entity.getEnd_day());
        experienceDTO.setStart_day(entity.getStart_day());
        experienceDTO.setOperator(entity.getOperator());
        experienceDTO.setOs(entity.getOs());
        experienceDTO.setFramework(entity.getFramework());
        experienceDTO.setLanguage(entity.getLanguage());
        experienceDTO.setReference(entity.getReference());
        experienceDTO.setTotal_cost(entity.getTotal_cost());
        experienceDTO.setTotal_size(entity.getTotal_size());
        experienceDTO.setUpdated_at(entity.getUpdated_at());
        experienceDTO.setCreated_at(entity.getCreated_at());
        experienceDTO.setEmployee_id(entity.getEmployee_id());

        Employee employee = entity.getEmployee();

        if(employee == null){
            Optional<Employee> employeeOptional = employeeRepository.findById(entity.getEmployee_id());
            employeeOptional.ifPresent(value -> experienceDTO.setEmployeeName(value.getFullName()));
        }
        else {
            experienceDTO.setEmployeeName(employee.getFullName());
        }

        return experienceDTO;
    }

    @Override
    public List<Experience> toEntity(List<ExperienceDTO> dtoList) {
        if (dtoList == null) {
            return null;
        }
        List<Experience> list = new ArrayList<>(dtoList.size());
        for (ExperienceDTO experienceDTO : dtoList) {
            list.add(toEntity(experienceDTO));
        }
        return list;
    }

    @Override
    public List<ExperienceDTO> toDto(List<Experience> entityList) {
        if (entityList == null) {
            return null;
        }
        List<ExperienceDTO> list = new ArrayList<ExperienceDTO>(entityList.size());
        for (Experience experience : entityList) {
            list.add(toDto(experience));
        }
        return list;
    }
}
