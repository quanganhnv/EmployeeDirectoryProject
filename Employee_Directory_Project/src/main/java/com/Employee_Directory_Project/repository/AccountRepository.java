package com.Employee_Directory_Project.repository;

import com.Employee_Directory_Project.entities.Account;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer> {
 //   public Account findByEmail(String email);
    public Account findByUsername(String username);

    Page<Account> findAll(Pageable pageable);

    public Account findByRememberToken(String token);

    public Account findByEmail(String email);
}
