package com.Employee_Directory_Project.service;

import com.Employee_Directory_Project.entities.Account;
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

    void delete(Integer id);

    public void updateRememberToken(String token, String email);

    public Account getByRememberToken(String token);

    public void updatePassword(Account account, String newPassword);
}
