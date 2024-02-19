package com.Employee_Directory_Project.controller;

import com.Employee_Directory_Project.repository.ProjectRepository;
import com.Employee_Directory_Project.security.AccountDetails;
import com.Employee_Directory_Project.service.EmployeeService;
import com.Employee_Directory_Project.service.ProjectService;
import com.Employee_Directory_Project.service.dto.EmployeeDTO;
import com.Employee_Directory_Project.service.dto.ProjectDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RestController
@RequestMapping ("/pm")
public class ProjectController {

    private final ProjectService projectService ;

    private final EmployeeService employeeService ;

    private final ProjectRepository projectRepository;

    public ProjectController(ProjectService projectService, EmployeeService employeeService, ProjectRepository projectRepository) {
        this.projectService = projectService;
        this.employeeService = employeeService;
        this.projectRepository = projectRepository;
    }

    @GetMapping("/project/add")
    public ModelAndView AddProject(ModelMap modelMap,Authentication authentication){
        authentication = SecurityContextHolder.getContext().getAuthentication();
        AccountDetails accountDetails = (AccountDetails)authentication.getPrincipal();
        ProjectDTO projectDTO = new ProjectDTO();
        List<EmployeeDTO> candidates = employeeService.findAll();
        modelMap.addAttribute( "candidates", candidates);
        modelMap.addAttribute( "projectDTO", projectDTO);
        modelMap.addAttribute("objectSearchPage","pm/project")
                .addAttribute("id", accountDetails.getEmployeeID())
                .addAttribute("email", accountDetails.getEmail())
                .addAttribute("avatar_path", accountDetails.getAvatarPath());
        return new ModelAndView("project/add", modelMap);
    }

    @PostMapping("/project/add")
    public ModelAndView SaveProject(@ModelAttribute("ProjectDTO") ProjectDTO projectDTO, ModelMap modelMap , Authentication authentication){
        authentication = SecurityContextHolder.getContext().getAuthentication();
        AccountDetails accountDetails = (AccountDetails)authentication.getPrincipal();
        if(projectRepository.findOneByName(projectDTO.getName()).isPresent()){
            modelMap.addAttribute("message","Name has been existed , please enter new name");
            modelMap.addAttribute( "projectDTO", projectDTO)
                    .addAttribute("id", accountDetails.getEmployeeID())
                    .addAttribute("email", accountDetails.getEmail())
                    .addAttribute("avatar_path", accountDetails.getAvatarPath());
            return new ModelAndView("project/add",modelMap);
        };
        modelMap.addAttribute("projectDTO",projectService.save(projectDTO));
        modelMap.addAttribute("objectSearchPage","pm/project")
                .addAttribute("id", accountDetails.getEmployeeID())
                .addAttribute("email", accountDetails.getEmail())
                .addAttribute("avatar_path", accountDetails.getAvatarPath());
        return new ModelAndView("project/detail",modelMap);
    }

    @GetMapping("/project/index")
    public ModelAndView getAllProjects( Pageable pageable , ModelMap modelMap ,Authentication authentication){
        authentication = SecurityContextHolder.getContext().getAuthentication();
        AccountDetails accountDetails = (AccountDetails)authentication.getPrincipal();
        List<ProjectDTO> list = projectService.findAll();
             modelMap.addAttribute("projectList", list);
             modelMap.addAttribute("objectSearchPage","pm/project")
                     .addAttribute("id", accountDetails.getEmployeeID())
                     .addAttribute("email", accountDetails.getEmail())
                     .addAttribute("avatar_path", accountDetails.getAvatarPath());
        return new ModelAndView("project/index", modelMap);
    }

    @GetMapping("/project/index/search/")
    public ModelAndView getAllProjects(@RequestParam(name = "textSearch",required = false)String textSearch, Pageable pageable , ModelMap modelMap ,Authentication authentication) {
        authentication = SecurityContextHolder.getContext().getAuthentication();
        AccountDetails accountDetails = (AccountDetails)authentication.getPrincipal();
        Page<ProjectDTO> page = projectService.findAllByNameContainingIgnoreCase(textSearch, pageable);
        modelMap.addAttribute("projectList",page);
        modelMap.addAttribute("objectSearchPage","pm/project")
                .addAttribute("id", accountDetails.getEmployeeID())
                .addAttribute("email", accountDetails.getEmail())
                .addAttribute("avatar_path", accountDetails.getAvatarPath());
        return new ModelAndView("project/index", modelMap);
    }

    @GetMapping("/project/index/{id}")
    public ModelAndView deleteProject(@PathVariable Integer id , ModelMap modelMap , Authentication authentication) {
        authentication = SecurityContextHolder.getContext().getAuthentication();
        AccountDetails accountDetails = (AccountDetails)authentication.getPrincipal();
        projectService.delete(id);
        List<ProjectDTO> list = projectService.findAll();
        modelMap.addAttribute("projectList",list);
        modelMap.addAttribute("objectSearchPage","pm/project")
                .addAttribute("id", accountDetails.getEmployeeID())
                .addAttribute("email", accountDetails.getEmail())
                .addAttribute("avatar_path", accountDetails.getAvatarPath());
        return new ModelAndView("project/index", modelMap);
    }

    @GetMapping(value = {"/project/update/{id}"})
    public ModelAndView updateProject(@PathVariable Integer id,ModelMap modelMap , Authentication authentication){
        authentication = SecurityContextHolder.getContext().getAuthentication();
        AccountDetails accountDetails = (AccountDetails)authentication.getPrincipal();
        ProjectDTO projectDTO = projectService.findAllById(id);
        modelMap.addAttribute( "projectDTO", projectDTO);

        List<EmployeeDTO> candidates = employeeService.findAll();
        modelMap.addAttribute( "candidates", candidates);
        modelMap.addAttribute("objectSearchPage","pm/project")
                .addAttribute("id", accountDetails.getEmployeeID())
                .addAttribute("email", accountDetails.getEmail())
                .addAttribute("avatar_path", accountDetails.getAvatarPath());
        return new ModelAndView("project/update", modelMap);
    }

    @PostMapping("/project/update/{id}")
    public ModelAndView updateSaveProject(@ModelAttribute("projectDTO") ProjectDTO projectDTO, @PathVariable Integer id, ModelMap modelMap , Authentication authentication){
        authentication = SecurityContextHolder.getContext().getAuthentication();
        AccountDetails accountDetails = (AccountDetails)authentication.getPrincipal();
        modelMap.addAttribute("projectDTO",projectService.update(projectDTO , id));
        modelMap.addAttribute("objectSearchPage","pm/project")
                .addAttribute("id", accountDetails.getEmployeeID())
                .addAttribute("email", accountDetails.getEmail())
                .addAttribute("avatar_path", accountDetails.getAvatarPath());
        return new ModelAndView("project/detail",modelMap);
    }

    @GetMapping("/project/detail/{id}")
    public ModelAndView projectDetail(@PathVariable Integer id , ModelMap modelMap,Authentication authentication){
        authentication = SecurityContextHolder.getContext().getAuthentication();
        AccountDetails accountDetails = (AccountDetails)authentication.getPrincipal();
        ProjectDTO projectDTO = projectService.findAllById(id);
        modelMap.addAttribute( "projectDTO", projectDTO);
        modelMap.addAttribute("objectSearchPage","pm/project")
                .addAttribute("id", accountDetails.getEmployeeID())
                .addAttribute("email", accountDetails.getEmail())
                .addAttribute("avatar_path", accountDetails.getAvatarPath());

        return new ModelAndView("project/detail", modelMap);
    }

}
