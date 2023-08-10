package com.Employee_Directory_Project.controller;

import com.Employee_Directory_Project.entities.Employee;
import com.Employee_Directory_Project.repository.DepartmentRepository;
import com.Employee_Directory_Project.security.AccountDetails;
import com.Employee_Directory_Project.service.DepartmentService;
import com.Employee_Directory_Project.service.EmployeeService;
import com.Employee_Directory_Project.service.dto.DepartmentDTO;
import com.Employee_Directory_Project.service.dto.EmployeeDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.time.ZonedDateTime;
import java.util.Optional;

@RestController
@RequestMapping("/admin/department")
public class DepartmentController {
    private final DepartmentService departmentService;

    private final DepartmentRepository departmentRepository;

    private final EmployeeService employeeService;

    public DepartmentController(DepartmentService departmentService, DepartmentRepository departmentRepository, EmployeeService employeeService) {
        this.departmentService = departmentService;
        this.departmentRepository = departmentRepository;
        this.employeeService = employeeService;
    }

    @GetMapping("/index")
    public ModelAndView getAllDepartment(Pageable pageable) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        AccountDetails accountDetails = (AccountDetails)authentication.getPrincipal();

        ModelAndView mav = new ModelAndView("department/index");
        Page<DepartmentDTO> page = departmentService.findAll(pageable);
        mav.addObject("listDepartment", page)
                .addObject("email", accountDetails.getEmail())
                .addObject("avatar_path", accountDetails.getAvatarPath())
                .addObject("id", accountDetails.getEmployeeID())
                .addObject("objectSearchPage", "department");
        return mav;
    }

    @GetMapping("/details/{id}")
    public ModelAndView getDepartment(@PathVariable int id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        AccountDetails accountDetails = (AccountDetails)authentication.getPrincipal();
        Optional<DepartmentDTO> departmentDTO = departmentService.findOne(id);
        ModelAndView mav = new ModelAndView("/department/details");
        if (departmentDTO.isPresent()) {
            mav.addObject("department", departmentDTO.get())
                    .addObject("email", accountDetails.getEmail())
                    .addObject("avatar_path", accountDetails.getAvatarPath())
                    .addObject("id", accountDetails.getEmployeeID())
                    .addObject("objectSearchPage", "department");
            return mav;
        }
        mav.addObject("error", "Can't find department details");
        return mav;
    }

    @GetMapping("/edit/{id}")
    public ModelAndView edit(@PathVariable int id, Pageable pageable) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        AccountDetails accountDetails = (AccountDetails)authentication.getPrincipal();
        ModelAndView mav = new ModelAndView("department/edit");
        Page<EmployeeDTO> listEmployee = employeeService.findAllByDepartment(id, pageable);
        mav.addObject("listEmployee", listEmployee)
                .addObject("department", departmentService.findOne(id).get())
                .addObject("email", accountDetails.getEmail())
                .addObject("avatar_path", accountDetails.getAvatarPath())
                .addObject("id", accountDetails.getEmployeeID())
                .addObject("objectSearchPage", "department");
        return mav;
    }

    @GetMapping("/delete/{id}")
    public ModelAndView delete(@PathVariable Integer id, Pageable pageable) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        AccountDetails accountDetails = (AccountDetails)authentication.getPrincipal();
        ModelAndView mav = new ModelAndView("/department/index");
        departmentService.delete(id);
        Page<DepartmentDTO> page = departmentService.findAll(pageable);
        mav.addObject("listDepartment", page)
                .addObject("messages", "Delete Success !")
                .addObject("email", accountDetails.getEmail())
                .addObject("avatar_path", accountDetails.getAvatarPath())
                .addObject("id", accountDetails.getEmployeeID())
                .addObject("objectSearchPage", "department");
        return mav;
    }

    @GetMapping("/add")
    public ModelAndView addDepartment(Pageable pageable, ModelAndView mav){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        AccountDetails accountDetails = (AccountDetails)authentication.getPrincipal();
        DepartmentDTO departmentDTO = new DepartmentDTO();
        mav = new ModelAndView("department/add");
        mav.addObject("department", departmentDTO)
                .addObject("email", accountDetails.getEmail())
                .addObject("avatar_path", accountDetails.getAvatarPath())
                .addObject("id", accountDetails.getEmployeeID())
                .addObject("objectSearchPage", "department");
        return mav;
    }

    @PostMapping("/add")
    public ModelAndView createDepartment (@ModelAttribute("department") DepartmentDTO departmentDTO, Pageable pageable) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        AccountDetails accountDetails = (AccountDetails)authentication.getPrincipal();
        ModelAndView mav = new ModelAndView("/department/index");
        DepartmentDTO result = departmentService.save(departmentDTO);
        Page<DepartmentDTO> page = departmentService.findAll(pageable);
        mav.addObject("listDepartment", page)
                .addObject("messages", "Add Department successfully !")
                .addObject("email", accountDetails.getEmail())
                .addObject("avatar_path", accountDetails.getAvatarPath())
                .addObject("id", accountDetails.getEmployeeID())
                .addObject("objectSearchPage", "department");
        return mav;
    }

    @PostMapping("/edit/{id}")
    public ModelAndView editDepartment(@PathVariable int id, @ModelAttribute("department") DepartmentDTO newDepartmentDTO, Pageable pageable ) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        AccountDetails accountDetails = (AccountDetails)authentication.getPrincipal();
        departmentService.findOne(id).map(DepartmentDTO -> {
            DepartmentDTO.setName(newDepartmentDTO.getName());
            DepartmentDTO.setPhone(newDepartmentDTO.getPhone());
            DepartmentDTO.setManager_id(newDepartmentDTO.getManager_id());
            DepartmentDTO.setDescription(newDepartmentDTO.getDescription());
            DepartmentDTO.setLastModifiedDate(ZonedDateTime.now());
            return departmentService.save(newDepartmentDTO);
        })
                .orElseGet(() -> {
                    newDepartmentDTO.setId(id);
                    return departmentService.save(newDepartmentDTO);
                });

        ModelAndView mav = new ModelAndView("/department/index");
        Page<DepartmentDTO> page = departmentService.findAll(pageable);
        mav.addObject("listDepartment", page)
                .addObject("messages", "Editing Department details successfully !")
                .addObject("email", accountDetails.getEmail())
                .addObject("avatar_path", accountDetails.getAvatarPath())
                .addObject("id", accountDetails.getEmployeeID())
                .addObject("objectSearchPage", "department");
        return mav;
    }
}
