package com.corejsf.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="employee")
public class Employee implements Serializable{
    private static final long serialVersionUID = 1L;
    
    @Id
    @Column(name="EmployeeID")
    private int id;

    @Column(name="EmployeeNumber")
    private int empNumber;
    
    @Column(name="Name")
    private String name;

    @Column(name="UserName")
    private String userName;
    
    public Employee() {};
    
    public Employee(final String empName, final int number, final String id) {
        name = empName;
        empNumber = number;
        userName = id;
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getEmpNumber() {
        return empNumber;
    }

    public void setEmpNumber(int empNumber) {
        this.empNumber = empNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
