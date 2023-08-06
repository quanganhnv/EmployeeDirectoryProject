package com.Employee_Directory_Project.service;

import com.Employee_Directory_Project.service.dto.AccountDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

public interface AccountService {
    AccountDTO save(AccountDTO accountDTO);

    public UserDetails loadUserByUsername(String username);

    Page<AccountDTO> findAll(Pageable pageable);

    Optional<AccountDTO> findById(Integer id);
}
