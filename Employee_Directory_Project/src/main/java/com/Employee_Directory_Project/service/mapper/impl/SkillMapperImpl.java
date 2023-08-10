package com.Employee_Directory_Project.service.mapper.impl;

import com.Employee_Directory_Project.entities.Employee;
import com.Employee_Directory_Project.entities.Skill;
import com.Employee_Directory_Project.service.dto.SkillDTO;
import com.Employee_Directory_Project.service.mapper.SkillMapper;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class SkillMapperImpl implements SkillMapper {

    @Override
    public Skill toEntity(SkillDTO dto) {
        if (dto == null) {
            return null;
        }
        Skill skill = new Skill();
        skill.setId(dto.getId());
        skill.setName(dto.getName());
        skill.setEmployee_id(dto.getEmployee_id());
        skill.setLevel(dto.getLevel());
        skill.setDescription(dto.getDescription());
        skill.setYears_of_experience(dto.getYears_of_experience());

        return skill;
    }

    @Override
    public SkillDTO toDto(Skill entity) {
        if (entity == null) {
            return null;
        }

        SkillDTO skillDTO = new SkillDTO();
        skillDTO.setId(entity.getId());
        skillDTO.setName(entity.getName());
        skillDTO.setLevel(entity.getLevel());
        skillDTO.setEmployee_id(entity.getEmployee_id());
        skillDTO.setDescription(entity.getDescription());
        skillDTO.setYears_of_experience(entity.getYears_of_experience());

        Employee employee = entity.getEmployee();
        if(employee != null){
            skillDTO.setEmployee_name(employee.getFullName());
        }

        return skillDTO;
    }

    @Override
    public List<Skill> toEntity(List<SkillDTO> dtoList) {
        if (dtoList == null) {
            return null;
        }

        List<Skill> list = new ArrayList<>(dtoList.size());
        for (SkillDTO skillDTO : dtoList) {
            list.add(toEntity(skillDTO));
        }

        return list;
    }

    @Override
    public List<SkillDTO> toDto(List<Skill> entityList) {
        if (entityList == null) {
            return null;
        }

        List<SkillDTO> list = new ArrayList<SkillDTO>(entityList.size());
        for (Skill skill : entityList) {
            list.add(toDto(skill));
        }
        return list;
    }
}
