package com.Employee_Directory_Project.repository;

import com.Employee_Directory_Project.entities.Certificate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CertificateRepository extends JpaRepository<Certificate, Integer> {
    Page<Certificate> findAllByEmployee_idAndNameContainingIgnoreCase(Integer employee_id, String textSearch, Pageable pageable);

    Optional<Certificate> findOneByEmployee_idAndId(Integer employee_id, Integer idCertificate);
}
