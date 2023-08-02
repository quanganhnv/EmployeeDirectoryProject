package com.Employee_Directory_Project.repository;

import com.Employee_Directory_Project.entities.Department;
import com.Employee_Directory_Project.entities.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Integer> {
    //Page<Department> findAllByNameContainingIgnoreCase(String textSearchName, Pageable pageable);
    Page<Department> findAll(Pageable pageable);
}
