package com.Employee_Directory_Project.repository;

import com.Employee_Directory_Project.entities.Project;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.DoubleStream;

@Repository
public interface ProjectRepository extends JpaRepository <Project, Integer> , JpaSpecificationExecutor<Project> {
    Optional<Project> findOneByName(String name);

    Page<Project> findAllByNameContainingIgnoreCase(String textSearchName, Pageable pageable);


    List<Project> findAll();

    Project findAllById(Integer Id);

}


