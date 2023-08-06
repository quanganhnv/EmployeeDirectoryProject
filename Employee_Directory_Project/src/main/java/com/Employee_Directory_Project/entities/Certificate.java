package com.Employee_Directory_Project.entities;


import javax.persistence.*;
import java.sql.Date;

@Entity

@Table(name = "certificate")
public class Certificate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private int id;

    @Column(name="name")
    private String name;

    @Column(name="authorization")
    private String authorization;

    @Column(name="description")
    private String description;

    @Column(name="effective_day")
    private Date effective_day;

    @Column(name="expiry_date")
    private Date expiry_date;

    @Column(name="created_at")
    private String created_at;

    @Column(name = "updated_at")
    private String updated_at;

    @ManyToOne
    @JoinColumn(name = "employee_id" , referencedColumnName = "id")
    private Employee employee;

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

    public String getAuthorization() {
        return authorization;
    }

    public void setAuthorization(String authorization) {
        this.authorization = authorization;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getEffective_day() {
        return effective_day;
    }

    public void setEffective_day(Date effective_day) {
        this.effective_day = effective_day;
    }

    public Date getExpiry_date() {
        return expiry_date;
    }

    public void setExpiry_date(Date expiry_date) {
        this.expiry_date = expiry_date;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }
}
