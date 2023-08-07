package com.Employee_Directory_Project.repository;

import com.Employee_Directory_Project.entities.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
//    Optional<Employee> findOneByEmailIgnoreCase(String email);

    Page<Employee> findAllByFirstNameOrLastNameContainingIgnoreCase(String firstName, String lastName, Pageable pageable);

    Page<Employee> findAllByDepartment_id(Integer department_id, Pageable pageable);

    Optional<Employee> findTopByOrderByIdDesc();
}
