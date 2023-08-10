package com.Employee_Directory_Project.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import com.sun.istack.NotNull;
import org.aspectj.bridge.IMessage;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;

import javax.annotation.sql.DataSourceDefinition;
import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;

import static javax.persistence.TemporalType.TIMESTAMP;

@Component
@Entity
@Table(name = "project")
public class Project implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name = "name",unique = true)
    private String name;

    @Column(name = "language")
    private String language;

    @NotNull
    @Column(name = "framework")
    private String framework;

    @Column(name = "operator")
    private String operator;

    @Column(name = "os")
    private String os;

    @Column(name = "description")
    private String description;

    @Column(name = "total_size")
    private int total_size;

    @Column(name = "total_cost")
    private float total_cost;

    @Column(name = "reference")
    private String reference;
    @NotNull
    @Column(name = "start_day")
    private Date start_day;

    @Column(name = "end_day")
    private Date end_day;

    @LastModifiedDate
    @Column(name = "updated_at")
    private ZonedDateTime updated_at ;

    @CreatedDate
    @NotNull
    @Column(name = "created_at")
    private ZonedDateTime created_at ;

    @Column(name = "pm_id")
    @NotNull
    private int pm_id;

    @JsonIgnore
    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL)
    private Set<Project_Mem> project_Mems = new HashSet<>();


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pm_id", referencedColumnName = "id", insertable = false, updatable = false)
    private Employee employee;


    //////// Getter And Setter   ///////////////



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getFramework() {
        return framework;
    }

    public void setFramework(String framework) {
        this.framework = framework;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getOs() {
        return os;
    }

    public void setOs(String os) {
        this.os = os;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getTotal_size() {
        return total_size;
    }

    public void setTotal_size(int total_size) {
        this.total_size = total_size;
    }

    public float getTotal_cost() {
        return total_cost;
    }

    public void setTotal_cost(float total_cost) {
        this.total_cost = total_cost;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
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

    public ZonedDateTime getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(ZonedDateTime updated_at) {
        this.updated_at = updated_at;
    }

    public ZonedDateTime getCreated_at() {
        return created_at;
    }

    public void setCreated_at(ZonedDateTime created_at) {
        this.created_at = created_at;
    }

    public int getPm_id() {
        return pm_id;
    }

    public void setPm_id(int pm_id) {
        this.pm_id = pm_id;
    }

    public Set<Project_Mem> getProject_Mems() {
        return project_Mems;
    }

    public void setProject_Mems(Set<Project_Mem> project_Mems) {
        this.project_Mems = project_Mems;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }
}
