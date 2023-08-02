package com.Employee_Directory_Project.controller;

import com.Employee_Directory_Project.repository.DepartmentRepository;
import com.Employee_Directory_Project.service.DepartmentService;
import com.Employee_Directory_Project.service.dto.DepartmentDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.time.ZonedDateTime;
import java.util.Optional;

@RestController
@RequestMapping("/admin/department")
public class DepartmentController {
    private final DepartmentService departmentService;

    private final DepartmentRepository departmentRepository;

    public DepartmentController(DepartmentService departmentService, DepartmentRepository departmentRepository) {
        this.departmentService = departmentService;
        this.departmentRepository = departmentRepository;
    }

    @GetMapping("/index")
    public ModelAndView getAllDepartment(Pageable pageable) {
        ModelAndView mav = new ModelAndView("/department/index");
        Page<DepartmentDTO> page = departmentService.findAll(pageable);
        mav.addObject("listDepartment", page);
        return mav;
    }

    @GetMapping("/details/{id}")
    public ModelAndView getDepartment(@PathVariable int id) {
        Optional<DepartmentDTO> departmentDTO = departmentService.findOne(id);
        ModelAndView mav = new ModelAndView("/department/details");
        if (departmentDTO.isPresent()) {
            mav.addObject("department", departmentDTO.get());
            return mav;
        }
        mav.addObject("error", "Can't find department details");
        return mav;
    }

    @GetMapping("/edit/{id}")
    public ModelAndView edit(@PathVariable int id) {
        ModelAndView mav = new ModelAndView("/department/edit");
        mav.addObject("department", departmentService.findOne(id).get());
        return mav;
    }

    @GetMapping("/delete/{id}")
    public ModelAndView delete(@PathVariable Integer id, Pageable pageable) {
        ModelAndView mav = new ModelAndView("/department/index");
        departmentService.delete(id);
        Page<DepartmentDTO> page = departmentService.findAll(pageable);
        mav.addObject("listDepartment", page);
        mav.addObject("messages", "Delete Success !");
        return mav;
    }

    @GetMapping("/add")
    public ModelAndView addDepartment(){
        ModelAndView mav = new ModelAndView("/department/add");
        return mav;
    }

    @PostMapping("/add")
    public ModelAndView createDepartment (@ModelAttribute("department") DepartmentDTO departmentDTO) {
        ModelAndView mav = new ModelAndView("/department/index");
        DepartmentDTO result = departmentService.save(departmentDTO);
        return mav;
    }

    @PostMapping("/edit/{id}")
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

        ModelAndView mav = new ModelAndView("/department/index");
        Page<DepartmentDTO> page = departmentService.findAll(pageable);
        mav.addObject("listDepartment", page);
        mav.addObject("messages", "Editing Department details successfully !");
        return mav;
    }
}
