package com.Employee_Directory_Project.controller;

import com.Employee_Directory_Project.entities.Skill;
import com.Employee_Directory_Project.repository.SkillRepository;
import com.Employee_Directory_Project.security.AccountDetails;
import com.Employee_Directory_Project.service.EmployeeService;
import com.Employee_Directory_Project.service.SkillService;
import com.Employee_Directory_Project.service.dto.SkillDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/skill")
public class SkillController {
    private final SkillService skillService;

    private final SkillRepository skillRepository;

    private final EmployeeService employeeService;

    public SkillController(SkillService skillService, SkillRepository skillRepository, EmployeeService employeeService) {
        this.skillService = skillService;
        this.skillRepository = skillRepository;
        this.employeeService = employeeService;
    }

    @GetMapping("/index")
    public ModelAndView index(Pageable pageable){
        ModelAndView mav = new ModelAndView("/skill/index");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        AccountDetails accountDetails = (AccountDetails)authentication.getPrincipal();
        Page<SkillDTO> listSkill = skillService.findAll(accountDetails.getEmployeeID(), pageable);

        mav.addObject("listSkill", listSkill)
                .addObject("employee_name",  accountDetails.getFullName())
                .addObject("email", accountDetails.getEmail())
                .addObject("id", accountDetails.getEmployeeID())
                .addObject("avatar_path", accountDetails.getAvatarPath())
                .addObject("objectSearchPage", "skill");

        return mav;
    }

    @GetMapping("/index/search")
    public ModelAndView search(@RequestParam(value = "textSearch", required = true) String textSearch, Pageable pageable) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        AccountDetails accountDetails = (AccountDetails)authentication.getPrincipal();
        ModelAndView mav = new ModelAndView("/skill/index");
        Page<SkillDTO> page = skillService.findAll(accountDetails.getEmployeeID(), pageable);

        mav.addObject("listSkill", page)
                .addObject("email", accountDetails.getEmail())
                .addObject("id", accountDetails.getEmployeeID())
                .addObject("avatar_path", accountDetails.getAvatarPath())
                .addObject("objectSearchPage", "skill");
        return mav;
    }

    @GetMapping("/delete/{id}")
    public ModelAndView delete(@PathVariable Integer id, Pageable pageable) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        AccountDetails accountDetails = (AccountDetails)authentication.getPrincipal();
        ModelAndView mav = new ModelAndView("/skill/index");
        skillService.delete(id);
        Page<SkillDTO> page = skillService.findAll(accountDetails.getEmployeeID(),pageable);
        mav.addObject("listSkill", page)
                .addObject("messages", "Delete Success !")
                .addObject("email", accountDetails.getEmail())
                .addObject("id", accountDetails.getEmployeeID())
                .addObject("avatar_path", accountDetails.getAvatarPath())
                .addObject("objectSearchPage", "skill");
        return mav;
    }

    @GetMapping("/add")
    public ModelAndView addSkill(Pageable pageable){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        AccountDetails accountDetails = (AccountDetails)authentication.getPrincipal();
        SkillDTO skillDTO = new SkillDTO();
        ModelAndView mav = new ModelAndView("/skill/add");

        List<String> listLevel = new ArrayList<String>();
        listLevel.add("0");
        listLevel.add("1");
        listLevel.add("2");
        listLevel.add("3");

        mav.addObject("skill", skillDTO)
                .addObject("listLevel", listLevel)
                .addObject("email", accountDetails.getEmail())
                .addObject("id", accountDetails.getEmployeeID())
                .addObject("avatar_path", accountDetails.getAvatarPath())
                .addObject("objectSearchPage", "skill");
        return mav;
    }

    @PostMapping("/add")
    public ModelAndView createSkill (@ModelAttribute("skill") SkillDTO skillDTO, Pageable pageable) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        AccountDetails accountDetails = (AccountDetails)authentication.getPrincipal();

        skillDTO.setEmployee_id(accountDetails.getEmployeeID());
        SkillDTO result = skillService.save(skillDTO);


        ModelAndView mav = new ModelAndView("/skill/index");
        Page<SkillDTO> listSkill = skillService.findAll(accountDetails.getEmployeeID(), pageable);
        mav.addObject("listSkill", listSkill)
                .addObject("messages", "Add Skill Success !")
                .addObject("email", accountDetails.getEmail())
                .addObject("id", accountDetails.getEmployeeID())
                .addObject("avatar_path", accountDetails.getAvatarPath())
                .addObject("objectSearchPage", "skill");
        return mav;
    }

    @GetMapping("/edit/{id}")
    public ModelAndView edit(@PathVariable Integer id){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        AccountDetails accountDetails = (AccountDetails)authentication.getPrincipal();
        ModelAndView mav = new ModelAndView("/skill/edit");

        List<String> listLevel = new ArrayList<String>();
        listLevel.add("0");
        listLevel.add("1");
        listLevel.add("2");
        listLevel.add("3");

        mav.addObject("skill", skillService.findOne(accountDetails.getEmployeeID(),id).get())
                .addObject("listLevel", listLevel)
                .addObject("id", accountDetails.getEmployeeID())
                .addObject("email", accountDetails.getEmail())
                .addObject("avatar_path", accountDetails.getAvatarPath())
                .addObject("objectSearchPage", "skill");
        return mav;
    }

    @PostMapping("/edit/{id}")
    public ModelAndView editSkill(@PathVariable int id, @ModelAttribute("skill") SkillDTO newSkillDTO, Pageable pageable ) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        AccountDetails accountDetails = (AccountDetails)authentication.getPrincipal();
        skillService.findOne(accountDetails.getEmployeeID(),id).map(SkillDTO -> {
            SkillDTO.setName(newSkillDTO.getName());
            SkillDTO.setEmployee_id(newSkillDTO.getEmployee_id());
            SkillDTO.setLevel(newSkillDTO.getLevel());
            SkillDTO.setDescription(newSkillDTO.getDescription());
            SkillDTO.setLastModifiedDate(ZonedDateTime.now());

            return skillService.save(newSkillDTO);
        })
                .orElseGet(() -> {
                    newSkillDTO.setId(id);
                    return skillService.save(newSkillDTO);
                });

        ModelAndView mav = new ModelAndView("/skill/index");
        Page<SkillDTO> page = skillService.findAll(accountDetails.getEmployeeID(), pageable);
        mav.addObject("listSkill", page)
                .addObject("messages", "Editing Skill successfully !")
                .addObject("email", accountDetails.getEmail())
                .addObject("id", accountDetails.getEmployeeID())
                .addObject("avatar_path", accountDetails.getAvatarPath())
                .addObject("objectSearchPage", "skill");
        return mav;
    }
}
