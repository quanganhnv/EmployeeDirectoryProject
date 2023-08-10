package com.Employee_Directory_Project.entities;

import com.sun.istack.NotNull;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.ZonedDateTime;

@Entity
@Table(name = "account")

public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @NotNull
    @Column(name = "email",unique = true)
    private String email;

    @NotNull
    @Column(name = "username",unique = true)
    private String username;

    @NotNull
    @Column(name = "password")
    private String password;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "employee_id", referencedColumnName = "id", insertable = false, updatable = false)
    private Employee employee;
    @NotNull
    @Column(name = "employee_id")
    private int employee_id;

    @NotNull
    @Column(name = "role_id")
    private String role_id;

    @Column(name = "avatar_path")
    private String avatar_path;
    @NotNull
    @Column(name = "is_active")
    private int status;

    @CreatedDate
    @NotNull
    @Column(name = "created_at", nullable = false, updatable = false)
    private ZonedDateTime createdDate = ZonedDateTime.now();

    @LastModifiedDate
    @Column(name = "updated_at")
    private ZonedDateTime lastModifiedDate = ZonedDateTime.now();

    @LastModifiedDate
    @Column(name = "changed_active_at")
    private ZonedDateTime changedActiveAt = ZonedDateTime.now();

    @Column(name = "remember_token")
    private String rememberToken;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getEmployee_id() {
        return employee_id;
    }

    public void setEmployee_id(int employee_id) {
        this.employee_id = employee_id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getRole_id() {
        return role_id;
    }

    public void setRole_id(String role_id) {
        this.role_id = role_id;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public String getAvatar_path() {
        return avatar_path;
    }

    public void setAvatar_path(String avatar_path) {
        this.avatar_path = avatar_path;
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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public ZonedDateTime getChangedActiveAt() {
        return changedActiveAt;
    }

    public void setChangedActiveAt(ZonedDateTime changedActiveAt) {
        this.changedActiveAt = changedActiveAt;
    }

    public String getRememberToken() {
        return rememberToken;
    }

    public void setRememberToken(String rememberToken) {
        this.rememberToken = rememberToken;
    }

    // getters and setters are not shown for brevity
}
