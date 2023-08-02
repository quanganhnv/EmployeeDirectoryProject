package com.Employee_Directory_Project.controller;

import com.Employee_Directory_Project.entities.Account;
import com.Employee_Directory_Project.repository.AccountRepository;
import com.Employee_Directory_Project.service.AccountService;
import com.Employee_Directory_Project.service.dto.AccountDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;

@RestController
@RequestMapping("/admin")
public class AccountController {
    private final AccountService accountService;

    private final AccountRepository accountRepository;


    public AccountController(AccountService accountService, AccountRepository accountRepository) {
        this.accountService = accountService;
        this.accountRepository = accountRepository;
    }

    @GetMapping("/page-list-account")
    public ModelAndView getAllAccount(Pageable pageable){
        ModelAndView mav = new ModelAndView("page-list-account");
        Page<AccountDTO> listAccount = accountService.findAll(pageable);
        mav.addObject("listAccount", listAccount);
        return mav;
    }
}
