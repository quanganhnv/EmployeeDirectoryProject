package com.Employee_Directory_Project.service.impl;

import com.Employee_Directory_Project.entities.Department;
import com.Employee_Directory_Project.repository.DepartmentRepository;
import com.Employee_Directory_Project.service.DepartmentService;
import com.Employee_Directory_Project.service.dto.DepartmentDTO;
import com.Employee_Directory_Project.service.mapper.DepartmentMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentRepository departmentRepository;

    private final DepartmentMapper departmentMapper;

    public DepartmentServiceImpl(DepartmentRepository departmentRepository, DepartmentMapper departmentMapper) {
        this.departmentRepository = departmentRepository;
        this.departmentMapper = departmentMapper;
    }

    @Override
    public DepartmentDTO save(DepartmentDTO departmentDTO) {
        Department department = departmentMapper.toEntity(departmentDTO);
        department = departmentRepository.save(department);
        return departmentMapper.toDto(department);
    }

//    @Override
//    public Page<DepartmentDTO> findAll(String textSearch, Pageable pageable) {
//        return departmentRepository.findAllByNameContainingIgnoreCase(textSearch, pageable).map(departmentMapper::toDto);
//    }
        @Override
    public Page<DepartmentDTO> findAll(Pageable pageable) {
        return departmentRepository.findAll(pageable).map(departmentMapper::toDto);
    }

    @Override
    public Optional<DepartmentDTO> findOne(Integer id) {
        return departmentRepository.findById(id).map(departmentMapper::toDto);
    }

    @Override
    public void delete(Integer id) {
        departmentRepository.deleteById(id);
    }
}
