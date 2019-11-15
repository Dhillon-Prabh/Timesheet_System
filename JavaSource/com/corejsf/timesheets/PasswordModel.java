package com.corejsf.timesheets;

import java.io.Serializable;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import ca.bcit.infosys.employee.Employee;

/**
 * Access class to back end to access login credentials.
 * 
 * @author jham
 * @version 1.0
 */
@Named("password")
@RequestScoped
public class PasswordModel implements Serializable {

    /** Database to get credential data. */
    @Inject
    private EmployeeDatabase db;
    /** User input for current password. */
    private String curPassword;
    /** User input for password to change to. */
    private String newPassword;
    /** User input for confirming password to change to. */
    private String confirmNewPassword;
    
    /**
     * Default no-argument constructor.
     */
    public PasswordModel() { }
    
    /**
     * Sets a password for a user.
     * 
     * @param emp employee to set password
     */
    public void setPassword(Employee emp) {
        if (newPassword.equals(confirmNewPassword)) {
            db.getLoginCombo().put(emp.getUserName(), newPassword);
        }
    }
    
    /**
     * Changes the password of a user.
     * 
     * @param emp employee to change password
     */
    public void changePassword(Employee emp) {
        String curPassword2 = db.getLoginCombo().get(emp.getUserName());
        if (!curPassword.equals(curPassword2)) {
            return;
        }
        setPassword(emp);
    }

    /**
     * Current password input getter.
     * @return current password input
     */
    public String getCurPassword() {
        return curPassword;
    }

    /**
     * Current password input setter.
     * @param curPassword password to set
     */
    public void setCurPassword(String curPassword) {
        this.curPassword = curPassword;
    }

    /**
     * New password getter.
     * @return new password input
     */
    public String getNewPassword() {
        return newPassword;
    }

    /**
     * New password setter.
     * @param newPassword password to set
     */
    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
    
    /**
     * Confirm new password getter.
     * @return confirm password input
     */
    public String getconfirmNewPassword() {
        return confirmNewPassword;
    }

    /**
     * Confirm new password setter.
     * @param newValue password to set
     */
    public void setconfirmNewPassword(String newValue) {
        this.confirmNewPassword = newValue;
    }
}
