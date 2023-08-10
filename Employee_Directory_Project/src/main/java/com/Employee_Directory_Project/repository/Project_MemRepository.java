package com.Employee_Directory_Project.repository;

import com.Employee_Directory_Project.entities.Project_Mem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.yaml.snakeyaml.events.Event;

import java.util.List;
import java.util.Optional;

public interface Project_MemRepository extends JpaRepository<Project_Mem,Integer> {
    Page<Project_Mem> findAllByProject_id(Integer id, Pageable pageable);

    List<Project_Mem> findAll();

    Project_Mem findAllById(Integer Id);
}
