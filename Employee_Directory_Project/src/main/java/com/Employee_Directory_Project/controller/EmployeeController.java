package com.Employee_Directory_Project.controller;

import com.Employee_Directory_Project.security.AccountDetails;
import com.Employee_Directory_Project.service.AccountService;
import com.Employee_Directory_Project.service.EmployeeService;
import com.Employee_Directory_Project.service.dto.AccountDTO;
import com.Employee_Directory_Project.service.dto.EmployeeDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

@RestController
public class EmployeeController {
    private final EmployeeService employeeService;

    private final AccountService accountService;

    public EmployeeController(EmployeeService employeeService, AccountService accountService) {
        this.employeeService = employeeService;
        this.accountService = accountService;
    }

    @GetMapping("/employee/index")
    public ModelAndView getListEmployeePage(@RequestParam(value = "textSearch", required = false, defaultValue = "") String textSearch, Pageable pageable){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        AccountDetails accountDetails = (AccountDetails)authentication.getPrincipal();
        ModelAndView mav = new ModelAndView("/employee/index");
        Page<EmployeeDTO> page = employeeService.findAll(pageable);
        mav.addObject("listEmployee", page)
            .addObject("id", accountDetails.getEmployeeID())
            .addObject("email", accountDetails.getEmail())
            .addObject("avatar_path", accountDetails.getAvatarPath())
            .addObject("objectSearchPage", "employee");
        return mav;
    }

    @GetMapping("/employee/index/search")
    public ModelAndView search(@RequestParam(value = "textSearch", required = true) String textSearch, Pageable pageable, Authentication authentication) {
        authentication = SecurityContextHolder.getContext().getAuthentication();
        AccountDetails accountDetails = (AccountDetails)authentication.getPrincipal();
        ModelAndView mav = new ModelAndView("/employee/index");
        Page<EmployeeDTO> page = employeeService.findAllByFirstNameOrLastName(textSearch, textSearch, pageable);
        mav.addObject("listEmployee", page)
                .addObject("objectSearchPage", "employee")
                .addObject("email", accountDetails.getEmail())
                .addObject("avatar_path", accountDetails.getAvatarPath())
                .addObject("objectSearchPage", "employee");
        // send to /employees/list
        return mav;
    }

    @GetMapping("/employee/details/{id}")
    public ModelAndView showListEmployeeOfDepartment(@PathVariable int id, Pageable pageable){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        AccountDetails accountDetails = (AccountDetails)authentication.getPrincipal();
        ModelAndView mav = new ModelAndView("/employee/index");
        Page<EmployeeDTO> page = employeeService.findAllByDepartment(id, pageable);
        mav.addObject("listEmployee", page)
                .addObject("objectSearchPage", "employee")
                .addObject("email", accountDetails.getEmail())
                .addObject("avatar_path", accountDetails.getAvatarPath())
                .addObject("objectSearchPage", "employee");
        return mav;
    }

    @GetMapping("/employee/profile/{id}")
    public ModelAndView viewProfile(@PathVariable Integer id, Authentication authentication){
        authentication = SecurityContextHolder.getContext().getAuthentication();
        AccountDetails accountDetails = (AccountDetails)authentication.getPrincipal();

        ModelAndView mav = new ModelAndView("/employee/profile");
        mav     .addObject("objectSearchPage", "employee")
                .addObject("email", accountDetails.getEmail())
                .addObject("avatar_path", accountDetails.getAvatarPath())
                .addObject("objectSearchPage", "employee");
        return mav;
    }

    @GetMapping("/admin/employee/add")
    public ModelAndView addEmployee(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        AccountDetails accountDetails = (AccountDetails)authentication.getPrincipal();
        EmployeeDTO employeeDTO = new EmployeeDTO();
        ModelAndView mav = new ModelAndView("/employee/add");
        mav.addObject("objectSearchPage", "employee")
                .addObject("email", accountDetails.getEmail())
                .addObject("avatar_path", accountDetails.getAvatarPath())
                .addObject("employee", employeeDTO)
                .addObject("objectSearchPage", "employee");

        return mav;
    }

    @PostMapping("/admin/employee/add")
    public ModelAndView createCertificate (@ModelAttribute("employee") EmployeeDTO employeeDTO, Pageable pageable) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        AccountDetails accountDetails = (AccountDetails)authentication.getPrincipal();

        Optional<EmployeeDTO> employeeDTO1 = employeeService.findTopByOrderByIdDesc();
        employeeDTO.setId(employeeDTO1.get().getId()+1);

        AccountDTO accountDTO = new AccountDTO();
        accountDTO.setId(employeeDTO.getId());
        accountDTO.setEmail(employeeDTO.getEmail());
        accountDTO.setUsername(employeeDTO.getUsername());
        accountDTO.setPassword(employeeDTO.getPassword());
        accountDTO.setRole_id(employeeDTO.getRole_id());
        accountDTO.setEmployee_id(employeeDTO.getId());
        accountDTO.setStatus(1);

        AccountDTO resultAccount = accountService.save(accountDTO);

        EmployeeDTO result = employeeService.save(employeeDTO);

        String textSearch = "";
        Page<EmployeeDTO> page = employeeService.findAll(pageable);
        ModelAndView mav = new ModelAndView("/employee/index");
        mav.addObject("listEmployee", page)
                .addObject("objectSearchPage", "employee")
                .addObject("email", accountDetails.getEmail())
                .addObject("avatar_path", accountDetails.getAvatarPath())
                .addObject("objectSearchPage", "employee");
        return mav;
    }
}
