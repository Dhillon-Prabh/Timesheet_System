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
    
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
    @Column(name="PW")
    private String password;
    
    public Credential() {};
    
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
}
