package com.Employee_Directory_Project.controller;

import com.Employee_Directory_Project.security.AccountDetails;
import com.Employee_Directory_Project.service.EmployeeService;
import com.Employee_Directory_Project.service.ProjectService;
import com.Employee_Directory_Project.service.Project_MemService;
import com.Employee_Directory_Project.service.dto.EmployeeDTO;
import com.Employee_Directory_Project.service.dto.ProjectDTO;
import com.Employee_Directory_Project.service.dto.Project_MemDTO;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RestController
@RequestMapping("/pm")
public class Project_MemController {
    
    private final Project_MemService project_MemService;
    private final EmployeeService employeeService;
    private final ProjectService projectService;

    public Project_MemController(Project_MemService project_MemService, EmployeeService employeeService, ProjectService projectService) {
        this.project_MemService = project_MemService;
        this.employeeService = employeeService;
        this.projectService = projectService;
    }

    @GetMapping("/project_Mem/add")
    public ModelAndView AddProject_Mem(ModelMap modelMap, Authentication authentication){
        authentication = SecurityContextHolder.getContext().getAuthentication();
        AccountDetails accountDetails = (AccountDetails)authentication.getPrincipal();
        Project_MemDTO project_MemDTO = new Project_MemDTO();
        List<EmployeeDTO> candidates = employeeService.findAll();
        List<ProjectDTO> projects = projectService.findAll();
        modelMap.addAttribute( "candidates", candidates);
        modelMap.addAttribute("projects",projects);
        modelMap.addAttribute( "project_MemDTO", project_MemDTO);
        modelMap.addAttribute("objectSearchPage","pm/project_Mem")
                .addAttribute("id", accountDetails.getEmployeeID())
                .addAttribute("email", accountDetails.getEmail())
                .addAttribute("avatar_path", accountDetails.getAvatarPath());
        return new ModelAndView("project_Mem/add", modelMap);
    }

    @PostMapping("/project_Mem/add")
    public ModelAndView SaveProject_Mem(@ModelAttribute("Project_MemDTO") Project_MemDTO project_MemDTO, ModelMap modelMap,Authentication authentication){
        authentication = SecurityContextHolder.getContext().getAuthentication();
        AccountDetails accountDetails = (AccountDetails)authentication.getPrincipal();
        modelMap.addAttribute("project_MemDTO",project_MemService.save(project_MemDTO));
        modelMap.addAttribute("objectSearchPage","pm/project_Mem")
                .addAttribute("id", accountDetails.getEmployeeID())
                .addAttribute("email", accountDetails.getEmail())
                .addAttribute("avatar_path", accountDetails.getAvatarPath());
        return new ModelAndView("project_Mem/detail",modelMap);
    }

    @GetMapping("/project_Mem/index")
    public ModelAndView getAllProject_Mems(Pageable pageable , ModelMap modelMap,Authentication authentication){
        authentication = SecurityContextHolder.getContext().getAuthentication();
        AccountDetails accountDetails = (AccountDetails)authentication.getPrincipal();
        List<Project_MemDTO> list = project_MemService.findAll();
        modelMap.addAttribute("project_MemList", list);
        modelMap.addAttribute("objectSearchPage","pm/project_Mem")
                .addAttribute("id", accountDetails.getEmployeeID())
                .addAttribute("email", accountDetails.getEmail())
                .addAttribute("avatar_path", accountDetails.getAvatarPath());
        return new ModelAndView("project_Mem/index", modelMap);
    }

    @GetMapping("/project_Mem/index/search/")
    public ModelAndView getAllProject_Mems(@RequestParam(name = "textSearch",required = false)String textSearch, ModelMap modelMap,Authentication authentication) {
        authentication = SecurityContextHolder.getContext().getAuthentication();
        AccountDetails accountDetails = (AccountDetails)authentication.getPrincipal();
        List<Project_MemDTO> page = project_MemService.findAllByProject_name(textSearch);
        modelMap.addAttribute("project_MemList",page);
        modelMap.addAttribute("objectSearchPage","pm/project_Mem")
                .addAttribute("id", accountDetails.getEmployeeID())
                .addAttribute("email", accountDetails.getEmail())
                .addAttribute("avatar_path", accountDetails.getAvatarPath());
        return new ModelAndView("project_Mem/index", modelMap);
    }

