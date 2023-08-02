package com.Employee_Directory_Project.controller;

import com.Employee_Directory_Project.entities.Account;
import com.Employee_Directory_Project.repository.EmployeeRepository;
import com.Employee_Directory_Project.security.AccountDetails;
import com.Employee_Directory_Project.service.DepartmentService;
import com.Employee_Directory_Project.service.EmployeeService;
import com.Employee_Directory_Project.service.dto.EmployeeDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RestController
@RequestMapping("/employee")
public class EmployeeController {
    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/index")
    public ModelAndView getListEmployeePage(@RequestParam(value = "textSearch", required = false, defaultValue = "") String textSearch, Pageable pageable){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        AccountDetails accountDetails = (AccountDetails)authentication.getPrincipal();
        ModelAndView mav = new ModelAndView("/employee/index");
        Page<EmployeeDTO> page = employeeService.findAll(textSearch, pageable);
        mav.addObject("listEmployee", page);
        mav.addObject("id", accountDetails.getID());
        mav.addObject("email", accountDetails.getEmail());
        mav.addObject("avatar_path", accountDetails.getAvatarPath());
        return mav;
    }

    @GetMapping("/index/search")
    public ModelAndView search(@RequestParam(value = "textSearch", required = true) String textSearch, Pageable pageable, Authentication authentication) {
        ModelAndView mav = new ModelAndView("/employee/index");
        Page<EmployeeDTO> page = employeeService.findAll(textSearch, pageable);
        mav.addObject("listEmployee", page);
        // send to /employees/list
        return mav;
    }

    @GetMapping("/details/{id}")
    public ModelAndView showListEmployeeOfDepartment(@PathVariable int id, Pageable pageable){
        ModelAndView mav = new ModelAndView("/employee/index");
        Page<EmployeeDTO> page = employeeService.findAllByDepartment(id, pageable);
        mav.addObject("listEmployee", page);
        return mav;
    }

    @GetMapping("/profile/{id}")
    public ModelAndView viewProfile(@PathVariable Integer id, Authentication authentication){
        authentication = SecurityContextHolder.getContext().getAuthentication();
        AccountDetails accountDetails = (AccountDetails)authentication.getPrincipal();

        ModelAndView mav = new ModelAndView("/employee/profile");
        return mav;
    }
}
