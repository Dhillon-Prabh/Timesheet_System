package com.corejsf.timesheets;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.enterprise.context.ApplicationScoped;

import ca.bcit.infosys.employee.Employee;

/**
 * Simulated database holding employee data and credential data.
 * 
 * @author jham
 * @version 1.0
 */
@ApplicationScoped
public class EmployeeDatabase implements Serializable {
    /** List of existing employees. */
    private List<Employee> employees = new ArrayList<Employee>();
    /** Map containing username and corresponding password. */
    private Map<String, String> loginCombo = new HashMap<String, String>();
    /** List of employees that are currently logged in. */
    private List<Employee> loginEmployees = new ArrayList<Employee>();
    
    /**
     * Generates a simulated database with a default admin.
     */
    public EmployeeDatabase() {
        Employee admin = new Employee("admin", 0, "admin");
        loginCombo.put("admin", "admin");
        employees.add(admin);
    };

    /**
     * Employee list getter.
     * @return ArrayList of Employees
     */
    public List<Employee> getEmployees() {
        return employees;
    }
    
    /**
     * Login combo getter.
     * @return HashMap of username and passwords
     */
    public Map<String, String> getLoginCombo() {
        return loginCombo;
    }
    
    /**
     * Logged in employees getter.
     * @return ArrayList of Employees
     */
    public List<Employee> getLoginEmployees() {
        return loginEmployees;
    }
}
