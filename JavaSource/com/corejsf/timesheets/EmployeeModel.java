package com.corejsf.timesheets;

import java.util.List;
import java.util.Map;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import ca.bcit.infosys.employee.Credentials;
import ca.bcit.infosys.employee.Employee;
import ca.bcit.infosys.employee.EmployeeList;

/**
 * Access class to back end to access Employees and verify login credentials.
 * 
 *  @author John Ham
 *  @version 1.0
 */
@Named("employeeDatabase")
@SessionScoped
public class EmployeeModel implements EmployeeList {

    /** Database to get employee data. */
    @Inject
    private EmployeeDatabase db;
    /** Employee that is currently logged in. */
    @Inject
    private CurrentEmployee currentEmployee = new CurrentEmployee();
    /** CDI bean to change password. */
    @Inject
    private PasswordModel cp;
    
    /**
     * Default no-argument constructor.
     */
    public EmployeeModel() { }
    
    @Override
    public List<Employee> getEmployees() {
        return db.getEmployees();
    }

    @Override
    public Employee getEmployee(String name) {
        for (Employee emp : db.getEmployees()) {
            if (emp.getName().equals(name)) {
                return emp;
            }
        }
        return null;
    }

    @Override
    public Map<String, String> getLoginCombos() {
        return db.getLoginCombo();
    }

    @Override
    public Employee getCurrentEmployee() {
        return currentEmployee;
    }

    @Override
    public Employee getAdministrator() {
        return getEmployee("admin");
    }

    @Override
    public boolean verifyUser(Credentials credential) {
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
    
    @Override
    public String logout(Employee employee) {
        db.getLoginEmployees().remove(currentEmployee);
        currentEmployee.clear();
        return "logOut";
    }

    @Override
    public void deleteEmployee(Employee userToDelete) {
        db.getLoginCombo().remove(userToDelete.getUserName());
        getEmployees().remove(userToDelete);
    }

    @Override
    public void addEmployee(Employee newEmployee) {
        cp.setPassword(newEmployee);
        getEmployees().add(newEmployee);
    }
}
