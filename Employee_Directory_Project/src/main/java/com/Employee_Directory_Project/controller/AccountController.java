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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        AccountDetails accountDetails = (AccountDetails)authentication.getPrincipal();
        ModelAndView mav = new ModelAndView("/account/index");
        Page<AccountDTO> listAccount = accountService.findAll(pageable);
        mav.addObject("listAccount", listAccount)
                .addObject("id", accountDetails.getEmployeeID())
                .addObject("avatar_path", accountDetails.getAvatarPath())
                .addObject("email", accountDetails.getEmail())
                .addObject("objectSearchPage", "account");

        return mav;
    }

    @GetMapping("/edit/{id}")
    public ModelAndView edit(@PathVariable Integer id){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        AccountDetails accountDetails = (AccountDetails)authentication.getPrincipal();
        ModelAndView mav = new ModelAndView("/account/edit");
        List<String> listRole = new ArrayList<String>();
        listRole.add("ROLE_ADMIN");
        listRole.add("ROLE_USER");
        listRole.add("ROLE_PM");

        List<String> listStatus = new ArrayList<String>();
        listStatus.add("0");
        listStatus.add("1");

        mav.addObject("account", accountService.findById(id).get())
                .addObject("id", accountDetails.getEmployeeID())
                .addObject("listRole", listRole)
                .addObject("listStatus",listStatus)
                .addObject("messages", "Edit Account Success !")
                .addObject("email", accountDetails.getEmail())
                .addObject("avatar_path", accountDetails.getAvatarPath())
                .addObject("objectSearchPage", "account");
        return mav;
    }

    @PostMapping("/edit/{id}")
    public ModelAndView editAccount(@PathVariable int id, @ModelAttribute("account") AccountDTO newAccountDTO, Pageable pageable ) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        AccountDetails accountDetails = (AccountDetails) authentication.getPrincipal();
        AccountDTO accountDTO = accountService.findById(id).get();
        accountDTO.setEmail(newAccountDTO.getEmail());
        accountDTO.setUsername(newAccountDTO.getUsername());
        accountDTO.setRole_id(newAccountDTO.getRole_id());
        accountDTO.setStatus(newAccountDTO.getStatus());
        accountDTO.setLastModifiedDate(ZonedDateTime.now());

        accountService.save(accountDTO);

        ModelAndView mav = new ModelAndView("/account/index");
        Page<AccountDTO> listAccount = accountService.findAll(pageable);
        mav.addObject("listAccount", listAccount)
                .addObject("id", accountDetails.getEmployeeID())
                .addObject("messages", "Editing Account details successfully !")
                .addObject("email", accountDetails.getEmail())
                .addObject("avatar_path", accountDetails.getAvatarPath())
                .addObject("objectSearchPage", "account");
        return mav;
    }

    @GetMapping("/delete/{id}")
    public ModelAndView delete(@PathVariable Integer id, Pageable pageable) {
        accountService.delete(id);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        AccountDetails accountDetails = (AccountDetails) authentication.getPrincipal();
        ModelAndView mav = new ModelAndView("/account/index");
        Page<AccountDTO> page = accountService.findAll(pageable);
        mav.addObject("listAccount", page)
                .addObject("messages", "Delete Success !")
                .addObject("id", accountDetails.getEmployeeID())
                .addObject("email", accountDetails.getEmail())
                .addObject("avatar_path", accountDetails.getAvatarPath())
                .addObject("objectSearchPage", "account");
        return mav;
    }
}
