package com.Employee_Directory_Project.service.impl;

import com.Employee_Directory_Project.entities.Experience;
import com.Employee_Directory_Project.repository.ExperienceRepository;
import com.Employee_Directory_Project.service.ExperienceService;
import com.Employee_Directory_Project.service.dto.ExperienceDTO;
import com.Employee_Directory_Project.service.mapper.ExperienceMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ExperienceServiceImpl implements ExperienceService {
    private final ExperienceRepository experienceRepository;
    private final ExperienceMapper experienceMapper;

    public ExperienceServiceImpl(ExperienceRepository experienceRepository, ExperienceMapper experienceMapper) {
        this.experienceRepository = experienceRepository;
        this.experienceMapper = experienceMapper;
    }

    @Override
    public ExperienceDTO save(ExperienceDTO experienceDTO){
        Experience experience = experienceMapper.toEntity(experienceDTO);
        experience.setCreated_at(ZonedDateTime.now());
        experience = experienceRepository.save(experience);
        return experienceMapper.toDto(experience);
    }

    @Override
    public Page<ExperienceDTO> findAllByNameContainingIgnoreCaseAndEmployee_id(String textSearch,Integer id, Pageable pageable) {
        return experienceRepository.findAllByNameContainingIgnoreCaseAndEmployee_id(textSearch,id,pageable).map(experienceMapper::toDto);
    }

    @Override
    public Optional<ExperienceDTO> findOne(Integer id) {
        return Optional.empty();
    }

    @Override
    public List<ExperienceDTO> findAllByEmployee_id(int id){
        return experienceMapper.toDto(experienceRepository.findAllByEmployee_id(id));
    }
    @Override
    public void delete(Integer id) {
        experienceRepository.deleteById(id);
    }

    @Override
    public ExperienceDTO findAllById(Integer id){
        return experienceMapper.toDto(experienceRepository.findAllById(id));
    }

    @Override
    public ExperienceDTO update(ExperienceDTO experienceDTO , Integer id){
        //tim doi tuongDB
        Optional<Experience> fromDB = experienceRepository.findById(id);
        fromDB.get().setId(experienceDTO.getId());
        fromDB.get().setName(experienceDTO.getName());
        fromDB.get().setDescription(experienceDTO.getDescription());
        fromDB.get().setEnd_day(experienceDTO.getEnd_day());
        fromDB.get().setStart_day(experienceDTO.getStart_day());
        fromDB.get().setOperator(experienceDTO.getOperator());
        fromDB.get().setOs(experienceDTO.getOs());
        fromDB.get().setFramework(experienceDTO.getFramework());
        fromDB.get().setLanguage(experienceDTO.getLanguage());
        fromDB.get().setReference(experienceDTO.getReference());
        fromDB.get().setTotal_cost(experienceDTO.getTotal_cost());
        fromDB.get().setTotal_size(experienceDTO.getTotal_size());
        fromDB.get().setEmployee_id(experienceDTO.getEmployee_id());
        fromDB.get().setUpdated_at(ZonedDateTime.now());
        Experience experience = experienceRepository.save(fromDB.get());
        return experienceMapper.toDto(experience);
    }
    
}
