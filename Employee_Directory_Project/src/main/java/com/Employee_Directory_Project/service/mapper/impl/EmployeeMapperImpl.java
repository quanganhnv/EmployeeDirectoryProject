package com.Employee_Directory_Project.service.mapper.impl;

import com.Employee_Directory_Project.entities.Account;
import com.Employee_Directory_Project.entities.Department;
import com.Employee_Directory_Project.entities.Employee;
import com.Employee_Directory_Project.service.dto.EmployeeDTO;
import com.Employee_Directory_Project.service.mapper.EmployeeMapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class EmployeeMapperImpl implements EmployeeMapper {
    @Override
    public Employee toEntity(EmployeeDTO dto) {
        if (dto == null) {
            return null;
        }

        Employee employee = new Employee();
        employee.setId(dto.getId());
        employee.setFirstName(dto.getFirstName());
        employee.setLastName(dto.getLastName());
        employee.setPhone(dto.getPhone());
        employee.setAddress(dto.getAddress());
        employee.setBirthday(dto.getBirthday());
        employee.setDepartment_id(dto.getDepartment_id());
        employee.setGender(dto.getGender());

        return employee;
    }

    @Override
    public EmployeeDTO toDto(Employee entity) {
        if (entity == null) {
            return null;
        }

        EmployeeDTO employeeDTO = new EmployeeDTO();
        employeeDTO.setId(entity.getId());
        employeeDTO.setFirstName(entity.getFirstName());
        employeeDTO.setLastName(entity.getLastName());
        employeeDTO.setFullName(entity.getFullName());
        employeeDTO.setPhone(entity.getPhone());
        employeeDTO.setAddress(entity.getAddress());
        employeeDTO.setBirthday(entity.getBirthday());
        employeeDTO.setDepartment_id(entity.getDepartment_id());
        employeeDTO.setGender(entity.getGender());
        employeeDTO.setImage_path(entity.getImage_path());

        Department department = entity.getDepartment();
        if (department != null) {
            employeeDTO.setDepartment_name(department.getName());
        }

        String roles = entity.getAccount().getRole_id();
        if (roles != null) {
            employeeDTO.setRole_id(roles);
        }

        String email = entity.getAccount().getEmail();
        if (email != null) {
            employeeDTO.setEmail(email);
        }


        return employeeDTO;
    }

    @Override
    public List<Employee> toEntity(List<EmployeeDTO> dtoList) {
        if (dtoList == null) {
            return null;
        }

        List<Employee> list = new ArrayList<>(dtoList.size());
        for (EmployeeDTO employeeDTO : dtoList) {
            list.add(toEntity(employeeDTO));
        }

        return list;
    }

    @Override
    public List<EmployeeDTO> toDto(List<Employee> entityList) {
        if (entityList == null) {
            return null;
        }

        List<EmployeeDTO> list = new ArrayList<EmployeeDTO>(entityList.size());
        for (Employee employee : entityList) {
            list.add(toDto(employee));
        }

        return list;
    }
}
