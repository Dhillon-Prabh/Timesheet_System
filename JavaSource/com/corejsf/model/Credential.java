package com.corejsf.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
    @JoinColumn(name="UserName")
    private String userName;

    @Column(name="PW")
    private String password;
    
    public Credential() {};
    
    /**
     * get userName
     * @return
     */
    public String getUserName() {
        return userName;
    }
    /**
     * userName setter.
     * @param id the loginID to set
     */
    public void setUserName(final String id) {
        userName = id;
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
