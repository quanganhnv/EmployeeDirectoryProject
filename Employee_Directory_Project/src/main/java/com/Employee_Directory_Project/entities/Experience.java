package com.Employee_Directory_Project.entities;


import com.sun.istack.NotNull;
import org.hibernate.annotations.Fetch;

import javax.persistence.*;
import java.math.BigInteger;
import java.sql.Date;
import java.time.ZonedDateTime;

@Entity
@Table(name = "experience")
public class Experience {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private int id;

    @Column(name="name")
    private String name;
    @NotNull
    @Column(name="language")
    private String language;
    @NotNull
    @Column(name="framework")
    private String framework;

    @Column(name="operator")
    private String operator;

    @Column(name="os")
    private String os;

    @Column(name="description")
    private String description;

    @Column(name="reference")
    private String reference;
    @NotNull
    @Column(name="total_size")
    private int total_size;
    @NotNull
    @Column(name="total_cost")
    private BigInteger total_cost;
    @NotNull
    @Column(name="start_day")
    private Date start_day;

    @NotNull
    @Column(name="end_day")
    private Date end_day;
    @NotNull
    @Column(name="created_at")
    private ZonedDateTime created_at;

    @Column(name = "updated_at")
    private ZonedDateTime updated_at;

    @Column(name = "employee_id")
    private int employee_id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id", referencedColumnName = "id", insertable = false, updatable = false)
    private Employee employee;

    /////////Getter and Setter //////////


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

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public int getTotal_size() {
        return total_size;
    }

    public void setTotal_size(int total_size) {
        this.total_size = total_size;
    }

    public BigInteger getTotal_cost() {
        return total_cost;
    }

    public void setTotal_cost(BigInteger total_cost) {
        this.total_cost = total_cost;
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

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }
}
