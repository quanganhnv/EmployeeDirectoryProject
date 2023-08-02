package com.Employee_Directory_Project.controller;

import com.Employee_Directory_Project.security.AccountDetails;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class UserController {
    @GetMapping("/user-profile/{id}")
    public ModelAndView viewProfile(@PathVariable Integer id, Authentication authentication){
        authentication = SecurityContextHolder.getContext().getAuthentication();
        AccountDetails accountDetails = (AccountDetails)authentication.getPrincipal();

        ModelAndView mav = new ModelAndView("/user-profile");
        return mav;
    }
}
