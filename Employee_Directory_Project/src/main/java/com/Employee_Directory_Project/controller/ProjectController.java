package com.Employee_Directory_Project.controller;

import com.Employee_Directory_Project.entities.Employee;
import com.Employee_Directory_Project.repository.ProjectRepository;
import com.Employee_Directory_Project.service.EmployeeService;
import com.Employee_Directory_Project.service.ProjectService;
import com.Employee_Directory_Project.service.dto.EmployeeDTO;
import com.Employee_Directory_Project.service.dto.ProjectDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RestController
@RequestMapping ("/pm")
public class ProjectController {
    private final ProjectService projectService  ;
    private final EmployeeService employeeService;

    public ProjectController(ProjectService projectService, EmployeeService employeeService) {
        this.projectService = projectService;
        this.employeeService = employeeService;
    }

    @GetMapping("/project/add")
    public ModelAndView AddProject(ModelMap modelMap){
        ProjectDTO projectDTO = new ProjectDTO();
        List<EmployeeDTO> Candidates =  employeeService.findAll();
        modelMap.addAttribute( "candidates", Candidates);
        modelMap.addAttribute( "projectDTO", projectDTO);
        return new ModelAndView("project/add", modelMap);
    }

    @PostMapping("/project/add")
    public ModelAndView SaveProject(@ModelAttribute("ProjectDTO") ProjectDTO projectDTO, ModelMap modelMap){
        Integer id =projectService.save(projectDTO).getId();
        ProjectDTO result = projectService.findAllById(id);
        System.out.print(result.getPm_id());
        modelMap.addAttribute("projectDTO",result);
        return new ModelAndView("project/detail",modelMap);
    }

    @GetMapping("/project/list")
    public ModelAndView getAllProjects(@RequestParam(name = "textSearch",required = false, defaultValue = "")String textSearch, Pageable pageable , ModelMap modelMap){

        List<ProjectDTO> list = projectService.findAll();
        modelMap.addAttribute("projectList",list);

            if(textSearch == "Hello")
        {
            Page<ProjectDTO> page = projectService.findAllByNameContainingIgnoreCase(textSearch, pageable);
            modelMap.addAttribute("project/list",page.getContent());
        }
        return new ModelAndView("project/list" , modelMap);
    }

    @GetMapping("/project/list/{id}")
    public ModelAndView deleteProject(@PathVariable Integer id , ModelMap modelMap)
    {
        projectService.delete(id);
        List<ProjectDTO> list = projectService.findAll();
        modelMap.addAttribute("projectList",list);
        return new ModelAndView("project/list" , modelMap);
    }

    @GetMapping(value = {"/project/update/{id}"})
    public ModelAndView updateProject(@PathVariable Integer id,ModelMap modelMap){
        ProjectDTO projectDTO = projectService.findAllById(id);
        modelMap.addAttribute( "projectDTO", projectDTO);
        return new ModelAndView("project/update", modelMap);
    }

    @PostMapping("/project/update/{id}")
    public ModelAndView updateSaveProject(@ModelAttribute("ProjectDT0") ProjectDTO projectDTO, ModelMap modelMap){
        modelMap.addAttribute("projectDTO",projectService.save(projectDTO));
        return new ModelAndView("project/detail",modelMap);
    }

    @GetMapping("/project/detail/{id}")
    public ModelAndView projectDetail(@PathVariable Integer id , ModelMap modelMap){
        ProjectDTO projectDTO = projectService.findAllById(id);
        modelMap.addAttribute( "projectDTO", projectDTO);
        return new ModelAndView("project/detail", modelMap);
    }
}
