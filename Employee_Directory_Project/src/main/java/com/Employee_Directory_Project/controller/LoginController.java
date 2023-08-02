package com.Employee_Directory_Project.controller;

import com.sun.org.apache.xpath.internal.operations.Mod;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.jws.WebParam;

@Controller
public class LoginController {
    @GetMapping("/sign-in")
    public ModelAndView getSignInPage() {
        ModelAndView mav = new ModelAndView("/sign-in");
        return mav;
    }

    @GetMapping("/index")
    public String getIndexPage(){
        return "index";
    }

    @GetMapping("/403")
    public String getAccessDeniedPage(){
        return "403";
    }

    @GetMapping("/page-recover-pw")
    public ModelAndView resetPW(ModelAndView mav){
        return mav;
    }

    @PostMapping("/page-recover-pw")
    public String resetPWwithMail(@RequestParam String email){
        return email;
    }
}
