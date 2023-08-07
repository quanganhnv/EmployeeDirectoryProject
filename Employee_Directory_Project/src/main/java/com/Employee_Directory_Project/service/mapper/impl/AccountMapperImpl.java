package com.Employee_Directory_Project.service.mapper.impl;

import com.Employee_Directory_Project.entities.Account;
import com.Employee_Directory_Project.entities.Employee;
import com.Employee_Directory_Project.service.dto.AccountDTO;
import com.Employee_Directory_Project.service.mapper.AccountMapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class AccountMapperImpl implements AccountMapper {
    @Override
    public Account toEntity(AccountDTO dto) {
        if (dto == null) {
            return null;
        }

        Account account = new Account();
        account.setId(dto.getId());
        account.setEmployee_id(dto.getEmployee_id());
        account.setAvatar_path(dto.getAvatar_path());
        account.setEmail(dto.getEmail());
        account.setUsername(dto.getUsername());
        account.setPassword(dto.getPassword());
        account.setRole_id(dto.getRole_id());
        account.setStatus(dto.getStatus());

        return account;
    }

    @Override
    public AccountDTO toDto(Account entity) {
        if (entity == null) {
            return null;
        }

        AccountDTO accountDTO = new AccountDTO();
        accountDTO.setId(entity.getId());
        accountDTO.setEmployee_id(entity.getEmployee_id());
        accountDTO.setAvatar_path(entity.getAvatar_path());
        accountDTO.setEmail(entity.getEmail());
        accountDTO.setUsername(entity.getUsername());
        accountDTO.setPassword(entity.getPassword());
        accountDTO.setRole_id(entity.getRole_id());
        accountDTO.setStatus(entity.getStatus());

        Employee employee = entity.getEmployee();
        if(employee != null){
            accountDTO.setEmployee_name(employee.getFullName());
        }

        return accountDTO;
    }

    @Override
    public List<Account> toEntity(List<AccountDTO> dtoList) {
        if (dtoList == null) {
            return null;
        }

        List<Account> list = new ArrayList<>(dtoList.size());
        for (AccountDTO accountDTO : dtoList) {
            list.add(toEntity(accountDTO));
        }

        return list;
    }

    @Override
    public List<AccountDTO> toDto(List<Account> entityList) {
        if (entityList == null) {
            return null;
        }

        List<AccountDTO> list = new ArrayList<AccountDTO>(entityList.size());
        for (Account account : entityList) {
            list.add(toDto(account));
        }

        return list;
    }
}
