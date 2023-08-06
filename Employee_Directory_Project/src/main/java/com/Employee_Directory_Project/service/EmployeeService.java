package com.Employee_Directory_Project.service;

import com.Employee_Directory_Project.service.dto.EmployeeDTO;
import org.springframework.data.domain.*;

import java.util.Optional;

public interface EmployeeService {

    EmployeeDTO save(EmployeeDTO notifyDTO);

    Page<EmployeeDTO> findAllByFirstNameOrLastName(String firstName, String lastName, Pageable pageable);

    Page<EmployeeDTO> findAll(Pageable pageable);

    Page<EmployeeDTO> findAllByDepartment(Integer department_id, Pageable pageable);

    Optional<EmployeeDTO> findTopByOrderByIdDesc ();

    Optional<EmployeeDTO> findOne(Integer id);

    void delete(Integer id);
}