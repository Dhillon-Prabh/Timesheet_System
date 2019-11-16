package com.corejsf.controller;

import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.corejsf.access.CredentialManager;
import com.corejsf.access.EmployeeManager;
import com.corejsf.model.Employee;

import ca.bcit.infosys.employee.Credentials;
import ca.bcit.infosys.employee.EmployeeList;

@Named("EmpController")
@SessionScoped
public class EmployeeController {

    @EJB
    private EmployeeManager empManager;
    
    @EJB
    private CredentialManager credManager;
    
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

    public boolean verifyUser(Credentials credential) {
        if (credential.getPassword().equals(credManager.findByUserName(credential.getUserName()))) {
            currentEmployee = credManager.findByUserName(credential.getUserName()).getUserName();
        }
        
        String username = credential.getUserName();
        if (credential.getPassword().equals(db.getLoginCombo().get(username))) {
            for (Employee emp : getEmployees()) {
                if (emp.getUserName().equals(username)) {
                    currentEmployee.copy(emp);
                }
            }
            if (currentEmployee.getUserName().equals("admin")) {
                currentEmployee.setIsAdmin(true);
            }
            db.getLoginEmployees().add(currentEmployee);
            return true;
        }
        return false;
    }
    
    public String logout(Employee employee) {
        db.getLoginEmployees().remove(currentEmployee);
        currentEmployee.clear();
        return "logOut";
    }

    public void deleteEmployee(Employee userToDelete) {
        db.getLoginCombo().remove(userToDelete.getUserName());
        getEmployees().remove(userToDelete);
    }

    public void addEmployee(Employee newEmployee) {
        cp.setPassword(newEmployee);
        getEmployees().add(newEmployee);
    }
}
