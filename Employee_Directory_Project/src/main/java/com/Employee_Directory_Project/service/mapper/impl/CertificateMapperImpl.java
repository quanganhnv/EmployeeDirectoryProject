package com.Employee_Directory_Project.service.mapper.impl;

import com.Employee_Directory_Project.entities.Certificate;
import com.Employee_Directory_Project.entities.Employee;
import com.Employee_Directory_Project.service.dto.CertificateDTO;
import com.Employee_Directory_Project.service.mapper.CertificateMapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CertificateMapperImpl implements CertificateMapper {
    @Override
    public Certificate toEntity(CertificateDTO dto) {
        if (dto == null) {
            return null;
        }
        Certificate certificate = new Certificate();
        certificate.setId(dto.getId());
        certificate.setName(dto.getName());
        certificate.setEmployee_id(dto.getEmployee_id());
        certificate.setEffective_day(dto.getEffective_day());
        certificate.setExpiry_date(dto.getExpiry_date());
        certificate.setAuthorization(dto.getAuthorization());
        certificate.setDescription(dto.getDescription());

        return certificate;
    }

    @Override
    public CertificateDTO toDto(Certificate entity) {
        if (entity == null) {
            return null;
        }

        CertificateDTO certificateDTO = new CertificateDTO();
        certificateDTO.setId(entity.getId());
        certificateDTO.setName(entity.getName());
        certificateDTO.setEmployee_id(entity.getEmployee_id());
        certificateDTO.setEffective_day(entity.getEffective_day());
        certificateDTO.setExpiry_date(entity.getExpiry_date());
        certificateDTO.setAuthorization(entity.getAuthorization());
        certificateDTO.setDescription(entity.getDescription());

        Employee employee = entity.getEmployee();
        if(employee != null){
            certificateDTO.setEmployee_name(employee.getFullName());
        }

        return certificateDTO;
    }

    @Override
    public List<Certificate> toEntity(List<CertificateDTO> dtoList) {
        if (dtoList == null) {
            return null;
        }

        List<Certificate> list = new ArrayList<>(dtoList.size());
        for (CertificateDTO departmentDTO : dtoList) {
            list.add(toEntity(departmentDTO));
        }

        return list;
    }

    @Override
    public List<CertificateDTO> toDto(List<Certificate> entityList) {
        if (entityList == null) {
            return null;
        }

        List<CertificateDTO> list = new ArrayList<CertificateDTO>(entityList.size());
        for (Certificate certificate : entityList) {
            list.add(toDto(certificate));
        }

        return list;
    }
}
