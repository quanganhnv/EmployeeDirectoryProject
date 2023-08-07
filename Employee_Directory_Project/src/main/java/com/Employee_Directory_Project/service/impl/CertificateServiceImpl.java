package com.Employee_Directory_Project.service.impl;

import com.Employee_Directory_Project.entities.Certificate;
import com.Employee_Directory_Project.repository.CertificateRepository;
import com.Employee_Directory_Project.service.CertificateService;
import com.Employee_Directory_Project.service.dto.CertificateDTO;
import com.Employee_Directory_Project.service.mapper.CertificateMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CertificateServiceImpl implements CertificateService {

    private final CertificateRepository certificateRepository;

    private final CertificateMapper certificateMapper;

    public CertificateServiceImpl(CertificateRepository certificateRepository, CertificateMapper certificateMapper) {
        this.certificateRepository = certificateRepository;
        this.certificateMapper = certificateMapper;
    }

    @Override
    public CertificateDTO save(CertificateDTO certificateDTO) {
        Certificate certificate = certificateMapper.toEntity(certificateDTO);
        certificate = certificateRepository.save(certificate);
        return certificateMapper.toDto(certificate);
    }

    @Override
    public Page<CertificateDTO> findAll(Integer employee_id, String textSearch, Pageable pageable) {
        return certificateRepository.findAllByEmployee_idAndNameContainingIgnoreCase(employee_id, textSearch, pageable).map(certificateMapper::toDto);
    }

    @Override
    public Optional<CertificateDTO> findOne(Integer employee_id, Integer idCertificate) {
        return certificateRepository.findOneByEmployee_idAndId(employee_id, idCertificate).map(certificateMapper::toDto);
    }

    @Override
    public void delete(Integer id) {
        certificateRepository.deleteById(id);
    }
}
