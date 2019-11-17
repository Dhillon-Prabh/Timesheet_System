package com.corejsf.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.NoResultException;

import com.corejsf.access.CredentialManager;
import com.corejsf.access.EmployeeManager;
import com.corejsf.access.TimesheetManager;
import com.corejsf.model.Credential;
import com.corejsf.model.Employee;
import com.corejsf.model.Timesheet;

@Named("empController")
@ConversationScoped
public class EmployeeController implements Serializable {
    private static final long serialVersionUID = 1L;

    @EJB
    protected EmployeeManager empManager;
    
    @EJB
    protected CredentialManager credManager;
    
    @Inject
    protected CredentialController credController;
    
    @EJB
    private TimesheetManager tsManager;
    
    @Inject 
    private Conversation conversation;
    private Employee currentEmployee;
    private boolean admin;
    
    /**
     * Default no-argument constructor.
     */
    public EmployeeController() { }
    
    public List<Employee> getEmployees() {
        return empManager.getAll();
    }

    public Employee getEmployee(String name) {
        return empManager.getByName(name);
    }

    public Map<String, String> getLoginCombos() {
        return credManager.getCombo();
    }

    public Employee getCurrentEmployee() {
        return currentEmployee;
    }
    
    public boolean getAdmin() {
        return admin;
    }
    
    public List<Timesheet> getAllTimesheets() {
        Timesheet[] tsArray = tsManager.getTimesheets(currentEmployee);
        List<Timesheet> tsList = new ArrayList<Timesheet>();
        for (Timesheet t : tsArray) {
            tsList.add(t);
        }
        return tsList;
    }
    
    public Timesheet getCurrentTimesheet() {
        return tsManager.getCurrentTimesheet(currentEmployee);
    }
    
    
    public boolean verifyUser(Credential cred) {
        Credential credential = credManager.findByUserName(cred.getUserName());
        if (credential == null) {
            return false;
        }
        if (cred.getPassword().equals(credential.getPassword())) {
            currentEmployee = credManager.findByUserName(cred.getUserName()).getEmp();
            credController.setCurrentCred(credManager.findByUserName(cred.getUserName()));
            if (cred.getUserName().equals("admin")) {
                admin = true;
            }
            conversation.begin();
            return true;
        }
        return false;
    }
    
    public String logout() {
        conversation.end();
        currentEmployee = null;
        credController.setCurrentCred(new Credential());
        admin = false;
        return "logOut";
    }
}
