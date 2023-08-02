package com.Employee_Directory_Project.controller;

import com.Employee_Directory_Project.repository.DepartmentRepository;
import com.Employee_Directory_Project.repository.EmployeeRepository;
import com.Employee_Directory_Project.service.DepartmentService;
import com.Employee_Directory_Project.service.EmployeeService;
import com.Employee_Directory_Project.service.dto.DepartmentDTO;
import com.Employee_Directory_Project.service.dto.EmployeeDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/admin")
public class AdminController {
    private final DepartmentService departmentService;

    private final DepartmentRepository departmentRepository;

    public AdminController(DepartmentService departmentService, DepartmentRepository departmentRepository) {
        this.departmentService = departmentService;
        this.departmentRepository = departmentRepository;
    }

    @GetMapping("/page-list-department")
    public ModelAndView getAllDepartment(Pageable pageable) {
        ModelAndView mav = new ModelAndView("/page-list-department");
        Page<DepartmentDTO> page = departmentService.findAll(pageable);
        mav.addObject("listDepartment", page);
        return mav;
    }

    @GetMapping("/page-detail-department/{id}")
    public ModelAndView getDepartment(@PathVariable int id) {
        Optional<DepartmentDTO> departmentDTO = departmentService.findOne(id);
        ModelAndView mav = new ModelAndView("/page-detail-department");
        if (departmentDTO.isPresent()) {
            mav.addObject("department", departmentDTO.get());
            return mav;
        }
        mav.addObject("error", "Can't find department details");
        return mav;
    }

    @GetMapping("/page-list-department/edit/{id}")
    public ModelAndView edit(@PathVariable int id) {
        ModelAndView mav = new ModelAndView("/page-edit-department");
        mav.addObject("department", departmentService.findOne(id).get());
        return mav;
    }

    @GetMapping("/page-list-department/delete/{id}")
    public ModelAndView delete(@PathVariable Integer id, Pageable pageable) {
        ModelAndView mav = new ModelAndView("/page-list-department");
        departmentService.delete(id);
        Page<DepartmentDTO> page = departmentService.findAll(pageable);
        mav.addObject("listDepartment", page);
        mav.addObject("messages", "Delete Success !");
        return mav;
    }

    @GetMapping("/page-add-department")
    public ModelAndView addDepartment(){
        ModelAndView mav = new ModelAndView("/page-add-department");
        return mav;
    }

    @PostMapping("/page-add-department")
    public ModelAndView createDepartment (@ModelAttribute("department") DepartmentDTO departmentDTO) {
        ModelAndView mav = new ModelAndView("/admin/page-list-department");
        DepartmentDTO result = departmentService.save(departmentDTO);
        return mav;
    }

    @PostMapping("/page-edit-department/{id}")
    public ModelAndView editDepartment(@PathVariable int id, @ModelAttribute("department") DepartmentDTO newDepartmentDTO, Pageable pageable ) {
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

        ModelAndView mav = new ModelAndView("/page-list-department");
        Page<DepartmentDTO> page = departmentService.findAll(pageable);
        mav.addObject("listDepartment", page);
        mav.addObject("messages", "Editing Department details successfully !");
        return mav;
    }
}
