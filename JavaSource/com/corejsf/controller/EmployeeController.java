package com.corejsf.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.inject.Inject;
import javax.inject.Named;

import com.corejsf.access.CredentialManager;
import com.corejsf.access.EmployeeManager;
import com.corejsf.access.TimesheetManager;
import com.corejsf.model.Credential;
import com.corejsf.model.Employee;
import com.corejsf.model.Timesheet;

@Named("empController")
@SessionScoped
public class EmployeeController implements Serializable {
    private static final long serialVersionUID = 1L;

    @EJB
    private EmployeeManager empManager;
    
    @EJB
    private CredentialManager credManager;
    
    @Inject
    private CredentialController credController;
    
    @EJB
    private TimesheetManager tsManager;
    
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
    
    public List<Timesheet> getAllTimesheets(Employee emp) {
        Timesheet[] tsArray = tsManager.getTimesheets(emp);
        List<Timesheet> tsList = new ArrayList<Timesheet>();
        for (Timesheet t : tsArray) {
            tsList.add(t);
        }
        return tsList;
    }
    
    public Timesheet getCurrentTimesheet() {
        return tsManager.getCurrentTimesheet(currentEmployee);
    }
    
    public void validateLogin(FacesContext context, UIComponent component, Object value) {

        UIInput loginID = (UIInput) component.findComponent("loginID");
        UIInput loginPassword = (UIInput) component.findComponent("loginPassword");
     
        String username = (String) loginID.getLocalValue();
        String password = (String) loginPassword.getSubmittedValue();
        Credential tempCred = new Credential();
        tempCred.setUserName(username);
        tempCred.setPassword(password);

        if (!verifyUser(tempCred)) {
            FacesMessage message = com.corejsf.util.Messages.getMessage(
                    "com.corejsf.controller.messages", "loginFail", null);
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(message);
        }
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
            return true;
        }
        return false;
    }
    
    public String logout() {
        currentEmployee = null;
        credController.setCurrentCred(new Credential());
        admin = false;
        return "logOut";
    }
}
