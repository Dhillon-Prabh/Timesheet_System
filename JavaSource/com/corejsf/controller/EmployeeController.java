package com.corejsf.controller;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import com.corejsf.access.CredentialManager;
import com.corejsf.access.EmployeeManager;
import com.corejsf.access.TimesheetManager;
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
    
    @EJB
    private TimesheetManager tsManager;
    
    private Employee currentEmployee;
    
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

    public Employee getAdministrator() {
        return getEmployee("admin");
    }

    public Timesheet getCurrentTimesheet() {
        return tsManager.getCurrentTimesheet(currentEmployee);
    }
    
    public boolean verifyUser(String username, String password) {
        if (password.equals(credManager.findByUserName(username).getPassword())) {
            currentEmployee = credManager.findByUserName(username).getEmp();
            return true;
        }
        return false;
    }

    public String logout(Employee employee) {
        currentEmployee = null;
        return "logOut";
    }

    public void deleteEmployee(Employee userToDelete) {
        empManager.remove(userToDelete);
    }

    public void addEmployee(Employee newEmployee) {
        empManager.persist(newEmployee);
    }
}
