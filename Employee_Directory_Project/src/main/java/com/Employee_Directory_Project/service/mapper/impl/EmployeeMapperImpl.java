package com.Employee_Directory_Project.service.mapper.impl;

import com.Employee_Directory_Project.entities.*;
import com.Employee_Directory_Project.repository.ExperienceRepository;
import com.Employee_Directory_Project.repository.Project_MemRepository;
import com.Employee_Directory_Project.repository.SkillRepository;
import com.Employee_Directory_Project.service.dto.EmployeeDTO;
import com.Employee_Directory_Project.service.dto.ExperienceDTO;
import com.Employee_Directory_Project.service.dto.ProjectDTO;
import com.Employee_Directory_Project.service.dto.SkillDTO;
import com.Employee_Directory_Project.service.mapper.*;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class EmployeeMapperImpl implements EmployeeMapper {
    private Project_MemMapper project_MemMapper;
    private ExperienceMapper experienceMapper;
    private SkillMapper skillMapper;
    private Project_MemRepository project_MemRepository;
    private SkillRepository skillRepository;
    private ExperienceRepository experienceRepository;

    public EmployeeMapperImpl(Project_MemMapper project_MemMapper, ExperienceMapper experienceMapper, SkillMapper skillMapper,
                              Project_MemRepository project_MemRepository, SkillRepository skillRepository, ExperienceRepository experienceRepository) {
        this.project_MemMapper = project_MemMapper;
        this.experienceMapper = experienceMapper;
        this.skillMapper = skillMapper;
        this.project_MemRepository = project_MemRepository;
        this.skillRepository = skillRepository;
        this.experienceRepository = experienceRepository;
    }

    @Override
    public Employee toEntity(EmployeeDTO dto) {
        if (dto == null) {
            return null;
        }

        Employee employee = new Employee();
        employee.setId(dto.getId());
        employee.setFirstName(dto.getFirstName());
        employee.setLastName(dto.getLastName());
        employee.setPhone(dto.getPhone());
        employee.setAddress(dto.getAddress());
        employee.setBirthday(dto.getBirthday());
        employee.setDepartment_id(dto.getDepartment_id());
        employee.setGender(dto.getGender());
        employee.setIdentity_number(dto.getIdentity_number());
        employee.setIssued_by(dto.getIssued_by());
        employee.setIssued_on(dto.getIssued_on());
        employee.setImage_path(dto.getImage_path());

        return employee;
    }

    @Override
    public EmployeeDTO toDto(Employee entity) {
        if (entity == null) {
            return null;
        }

        EmployeeDTO employeeDTO = new EmployeeDTO();
        employeeDTO.setId(entity.getId());
        employeeDTO.setFirstName(entity.getFirstName());
        employeeDTO.setLastName(entity.getLastName());
        employeeDTO.setFullName(entity.getFullName());
        employeeDTO.setPhone(entity.getPhone());
        employeeDTO.setAddress(entity.getAddress());
        employeeDTO.setBirthday(entity.getBirthday());
        employeeDTO.setDepartment_id(entity.getDepartment_id());
        employeeDTO.setGender(entity.getGender());
        employeeDTO.setImage_path(entity.getImage_path());
        employeeDTO.setIdentity_number(entity.getIdentity_number());
        employeeDTO.setIssued_by(entity.getIssued_by());
        employeeDTO.setIssued_on(entity.getIssued_on());

        Department department = entity.getDepartment();
        if (department != null) {
            employeeDTO.setDepartment_name(department.getName());
        }

        Account account = entity.getAccount();
        if (account != null) {
            employeeDTO.setRole_id(account.getRole_id());
            employeeDTO.setEmail(account.getEmail());
        }

        if(entity.getProject_Mems() == null){
            List<Project_Mem> project_Mems = project_MemRepository.findAll();
            employeeDTO.setProject_MemDTOs(project_MemMapper.toDto(project_Mems));
        }
        else{
            employeeDTO.setProject_MemDTOs(project_MemMapper.toDto(new ArrayList<>(entity.getProject_Mems())));
        }

        if(entity.getExperiences() == null){
            List<Experience> experience = experienceRepository.findAll();
            employeeDTO.setExperienceDTOs(experienceMapper.toDto(experience));
        }
        else{
            employeeDTO.setExperienceDTOs(experienceMapper.toDto(new ArrayList<>(entity.getExperiences())));
        }

        if(entity.getSkills() == null){
            List<Skill> skill = skillRepository.findAll();
            employeeDTO.setSkillDTOs(skillMapper.toDto(skill));
        }
        else{
            employeeDTO.setSkillDTOs(skillMapper.toDto(new ArrayList<>(entity.getSkills())));
        }


        return employeeDTO;
    }

    @Override
    public List<Employee> toEntity(List<EmployeeDTO> dtoList) {
        if (dtoList == null) {
            return null;
        }

        List<Employee> list = new ArrayList<>(dtoList.size());
        for (EmployeeDTO employeeDTO : dtoList) {
            list.add(toEntity(employeeDTO));
        }

        return list;
    }

    @Override
    public List<EmployeeDTO> toDto(List<Employee> entityList) {
        if (entityList == null) {
            return null;
        }

        List<EmployeeDTO> list = new ArrayList<EmployeeDTO>(entityList.size());
        for (Employee employee : entityList) {
            list.add(toDto(employee));
        }

        return list;
    }
}
