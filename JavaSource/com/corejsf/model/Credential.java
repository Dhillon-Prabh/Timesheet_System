package com.corejsf.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * Model for credential table.
 * @author jham
 * @author psingh
 * @version 1.0
 */
@Entity
@Table(name = "credentials")
public class Credential implements Serializable {
    private static final long serialVersionUID = 1L;
    
    /** primary key of table. */
    @Id
    @Column(name = "CredentialsID")
    private int id;
    
    /** Employee joined to credential. */
    @OneToOne
    @JoinColumn(name = "EmployeeID")
    private Employee emp;

    /** username. */
    @Column(name = "userName")
    private String userName;
    
    /** password. */
    @Column(name = "PW")
    private String password;
    
    /**
     * default constructor.
     */
    public Credential() { };
    
    /**
     * gets the id.
     * @return id
     */
    public int getId() {
        return id;
    }
    
    /**
     * get userName.
     * @return username
     */
    public Employee getEmp() {
        return emp;
    }
    
    /**
     * userName setter.
     * @param emp Employee
     */
    public void setEmp(final Employee emp) {
        this.emp = emp;
    }
    
    /**
     * password getter.
     * @return the password
     */
    public String getPassword() {
        return password;
    }
    
    /**
     * password setter.
     * @param pw the password to set
     */
    public void setPassword(final String pw) {
        password = pw;
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