    @GetMapping("/project_Mem/index/{id}")
    public ModelAndView deleteProject_Mem(@PathVariable Integer id , ModelMap modelMap,Authentication authentication) {
        authentication = SecurityContextHolder.getContext().getAuthentication();
        AccountDetails accountDetails = (AccountDetails)authentication.getPrincipal();
        project_MemService.delete(id);
        List<Project_MemDTO> list = project_MemService.findAll();
        modelMap.addAttribute("project_MemList",list);
        modelMap.addAttribute("objectSearchPage","pm/project_Mem")
                .addAttribute("id", accountDetails.getEmployeeID())
                .addAttribute("email", accountDetails.getEmail())
                .addAttribute("avatar_path", accountDetails.getAvatarPath());
        return new ModelAndView("project_Mem/index", modelMap);
    }

    @GetMapping(value = {"/project_Mem/update/{id}"})
    public ModelAndView updateProject_Mem(@PathVariable Integer id,ModelMap modelMap,Authentication authentication){
        authentication = SecurityContextHolder.getContext().getAuthentication();
        AccountDetails accountDetails = (AccountDetails)authentication.getPrincipal();
        Project_MemDTO project_MemDTO = project_MemService.findAllById(id);
        modelMap.addAttribute( "project_MemDTO", project_MemDTO);
        List<ProjectDTO> projects = projectService.findAll();
        modelMap.addAttribute("projects",projects);
        List<EmployeeDTO> candidates = employeeService.findAll();
        modelMap.addAttribute( "candidates", candidates);
        modelMap.addAttribute("objectSearchPage","pm/project_Mem")
                .addAttribute("id", accountDetails.getEmployeeID())
                .addAttribute("email", accountDetails.getEmail())
                .addAttribute("avatar_path", accountDetails.getAvatarPath());
        return new ModelAndView("project_Mem/update", modelMap);
    }

    @PostMapping("/project_Mem/update/{id}")
    public ModelAndView updateSaveProject_Mem(@ModelAttribute("project_MemDTO") Project_MemDTO project_MemDTO,@PathVariable Integer id, ModelMap modelMap,Authentication authentication){
        authentication = SecurityContextHolder.getContext().getAuthentication();
        AccountDetails accountDetails = (AccountDetails)authentication.getPrincipal();
        modelMap.addAttribute("project_MemDTO",project_MemService.update(project_MemDTO , id));
        modelMap.addAttribute("objectSearchPage","pm/project_Mem")
                .addAttribute("id", accountDetails.getEmployeeID())
                .addAttribute("email", accountDetails.getEmail())
                .addAttribute("avatar_path", accountDetails.getAvatarPath());
        return new ModelAndView("project_Mem/detail",modelMap);
    }

    @GetMapping("/project_Mem/detail/{id}")
    public ModelAndView project_MemDetail(@PathVariable Integer id , ModelMap modelMap ,Authentication authentication){
        authentication = SecurityContextHolder.getContext().getAuthentication();
        AccountDetails accountDetails = (AccountDetails)authentication.getPrincipal();
        Project_MemDTO project_MemDTO = project_MemService.findAllById(id);
        modelMap.addAttribute( "project_MemDTO", project_MemDTO);
        modelMap.addAttribute("objectSearchPage","pm/project_Mem")
                .addAttribute("id", accountDetails.getEmployeeID())
                .addAttribute("email", accountDetails.getEmail())
                .addAttribute("avatar_path", accountDetails.getAvatarPath());
        return new ModelAndView("project_Mem/detail", modelMap);
    }
}
