package com.Employee_Directory_Project.controller;

import com.Employee_Directory_Project.security.AccountDetails;
import com.Employee_Directory_Project.service.EmployeeService;
import com.Employee_Directory_Project.service.ExperienceService;
import com.Employee_Directory_Project.service.dto.EmployeeDTO;
import com.Employee_Directory_Project.service.dto.ExperienceDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RestController
@RequestMapping ("/employee")
public class ExperienceController {

    private final ExperienceService experienceService;
    private final EmployeeService employeeService;

    public ExperienceController(ExperienceService experienceService, EmployeeService employeeService) {
        this.experienceService = experienceService;
        this.employeeService = employeeService;
    }

    @GetMapping("/experience/add")
    public ModelAndView AddExperience(ModelMap modelMap,Authentication authentication){
        authentication = SecurityContextHolder.getContext().getAuthentication();
        AccountDetails accountDetails = (AccountDetails)authentication.getPrincipal();
        ExperienceDTO experienceDTO = new ExperienceDTO();
        List<EmployeeDTO> candidates = employeeService.findAll();
        modelMap.addAttribute( "candidates", candidates);
        modelMap.addAttribute( "experienceDTO", experienceDTO);
        modelMap.addAttribute("objectSearchPage","employee/experience")
                .addAttribute("id", accountDetails.getEmployeeID())
                .addAttribute("email", accountDetails.getEmail())
                .addAttribute("avatar_path", accountDetails.getAvatarPath());
        return new ModelAndView("experience/add", modelMap);
    }

    @PostMapping("/experience/add")
    public ModelAndView SaveExperience(@ModelAttribute("ExperienceDTO") ExperienceDTO experienceDTO, ModelMap modelMap ){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        AccountDetails accountDetails = (AccountDetails)authentication.getPrincipal();
        experienceDTO.setEmployee_id(accountDetails.getEmployeeID());
        modelMap.addAttribute("experienceDTO",experienceService.save(experienceDTO));
        modelMap.addAttribute("objectSearchPage","employee/experience")
                .addAttribute("id", accountDetails.getEmployeeID())
                .addAttribute("email", accountDetails.getEmail())
                .addAttribute("avatar_path", accountDetails.getAvatarPath());
        return new ModelAndView("experience/detail",modelMap);
    }

    @GetMapping("/experience/index/search/")
    public ModelAndView getAllExperiences(@RequestParam(name = "textSearch",required = false)String textSearch, Pageable pageable ,
                                          ModelMap modelMap) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        AccountDetails accountDetails = (AccountDetails)authentication.getPrincipal();
        Page<ExperienceDTO> page = experienceService.findAllByNameContainingIgnoreCaseAndEmployee_id(textSearch,accountDetails.getEmployeeID(), pageable);
        modelMap.addAttribute("experienceList",page);
        modelMap.addAttribute("objectSearchPage","employee/experience")
                .addAttribute("id", accountDetails.getEmployeeID())
                .addAttribute("email", accountDetails.getEmail())
                .addAttribute("avatar_path", accountDetails.getAvatarPath());
        return new ModelAndView("experience/index", modelMap);
    }

    @GetMapping("/experience/index")
    public ModelAndView getAllExperiences( Pageable pageable , ModelMap modelMap){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        AccountDetails accountDetails = (AccountDetails)authentication.getPrincipal();
        List<ExperienceDTO> index = experienceService.findAllByEmployee_id(accountDetails.getEmployeeID());
        modelMap.addAttribute("experienceList",index);
        modelMap.addAttribute("objectSearchPage","employee/experience")
                .addAttribute("id", accountDetails.getEmployeeID())
                .addAttribute("email", accountDetails.getEmail())
                .addAttribute("avatar_path", accountDetails.getAvatarPath());
        return new ModelAndView("experience/index",modelMap);
    }

    @GetMapping("/experience/index/{id}")
    public ModelAndView deleteExperience(@PathVariable Integer id , ModelMap modelMap) {
        experienceService.delete(id);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        AccountDetails accountDetails = (AccountDetails)authentication.getPrincipal();
        List<ExperienceDTO> index = experienceService.findAllByEmployee_id(accountDetails.getEmployeeID());
        modelMap.addAttribute("experienceList",index);
        modelMap.addAttribute("objectSearchPage","employee/experience")
                .addAttribute("id", accountDetails.getEmployeeID())
                .addAttribute("email", accountDetails.getEmail())
                .addAttribute("avatar_path", accountDetails.getAvatarPath());
        return new ModelAndView("experience/index", modelMap);
    }

    @GetMapping(value = {"/experience/update/{id}"})
    public ModelAndView updateExperience(@PathVariable Integer id,ModelMap modelMap,Authentication authentication){
        authentication = SecurityContextHolder.getContext().getAuthentication();
        AccountDetails accountDetails = (AccountDetails)authentication.getPrincipal();
        ExperienceDTO experienceDTO = experienceService.findAllById(id);
        modelMap.addAttribute( "experienceDTO", experienceDTO);
        modelMap.addAttribute("objectSearchPage","employee/experience")
                .addAttribute("id", accountDetails.getEmployeeID())
                .addAttribute("email", accountDetails.getEmail())
                .addAttribute("avatar_path", accountDetails.getAvatarPath());

        return new ModelAndView("experience/update", modelMap);
    }

    @PostMapping("/experience/update/{id}")
    public ModelAndView updateSaveExperience(@ModelAttribute("experienceDTO") ExperienceDTO experienceDTO,@PathVariable Integer id,
                                                ModelMap modelMap ,Authentication authentication ){
        //set Employee id
        authentication = SecurityContextHolder.getContext().getAuthentication();
        AccountDetails accountDetails = (AccountDetails)authentication.getPrincipal();
        experienceDTO.setEmployee_id(accountDetails.getEmployeeID());
        // Attribute
        modelMap.addAttribute("experienceDTO",experienceService.update(experienceDTO ,id));
        modelMap.addAttribute("objectSearchPage","employee/experience")
                .addAttribute("id", accountDetails.getEmployeeID())
                .addAttribute("email", accountDetails.getEmail())
                .addAttribute("avatar_path", accountDetails.getAvatarPath());
        return new ModelAndView("experience/detail",modelMap);
    }

    @GetMapping("/experience/detail/{id}")
    public ModelAndView experienceDetail(@PathVariable Integer id , ModelMap modelMap,Authentication authentication){
        authentication = SecurityContextHolder.getContext().getAuthentication();
        AccountDetails accountDetails = (AccountDetails)authentication.getPrincipal();
        ExperienceDTO experienceDTO = experienceService.findAllById(id);
        modelMap.addAttribute( "experienceDTO", experienceDTO);
        modelMap.addAttribute("objectSearchPage","employee/experience")
                .addAttribute("id", accountDetails.getEmployeeID())
                .addAttribute("email", accountDetails.getEmail())
                .addAttribute("avatar_path", accountDetails.getAvatarPath());
        return new ModelAndView("experience/detail", modelMap);
    }
}
