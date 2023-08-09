package com.Employee_Directory_Project.service.impl;

import com.Employee_Directory_Project.entities.Project;
import com.Employee_Directory_Project.entities.Project_Mem;
import com.Employee_Directory_Project.repository.ProjectRepository;
import com.Employee_Directory_Project.repository.Project_MemRepository;
import com.Employee_Directory_Project.service.Project_MemService;
import com.Employee_Directory_Project.service.dto.Project_MemDTO;
import com.Employee_Directory_Project.service.mapper.Project_MemMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class Project_MemServiceImpl implements Project_MemService {
    private final Project_MemRepository project_MemRepository;
    private final Project_MemMapper project_MemMapper;
    private final ProjectRepository projectRepository;


    public Project_MemServiceImpl(Project_MemRepository project_MemRepository, Project_MemMapper project_MemMapper, ProjectRepository projectRepository) {
        this.project_MemRepository = project_MemRepository;
        this.project_MemMapper = project_MemMapper;
        this.projectRepository = projectRepository;
    }

    @Override
    public Project_MemDTO save(Project_MemDTO project_MemDTO){
        Project_Mem project_Mem = project_MemMapper.toEntity(project_MemDTO);
        project_Mem.setCreated_at(ZonedDateTime.now());
        project_Mem = project_MemRepository.save(project_Mem);
        return project_MemMapper.toDto(project_Mem);
    }

    @Override
    public List<Project_MemDTO> findAllByProject_name(String textSearch) {
        List<Project_MemDTO> allFromDB = project_MemMapper.toDto(project_MemRepository.findAll());
        List<Project_MemDTO> resultList = new ArrayList<>();
        for (Project_MemDTO item: allFromDB) {
            if(item.getProject_name().toLowerCase().contains(textSearch.toLowerCase())) resultList.add(item);
        }
        return resultList;
    }

    @Override
    public Optional<Project_MemDTO> findOne(Integer id) {
        return Optional.empty();
    }

    @Override
    public List<Project_MemDTO> findAll(){
        return project_MemMapper.toDto(project_MemRepository.findAll());
    }

    @Override
    public void delete(Integer id) {
        project_MemRepository.deleteById(id);
    }


    @Override
    public Project_MemDTO findAllById(Integer id){
        return project_MemMapper.toDto(project_MemRepository.findAllById(id));
    }

    @Override
    public Project_MemDTO update(Project_MemDTO project_MemDTO , Integer id){
        //tim doi tuongDB
        Optional<Project_Mem> fromDB = project_MemRepository.findById(id);
        fromDB.get().setId(project_MemDTO.getId());
        fromDB.get().setOperator(project_MemDTO.getOperator());
        fromDB.get().setStatus(project_MemDTO.getStatus());
        fromDB.get().setDescription(project_MemDTO.getDescription());
        fromDB.get().setStart_day(project_MemDTO.getStart_day());
        fromDB.get().setEnd_day(project_MemDTO.getEnd_day());
        fromDB.get().setUpdated_at(ZonedDateTime.now());
        fromDB.get().setEmployee_id(project_MemDTO.getEmployee_id());
        Project_Mem project_Mem = project_MemRepository.save(fromDB.get());
        return project_MemMapper.toDto(project_Mem);
    }

}
