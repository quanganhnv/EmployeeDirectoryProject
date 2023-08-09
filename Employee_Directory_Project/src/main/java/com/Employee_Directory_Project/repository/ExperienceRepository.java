package com.Employee_Directory_Project.repository;

import com.Employee_Directory_Project.entities.Experience;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface ExperienceRepository extends JpaRepository<Experience, Integer> {
    Page<Experience> findAllByNameContainingIgnoreCaseAndEmployee_id(String textSearchName,Integer Id, Pageable pageable);

    List<Experience> findAllByEmployee_id(Integer Id);

    Experience findAllById(Integer Id);

}
