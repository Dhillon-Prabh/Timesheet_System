package com.corejsf.timesheets;

import javax.inject.Named;

import ca.bcit.infosys.employee.Credentials;
import ca.bcit.infosys.employee.Employee;

import javax.enterprise.context.SessionScoped; 
 
/**
 * Employee that is currently logged in in a session.
 * 
 * @author hamj1
 * @version 1.0
 */
@Named("user")
@SessionScoped
public class CurrentEmployee extends Employee {
    /** Credentials associated with user. */
    private Credentials credentials = new Credentials();
    /** Whether user is an admin. */
    private boolean isAdmin;
 
    /**
     * Default no-argument constructor. Creates new credentials.
     */
    public CurrentEmployee() {
        credentials = new Credentials();
    }
    
    /**
     * Copies Employee's data over.
     * @param emp Employee to copy data from.
     */
    public void copy(Employee emp) {
        this.setUserName(emp.getUserName());
        this.setName(emp.getName());
        this.setEmpNumber(emp.getEmpNumber());
    }
    
    /**
     * Clears user of data.
     */
    public void clear() {
        credentials = new Credentials();
        setIsAdmin(false);
        setName(null);
        setUserName(null);
        setEmpNumber(0);
    }
    
    /**
     * Credentials getter.
     * @return credentials
     */
    public Credentials getCredentials() {
        return credentials;
    }
    
    /**
     * Credentials setter.
     * @param credentials credentials to set
     */
    public void setCredentials(Credentials credentials) {
        this.credentials = credentials;
    }

    /**
     * isAdmin getter.
     * @return the isAdmin
     */
    public boolean getIsAdmin() {
        return isAdmin;
    }

    /**
     * isAdmin setter.
     * @param isAdmin boolean value to set
     */
    public void setIsAdmin(boolean isAdmin) {
        this.isAdmin = isAdmin;
    }
}
