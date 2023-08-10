package com.Employee_Directory_Project.controller;

import com.Employee_Directory_Project.entities.Skill;
import com.Employee_Directory_Project.security.AccountDetails;
import com.Employee_Directory_Project.service.AccountService;
import com.Employee_Directory_Project.service.DepartmentService;
import com.Employee_Directory_Project.service.EmployeeService;
import com.Employee_Directory_Project.service.SkillService;
import com.Employee_Directory_Project.service.dto.AccountDTO;
import com.Employee_Directory_Project.service.dto.DepartmentDTO;
import com.Employee_Directory_Project.service.dto.EmployeeDTO;
import com.Employee_Directory_Project.service.dto.SkillDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;

@RestController
public class EmployeeController {
    private final EmployeeService employeeService;

    private final AccountService accountService;

    private final DepartmentService departmentService;

    private final SkillService skillService;

    public EmployeeController(EmployeeService employeeService, AccountService accountService, DepartmentService departmentService, SkillService skillService) {
        this.employeeService = employeeService;
        this.accountService = accountService;
        this.departmentService = departmentService;
        this.skillService = skillService;
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
                .addObject("id", accountDetails.getEmployeeID())
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
                .addObject("id", accountDetails.getEmployeeID())
                .addObject("email", accountDetails.getEmail())
                .addObject("avatar_path", accountDetails.getAvatarPath())
                .addObject("objectSearchPage", "employee");
        return mav;
    }

    @GetMapping("/employee/profile/{id}")
    public ModelAndView viewProfile(@PathVariable int id, Authentication authentication, Pageable pageable){
        authentication = SecurityContextHolder.getContext().getAuthentication();
        AccountDetails accountDetails = (AccountDetails)authentication.getPrincipal();

        EmployeeDTO employeeDTO = employeeService.findOne(id).get();
        Page<SkillDTO> listSkill = skillService.findAll(id, pageable);

        Map<String, Integer> graphData = new TreeMap<>();

        for (int i = 0; i < listSkill.getContent().size(); i++)
        {
            SkillDTO skillDTO = listSkill.getContent().get(i);
            graphData.put(skillDTO.getName(), skillDTO.getYears_of_experience());
        }

        ModelAndView mav = new ModelAndView("/employee/profile");

        mav     .addObject("employee", employeeDTO)
                .addObject("chartData", graphData)
                .addObject("id", accountDetails.getEmployeeID())
                .addObject("email", accountDetails.getEmail())
                .addObject("avatar_path", accountDetails.getAvatarPath())
                .addObject("objectSearchPage", "employee");
        return mav;
    }

    @GetMapping("/admin/employee/add")
    public ModelAndView addEmployee(Pageable pageable){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        AccountDetails accountDetails = (AccountDetails)authentication.getPrincipal();
        Page<DepartmentDTO> listDepartment = departmentService.findAll(pageable);
        EmployeeDTO employeeDTO = new EmployeeDTO();
        ModelAndView mav = new ModelAndView("/employee/add");
        mav.addObject("objectSearchPage", "employee")
                .addObject("listDepartment", listDepartment)
                .addObject("id", accountDetails.getEmployeeID())
                .addObject("email", accountDetails.getEmail())
                .addObject("avatar_path", accountDetails.getAvatarPath())
                .addObject("employee", employeeDTO)
                .addObject("objectSearchPage", "employee");

        return mav;
    }

    @PostMapping("/admin/employee/add")
    public ModelAndView createCertificate (@ModelAttribute("employee") EmployeeDTO newEmployeeDTO, Pageable pageable) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        AccountDetails accountDetails = (AccountDetails)authentication.getPrincipal();

        Optional<EmployeeDTO> employeeDTO1 = employeeService.findTopByOrderByIdDesc();
        newEmployeeDTO.setId(employeeDTO1.get().getId()+1);

        AccountDTO accountDTO = new AccountDTO();
        accountDTO.setId(newEmployeeDTO.getId());
        accountDTO.setEmail(newEmployeeDTO.getEmail());
        accountDTO.setUsername(newEmployeeDTO.getUsername());
        accountDTO.setPassword(newEmployeeDTO.getPassword());
        accountDTO.setRole_id(newEmployeeDTO.getRole_id());
        accountDTO.setEmployee_id(newEmployeeDTO.getId());
        accountDTO.setStatus(1);

        AccountDTO resultAccount = accountService.save(accountDTO);

        EmployeeDTO result = employeeService.save(newEmployeeDTO);

        String textSearch = "";
        Page<EmployeeDTO> page = employeeService.findAll(pageable);
        ModelAndView mav = new ModelAndView("/employee/index");
        mav.addObject("listEmployee", page)
                .addObject("objectSearchPage", "employee")
                .addObject("messages", "Add Employee Success !")
                .addObject("id", accountDetails.getEmployeeID())
                .addObject("email", accountDetails.getEmail())
                .addObject("avatar_path", accountDetails.getAvatarPath())
                .addObject("objectSearchPage", "employee");
        return mav;
    }

    @GetMapping("/employee/edit")
    public ModelAndView edit(Pageable pageable){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        AccountDetails accountDetails = (AccountDetails)authentication.getPrincipal();
        ModelAndView mav = new ModelAndView("/employee/edit");
        Page<DepartmentDTO> listDepartment = departmentService.findAll(pageable);
        mav.addObject("employee", employeeService.findOne(accountDetails.getEmployeeID()).get())
                .addObject("email", accountDetails.getEmail())
                .addObject("id", accountDetails.getEmployeeID())
                .addObject("listDepartment", listDepartment)
                .addObject("avatar_path", accountDetails.getAvatarPath())
                .addObject("objectSearchPage", "employee");
        return mav;
    }

    @PostMapping("/employee/edit")
    public ModelAndView editEmployee(@ModelAttribute("employee") EmployeeDTO newEmployeeDTO, Pageable pageable ) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        AccountDetails accountDetails = (AccountDetails) authentication.getPrincipal();

        EmployeeDTO employeeDTO = employeeService.findOne(accountDetails.getEmployeeID()).get();
        employeeDTO.setFirstName(newEmployeeDTO.getFirstName());
        employeeDTO.setLastName(newEmployeeDTO.getLastName());
        employeeDTO.setPhone(newEmployeeDTO.getPhone());
        employeeDTO.setGender(newEmployeeDTO.getGender());
        employeeDTO.setEmail(newEmployeeDTO.getEmail());
        employeeDTO.setBirthday(newEmployeeDTO.getBirthday());
        employeeDTO.setIdentity_number(newEmployeeDTO.getIdentity_number());
        employeeDTO.setIssued_on(newEmployeeDTO.getIssued_on());
        employeeDTO.setIssued_by(newEmployeeDTO.getIssued_by());
        employeeDTO.setAddress(newEmployeeDTO.getAddress());
        employeeDTO.setDepartment_id(newEmployeeDTO.getDepartment_id());
        employeeDTO.setLastModifiedDate(ZonedDateTime.now());

        employeeService.save(employeeDTO);

        ModelAndView mav = new ModelAndView("/employee/edit");
        EmployeeDTO employeeDTOAfterEdit = employeeService.findOne(accountDetails.getEmployeeID()).get();
        mav.addObject("employee", employeeDTOAfterEdit)
                .addObject("messages", "Editing Info successfully !")
                .addObject("id", accountDetails.getEmployeeID())
                .addObject("email", accountDetails.getEmail())
                .addObject("avatar_path", accountDetails.getAvatarPath())
                .addObject("objectSearchPage", "employee");
        return mav;
    }
}
