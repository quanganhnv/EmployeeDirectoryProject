package com.Employee_Directory_Project.service.dto;

import com.Employee_Directory_Project.entities.Department;
import com.Employee_Directory_Project.entities.Employee;
import com.Employee_Directory_Project.entities.Experience;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Set;

public class EmployeeDTO implements Serializable {
    private Integer id;

    private String firstName;

    private String lastName;

    private String fullName;

    private String birthday;

    private String phone;

    private String address;

    private String department_id;

    private String department_name;

    private String role_id;

    private String email;

    private String image_path;

    private int gender;

    private String username;

    private String password;

    private String identity_number;

    private String issued_on;

    private String issued_by;

    private List<Project_MemDTO> project_MemDTOs ;

    private List<ExperienceDTO> ExperienceDTOs ;

    private List<SkillDTO> SkillDTOs ;

    private List<ProjectDTO> projectDTOs ;

    private ZonedDateTime createdDate = ZonedDateTime.now();

    private ZonedDateTime lastModifiedDate = ZonedDateTime.now();


    ////////////Getter And Setter ////////////

    public String getFullName() {
        String firstName = this.firstName;
        String lastName = this.lastName;
        return firstName + " " + lastName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDepartment_id() {
        return department_id;
    }

    public void setDepartment_id(String department_id) {
        this.department_id = department_id;
    }

    public String getDepartment_name() {
        return department_name;
    }

    public void setDepartment_name(String department_name) {
        this.department_name = department_name;
    }

    public String getRole_id() {
        return role_id;
    }

    public void setRole_id(String role_id) {
        this.role_id = role_id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImage_path() {
        return image_path;
    }

    public void setImage_path(String image_path) {
        this.image_path = image_path;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getIdentity_number() {
        return identity_number;
    }

    public void setIdentity_number(String identity_number) {
        this.identity_number = identity_number;
    }

    public String getIssued_on() {
        return issued_on;
    }

    public void setIssued_on(String issued_on) {
        this.issued_on = issued_on;
    }

    public String getIssued_by() {
        return issued_by;
    }

    public void setIssued_by(String issued_by) {
        this.issued_by = issued_by;
    }

    public List<Project_MemDTO> getProject_MemDTOs() {
        return project_MemDTOs;
    }

    public void setProject_MemDTOs(List<Project_MemDTO> project_MemDTOs) {
        this.project_MemDTOs = project_MemDTOs;
    }

    public List<ExperienceDTO> getExperienceDTOs() {
        return ExperienceDTOs;
    }

    public void setExperienceDTOs(List<ExperienceDTO> experienceDTOs) {
        ExperienceDTOs = experienceDTOs;
    }

    public List<SkillDTO> getSkillDTOs() {
        return SkillDTOs;
    }

    public void setSkillDTOs(List<SkillDTO> skillDTOs) {
        SkillDTOs = skillDTOs;
    }

    public List<ProjectDTO> getProjectDTOs() {
        return projectDTOs;
    }

    public void setProjectDTOs(List<ProjectDTO> projectDTOs) {
        this.projectDTOs = projectDTOs;
    }

    public ZonedDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(ZonedDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public ZonedDateTime getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(ZonedDateTime lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }
}
