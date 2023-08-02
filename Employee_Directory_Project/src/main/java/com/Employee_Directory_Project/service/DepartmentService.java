package com.Employee_Directory_Project.service;

import com.Employee_Directory_Project.service.dto.DepartmentDTO;
import com.Employee_Directory_Project.service.dto.EmployeeDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

public interface DepartmentService {
    DepartmentDTO save(DepartmentDTO notifyDTO);

  //  Page<DepartmentDTO> findAll(String textSearch, Pageable pageable);

    Page<DepartmentDTO> findAll(Pageable pageable);

    Optional<DepartmentDTO> findOne(Integer id);

    void delete(Integer id);
}
