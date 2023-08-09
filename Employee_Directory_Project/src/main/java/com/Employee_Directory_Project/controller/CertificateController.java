package com.Employee_Directory_Project.controller;

import com.Employee_Directory_Project.repository.CertificateRepository;
import com.Employee_Directory_Project.security.AccountDetails;
import com.Employee_Directory_Project.service.CertificateService;
import com.Employee_Directory_Project.service.EmployeeService;
import com.Employee_Directory_Project.service.dto.CertificateDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.time.ZonedDateTime;

@RestController
@RequestMapping("/certificate")
public class CertificateController {
    private final CertificateService certificateService;

    private final CertificateRepository certificateRepository;

    private final EmployeeService employeeService;

    public CertificateController(CertificateService certificateService, CertificateRepository certificateRepository, EmployeeService employeeService) {
        this.certificateService = certificateService;
        this.certificateRepository = certificateRepository;
        this.employeeService = employeeService;
    }

    @GetMapping("/index")
    public ModelAndView index(@RequestParam(value = "textSearch", required = false, defaultValue = "") String textSearch, Pageable pageable){
        ModelAndView mav = new ModelAndView("/certificate/index");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        AccountDetails accountDetails = (AccountDetails)authentication.getPrincipal();

        Page<CertificateDTO> listCertificate = certificateService.findAll(accountDetails.getEmployeeID(),textSearch, pageable);

        mav.addObject("listCertificate", listCertificate)
                .addObject("id", accountDetails.getEmployeeID())
                .addObject("employee_name",  accountDetails.getFullName())
                .addObject("email", accountDetails.getEmail())
                .addObject("avatar_path", accountDetails.getAvatarPath())
                .addObject("objectSearchPage", "certificate");

        return mav;
    }

    @GetMapping("/index/search")
    public ModelAndView search(@RequestParam(value = "textSearch", required = true) String textSearch, Pageable pageable) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        AccountDetails accountDetails = (AccountDetails)authentication.getPrincipal();
        ModelAndView mav = new ModelAndView("/certificate/index");
        Page<CertificateDTO> page = certificateService.findAll(accountDetails.getEmployeeID(), textSearch, pageable);

        mav.addObject("listCertificate", page)
                .addObject("id", accountDetails.getEmployeeID())
                .addObject("email", accountDetails.getEmail())
                .addObject("avatar_path", accountDetails.getAvatarPath())
                .addObject("objectSearchPage", "certificate");
        return mav;
    }

    @GetMapping("/delete/{id}")
    public ModelAndView delete(@RequestParam(value = "textSearch", required = false, defaultValue = "") String textSearch, @PathVariable Integer id, Pageable pageable) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        AccountDetails accountDetails = (AccountDetails)authentication.getPrincipal();
        ModelAndView mav = new ModelAndView("/certificate/index");
        certificateService.delete(id);
        Page<CertificateDTO> page = certificateService.findAll(accountDetails.getEmployeeID(),textSearch,pageable);
        mav.addObject("listCertificate", page)
                .addObject("id", accountDetails.getEmployeeID())
                .addObject("employee_name",  accountDetails.getFullName())
                .addObject("messages", "Delete Success !")
                .addObject("email", accountDetails.getEmail())
                .addObject("avatar_path", accountDetails.getAvatarPath())
                .addObject("objectSearchPage", "certificate");
        return mav;
    }

    @GetMapping("/add")
    public ModelAndView addCertificate(Pageable pageable){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        AccountDetails accountDetails = (AccountDetails)authentication.getPrincipal();
        CertificateDTO certificateDTO = new CertificateDTO();
        ModelAndView mav = new ModelAndView("/certificate/add");
        mav.addObject("certificate", certificateDTO)
                .addObject("id", accountDetails.getEmployeeID())
                .addObject("email", accountDetails.getEmail())
                .addObject("avatar_path", accountDetails.getAvatarPath())
                .addObject("objectSearchPage", "certificate");
        return mav;
    }

    @PostMapping("/add")
    public ModelAndView createCertificate (@ModelAttribute("certificate") CertificateDTO certificateDTO, Pageable pageable) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        AccountDetails accountDetails = (AccountDetails)authentication.getPrincipal();
        certificateDTO.setEmployee_id(accountDetails.getEmployeeID());
        CertificateDTO result = certificateService.save(certificateDTO);
        ModelAndView mav = new ModelAndView("/certificate/index");
        Page<CertificateDTO> listCertificate = certificateService.findAll(accountDetails.getEmployeeID(),"", pageable);
        mav.addObject("listCertificate", listCertificate)
                .addObject("id", accountDetails.getEmployeeID())
                .addObject("employee_name",  accountDetails.getFullName())
                .addObject("email", accountDetails.getEmail())
                .addObject("avatar_path", accountDetails.getAvatarPath())
                .addObject("objectSearchPage", "certificate");
        return mav;
    }

    @GetMapping("/edit/{id}")
    public ModelAndView edit(@PathVariable Integer id){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        AccountDetails accountDetails = (AccountDetails)authentication.getPrincipal();
        ModelAndView mav = new ModelAndView("/certificate/edit");
        mav.addObject("certificate", certificateService.findOne(accountDetails.getEmployeeID(),id).get())
                .addObject("email", accountDetails.getEmail())
                .addObject("id", accountDetails.getEmployeeID())
                .addObject("avatar_path", accountDetails.getAvatarPath())
                .addObject("objectSearchPage", "certificate");
        return mav;
    }

    @PostMapping("/edit/{id}")
    public ModelAndView editCertificate(@RequestParam(value = "textSearch", required = false, defaultValue = "") String textSearch, @PathVariable int id, @ModelAttribute("certificate") CertificateDTO newCertificateDTO, Pageable pageable ) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        AccountDetails accountDetails = (AccountDetails)authentication.getPrincipal();
        certificateService.findOne(accountDetails.getEmployeeID(),id).map(CertificateDTO -> {
            CertificateDTO.setName(newCertificateDTO.getName());
            CertificateDTO.setEmployee_id(newCertificateDTO.getEmployee_id());
            CertificateDTO.setEffective_day(newCertificateDTO.getEffective_day());
            CertificateDTO.setExpiry_date(newCertificateDTO.getExpiry_date());
            CertificateDTO.setAuthorization(newCertificateDTO.getAuthorization());
            CertificateDTO.setLastModifiedDate(ZonedDateTime.now());
            return certificateService.save(newCertificateDTO);
        })
                .orElseGet(() -> {
                    newCertificateDTO.setId(id);
                    return certificateService.save(newCertificateDTO);
                });

        ModelAndView mav = new ModelAndView("/certificate/index");
        Page<CertificateDTO> page = certificateService.findAll(accountDetails.getEmployeeID(), textSearch, pageable);
        mav.addObject("listCertificate", page)
                .addObject("messages", "Editing Certificate details successfully !")
                .addObject("employee_name",  accountDetails.getFullName())
                .addObject("email", accountDetails.getEmail())
                .addObject("avatar_path", accountDetails.getAvatarPath())
                .addObject("id", accountDetails.getEmployeeID())
                .addObject("objectSearchPage", "certificate");
        return mav;
    }
}
