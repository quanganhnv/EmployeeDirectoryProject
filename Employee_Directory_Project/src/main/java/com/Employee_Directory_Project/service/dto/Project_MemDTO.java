package com.Employee_Directory_Project.service.dto;

import com.Employee_Directory_Project.entities.Employee;
import com.Employee_Directory_Project.entities.Project;

import javax.persistence.*;
import java.sql.Date;
import java.time.ZonedDateTime;

public class Project_MemDTO {
    private int id;
    private String operator;
    private String status;
    private String description;
    private Date start_day;
    private Date end_day;
    private ZonedDateTime created_at;
    private ZonedDateTime updated_at;
    private int employee_id;
    private int project_id;

    private String employee_name;

    private String employee_identity;

    private String employee_department;

    private String project_language;

    private String project_name;

    private String project_framework;

    //////getter and setter/////


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getStart_day() {
        return start_day;
    }

    public void setStart_day(Date start_day) {
        this.start_day = start_day;
    }

    public Date getEnd_day() {
        return end_day;
    }

    public void setEnd_day(Date end_day) {
        this.end_day = end_day;
    }

    public ZonedDateTime getCreated_at() {
        return created_at;
    }

    public void setCreated_at(ZonedDateTime created_at) {
        this.created_at = created_at;
    }

    public ZonedDateTime getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(ZonedDateTime updated_at) {
        this.updated_at = updated_at;
    }

    public int getEmployee_id() {
        return employee_id;
    }

    public void setEmployee_id(int employee_id) {
        this.employee_id = employee_id;
    }

    public int getProject_id() {
        return project_id;
    }

    public void setProject_id(int project_id) {
        this.project_id = project_id;
    }

    public String getEmployee_name() {
        return employee_name;
    }

    public void setEmployee_name(String employee_name) {
        this.employee_name = employee_name;
    }

    public String getProject_name() {
        return project_name;
    }

    public void setProject_name(String project_name) {
        this.project_name = project_name;
    }

    public String getEmployee_identity() {
        return employee_identity;
    }

    public void setEmployee_identity(String employee_identity) {
        this.employee_identity = employee_identity;
    }

    public String getEmployee_department() {
        return employee_department;
    }

    public void setEmployee_department(String employee_department) {
        this.employee_department = employee_department;
    }

    public String getProject_language() {
        return project_language;
    }

    public void setProject_language(String project_language) {
        this.project_language = project_language;
    }

    public String getProject_framework() {
        return project_framework;
    }

    public void setProject_framework(String project_framework) {
        this.project_framework = project_framework;
    }
}
