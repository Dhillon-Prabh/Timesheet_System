package com.corejsf.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Model for employee table.
 * @author jham
 * @author psingh
 * @version 1.0
 */
@Entity
@Table(name = "employee")
public class Employee implements Serializable {
    private static final long serialVersionUID = 1L;
    
    /** primary key. */
    @Id
    @Column(name = "EmployeeID")
    private int id;

    /** employee number. */
    @Column(name = "EmployeeNumber")
    private int empNumber;
    
    /** name. */
    @Column(name = "Name")
    private String name;

    /** username. */
    @Column(name = "UserName")
    private String userName;
    
    /**
     * default constructor for the employee.
     */
    public Employee() { };
    
    /**
     * Employee constructor.
     * @param empName name
     * @param number employee number
     * @param id username
     */
    public Employee(final String empName, final int number, final String id) {
        name = empName;
        empNumber = number;
        userName = id;
    }
    
    /**
     * returns the id.
     * @return id
     */
    public int getId() {
        return id;
    }

    /**
     * returns the emp number.
     * @return emp number
     */
    public int getEmpNumber() {
        return empNumber;
    }

    /**
     * sets the emp number.
     * @param empNumber emp number
     */
    public void setEmpNumber(int empNumber) {
        this.empNumber = empNumber;
    }

    /**
     * gets the emp name.
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * sets the emp name.
     * @param name name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * gets the user name.
     * @return username
     */
    public String getUserName() {
        return userName;
    }

    /**
     * sets the user name.
     * @param userName username
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }
}
