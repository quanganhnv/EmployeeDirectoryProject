package com.Employee_Directory_Project.service.impl;

import com.Employee_Directory_Project.entities.Employee;
import com.Employee_Directory_Project.repository.EmployeeRepository;
import com.Employee_Directory_Project.service.EmployeeService;
import com.Employee_Directory_Project.service.dto.EmployeeDTO;
import com.Employee_Directory_Project.service.mapper.EmployeeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    private final EmployeeRepository employeeRepository;

    private final EmployeeMapper employeeMapper;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository, EmployeeMapper employeeMapper) {
        this.employeeRepository = employeeRepository;
        this.employeeMapper = employeeMapper;
    }

    @Override
    public EmployeeDTO save(EmployeeDTO employeeDTO) {
        Employee employee = employeeMapper.toEntity(employeeDTO);
        employee = employeeRepository.save(employee);
        return employeeMapper.toDto(employee);
    }

    @Override
    public Page<EmployeeDTO> findAllByFirstNameOrLastName(String firstName,String lastName , Pageable pageable) {
        return employeeRepository.findAllByFirstNameOrLastNameContainingIgnoreCase(firstName, lastName, pageable).map(employeeMapper::toDto);
    }

    @Override
    public Page<EmployeeDTO> findAll(Pageable pageable) {
        return employeeRepository.findAll(pageable).map(employeeMapper::toDto);
    }

    @Override
    public List<EmployeeDTO> findAll(){
        return employeeMapper.toDto(employeeRepository.findAll());
    }

    @Override
    public Page<EmployeeDTO> findAllByDepartment(Integer department_id, Pageable pageable) {
        return employeeRepository.findAllByDepartment_id(department_id, pageable).map(employeeMapper::toDto);
    }

    @Override
    public Optional<EmployeeDTO> findTopByOrderByIdDesc() {
        return employeeRepository.findTopByOrderByIdDesc().map(employeeMapper::toDto);
    }

    @Override
    public Optional<EmployeeDTO> findOne(Integer id) {
        return employeeRepository.findById(id).map(employeeMapper::toDto);
    }

    @Override
    public void delete(Integer id) {
        employeeRepository.deleteById(id);
    }
}
