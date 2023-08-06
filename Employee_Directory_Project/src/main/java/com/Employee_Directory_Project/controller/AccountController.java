package com.Employee_Directory_Project.controller;

import com.Employee_Directory_Project.entities.Account;
import com.Employee_Directory_Project.repository.AccountRepository;
import com.Employee_Directory_Project.security.AccountDetails;
import com.Employee_Directory_Project.service.AccountService;
import com.Employee_Directory_Project.service.dto.AccountDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;

import java.time.ZonedDateTime;

@RestController
@RequestMapping("/admin/account")
public class AccountController {
    private final AccountService accountService;

    private final AccountRepository accountRepository;


    public AccountController(AccountService accountService, AccountRepository accountRepository) {
        this.accountService = accountService;
        this.accountRepository = accountRepository;
    }

    @GetMapping("/index")
    public ModelAndView getAllAccount(Pageable pageable){
        ModelAndView mav = new ModelAndView("/account/index");
        Page<AccountDTO> listAccount = accountService.findAll(pageable);
        mav.addObject("listAccount", listAccount);
        return mav;
    }

    @GetMapping("/edit/{id}")
    public ModelAndView edit(@PathVariable Integer id){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        AccountDetails accountDetails = (AccountDetails)authentication.getPrincipal();
        ModelAndView mav = new ModelAndView("/account/edit");
        mav.addObject("account", accountService.findById(id).get())
                .addObject("email", accountDetails.getEmail())
                .addObject("avatar_path", accountDetails.getAvatarPath())
                .addObject("objectSearchPage", "account");
        return mav;
    }

    @PostMapping("/edit/{id}")
    public ModelAndView editAccount(@PathVariable int id, @ModelAttribute("account") AccountDTO newAccountDTO, Pageable pageable ) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        AccountDetails accountDetails = (AccountDetails) authentication.getPrincipal();
        accountService.findById(id).map(AccountDTO -> {
            AccountDTO.setEmail(newAccountDTO.getEmail());
            AccountDTO.setUsername(newAccountDTO.getUsername());
            AccountDTO.setRole_id(newAccountDTO.getRole_id());
            AccountDTO.setStatus(newAccountDTO.getStatus());
            AccountDTO.setLastModifiedDate(ZonedDateTime.now());
            return accountService.save(newAccountDTO);
        })
                .orElseGet(() -> {
                    newAccountDTO.setId(id);
                    return accountService.save(newAccountDTO);
                });

        ModelAndView mav = new ModelAndView("/account/index");
        Page<AccountDTO> listAccount = accountService.findAll(pageable);
        mav.addObject("listAccount", listAccount)
          //      .addObject("messages", "Editing Account details successfully !")
                .addObject("email", accountDetails.getEmail())
                .addObject("avatar_path", accountDetails.getAvatarPath())
                .addObject("objectSearchPage", "account");
        return mav;
    }
}
