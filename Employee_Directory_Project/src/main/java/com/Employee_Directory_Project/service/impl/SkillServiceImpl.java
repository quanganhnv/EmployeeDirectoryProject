package com.Employee_Directory_Project.service.impl;

import com.Employee_Directory_Project.entities.Skill;
import com.Employee_Directory_Project.repository.SkillRepository;
import com.Employee_Directory_Project.service.SkillService;
import com.Employee_Directory_Project.service.dto.SkillDTO;
import com.Employee_Directory_Project.service.mapper.SkillMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SkillServiceImpl implements SkillService {

    private final SkillRepository skillRepository;

    private final SkillMapper skillMapper;

    public SkillServiceImpl(SkillRepository skillRepository, SkillMapper skillMapper) {
        this.skillRepository = skillRepository;
        this.skillMapper = skillMapper;
    }

    @Override
    public SkillDTO save(SkillDTO skillDTO) {
        Skill skill = skillMapper.toEntity(skillDTO);
        skill = skillRepository.save(skill);
        return skillMapper.toDto(skill);
    }

    @Override
    public Page<SkillDTO> findAll(Integer employee_id, Pageable pageable) {
        return skillRepository.findAllByEmployee_id(employee_id, pageable).map(skillMapper::toDto);
    }

    @Override
    public Optional<SkillDTO> findOne(Integer employee_id, Integer idSkill) {
        return skillRepository.findOneByEmployee_idAndId(employee_id, idSkill).map(skillMapper::toDto);
    }

    @Override
    public void delete(Integer id) {
        skillRepository.deleteById(id);
    }
}
