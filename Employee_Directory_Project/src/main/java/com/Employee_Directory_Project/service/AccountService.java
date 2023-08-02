package com.Employee_Directory_Project.service;

import com.Employee_Directory_Project.service.dto.AccountDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;

public interface AccountService {
    public UserDetails loadUserByUsername(String username);

    Page<AccountDTO> findAll(Pageable pageable);
}
