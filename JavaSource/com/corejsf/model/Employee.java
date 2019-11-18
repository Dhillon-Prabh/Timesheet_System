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
    
    /**
     * default constructor for the employee
     */
    public Employee() {};
    
    /**
     * Employee constructor
     * @param empName
     * @param number
     * @param id
     */
    public Employee(final String empName, final int number, final String id) {
        name = empName;
        empNumber = number;
        userName = id;
    }
    
    /**
     * returns the id
     * @return
     */
    public int getId() {
        return id;
    }

    /**
     * returns the emp number
     * @return
     */
    public int getEmpNumber() {
        return empNumber;
    }

    /**
     * sets the emp number
     * @param empNumber
     */
    public void setEmpNumber(int empNumber) {
        this.empNumber = empNumber;
    }

    /**
     * gets the emp name
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     * sets the emp name
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * gets the user name
     * @return
     */
    public String getUserName() {
        return userName;
    }

    /**
     * sets the user name
     * @param userName
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }
}
