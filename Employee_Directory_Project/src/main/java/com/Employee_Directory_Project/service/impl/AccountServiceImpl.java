package com.Employee_Directory_Project.service.impl;

import com.Employee_Directory_Project.entities.Account;
import com.Employee_Directory_Project.repository.AccountRepository;
import com.Employee_Directory_Project.security.AccountDetails;
import com.Employee_Directory_Project.service.AccountService;
import com.Employee_Directory_Project.service.dto.AccountDTO;
import com.Employee_Directory_Project.service.mapper.AccountMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AccountServiceImpl implements AccountService, UserDetailsService {
    private final AccountRepository accountRepository;

    private final AccountMapper accountMapper;

    public AccountServiceImpl(AccountRepository accountRepository, AccountMapper accountMapper) {
        this.accountRepository = accountRepository;
        this.accountMapper = accountMapper;
    }

    @Override
    public AccountDTO save(AccountDTO accountDTO) {
        Account account = accountMapper.toEntity(accountDTO);
        account = accountRepository.save(account);
        return accountMapper.toDto(account);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account account = accountRepository.findByUsername(username);
        if (account == null) {
            throw new UsernameNotFoundException("No account found with the given username");
        }
        return new AccountDetails(account);
    }

    @Override
    public Page<AccountDTO> findAll(Pageable pageable) {
        return accountRepository.findAll(pageable).map(accountMapper::toDto);
    }

    @Override
    public Optional<AccountDTO> findById(Integer id) {
        return accountRepository.findById(id).map(accountMapper::toDto);
    }

    @Override
    public void delete(Integer id) {
        accountRepository.deleteById(id);
    }

    @Override
    public void updateRememberToken(String token, String email) throws UsernameNotFoundException {
        Account account = accountRepository.findByEmail(email);
        if (account != null) {
            account.setRememberToken(token);
            accountRepository.save(account);
        }
    }

    @Override
    public Account getByRememberToken(String token) {
        return accountRepository.findByRememberToken(token);
    }

    @Override
    public void updatePassword(Account account, String newPassword) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(newPassword);
        account.setPassword(encodedPassword);

        account.setRememberToken(null);
        accountRepository.save(account);
    }

//    public UserDetails loadUserByEmail(String email) throws UsernameNotFoundException{
//        Account account = accountRepository.findByEmail(email);
//        if (account == null) {
//            throw new UsernameNotFoundException("No account found with the given email");
//        }
//        return new AccountDetails(account);
//    }
}