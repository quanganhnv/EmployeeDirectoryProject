package com.Employee_Directory_Project.service;

import com.Employee_Directory_Project.service.dto.CertificateDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

public interface CertificateService {
    CertificateDTO save(CertificateDTO certificateDTO);

    Page<CertificateDTO> findAll(Integer employee_id, String textSearch, Pageable pageable);

    Optional<CertificateDTO> findOne(Integer employee_id, Integer idCertificate);

    void delete(Integer id);
}
