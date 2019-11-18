package com.corejsf.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="credentials")
public class Credential implements Serializable{
    private static final long serialVersionUID = 1L;
    
    @Id
    @Column(name="CredentialsID")
    private int id;
    
    @OneToOne
    @JoinColumn(name="EmployeeID")
    private Employee emp;

    @Column(name="userName")
    private String userName;
    
    @Column(name="PW")
    private String password;
    
    /**
     * default constructor
     */
    public Credential() {};
    
    /**
     * gets the id
     * @return
     */
    public int getId() {
        return id;
    }
    
    /**
     * get userName
     * @return
     */
    public Employee getEmp() {
        return emp;
    }
    
    /**
     * userName setter.
     * @param id the loginID to set
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
