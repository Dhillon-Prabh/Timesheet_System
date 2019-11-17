package com.corejsf.controller;

import javax.inject.Named;

import com.corejsf.model.Credential;
import com.corejsf.model.Employee;

@Named("adminController")
public class AdminController extends EmployeeController {
    private static final long serialVersionUID = 1L;
    
    /** Employee username input. */
    private String userName;
    /** Employee number input. */
    private Integer empNumber;
    /** Employee name input. */
    private String name;
    /** Temporary employee. */
    private Employee employee;
    /** Whether user is adding employee. */
    private boolean addEmp;
    /** Whether user is updating employee. */
    private boolean updateEmp;
    /** Whether user has found an employee. */
    private boolean foundEmp;
    /** Whether user is removing employee. */
    private boolean removeEmp;

    /**
     * Default no-argument constructor. Sets boolean values so that necessary
     * text is rendered.
     */
    public AdminController() {
        foundEmp = false;
        addEmp = false;
        updateEmp = true;
        removeEmp = false;
    }
    
    public void deleteEmployee() {
        Credential cred = credManager.findByUserName(userName);
        credManager.remove(cred);
        empManager.remove(employee);
        clear();
    }

    public void addEmployee(String password, String newPassword) {
        if (!password.equals(newPassword)) {
            return;
        }
        employee = new Employee();
        employee.setEmpNumber(empNumber);
        employee.setName(name);
        employee.setUserName(userName);
        empManager.persist(employee);
        Credential cred = new Credential();
        cred.setUserName(userName);
        cred.setPassword(password);
        cred.setEmp(getEmployee(name));
        credManager.persist(cred);
        clear();
    }
    
    /**
     * Sets boolean values to render necessary text when adding users.
     */
    public void add() {
        clear();
        foundEmp = false;
        updateEmp = false;
        addEmp = true;
        removeEmp = false;
    }

    /**
     * Sets boolean values to render necessary text when updating users.
     */
    public void update() {
        clear();
        foundEmp = false;
        updateEmp = true;
        addEmp = false;
        removeEmp = false;
    }

    /**
     * Sets boolean values to render necessary text when removing users.
     */
    public void remove() {
        clear();
        foundEmp = false;
        updateEmp = false;
        addEmp = false;
        removeEmp = true;
    }

    /**
     * Sets boolean values to render necessary text when finding users.
     * @param emp Employee to find
     */
    public void findEmp(String name) {
        Employee emp = getEmployee(name);
        if (emp == null) {
            foundEmp = false;
        } else {
            employee = emp;
            foundEmp = true;
            setUserName(emp.getUserName());
            setEmpNumber(emp.getEmpNumber());
            setName(emp.getName());
        }
    }
    
    /**
     * Edits employee name or employee number.
     */
    public void editEmployee() {
        if (name != employee.getName()) {
            employee.setName(name);
        }
        if (empNumber != employee.getEmpNumber()) {
            employee.setEmpNumber(empNumber);
        }
        empManager.merge(employee);
    }
    
    /**
     * Clears username, empNumber, and name.
     */
    public void clear() {
        userName = null;
        empNumber = null;
        name = null;
        employee = null;
    }
    
    /**
     * Generates a new Employee to add.
     * @return new Employee
     */
    public Employee newEmployee() {
        return new Employee(name, empNumber, userName);
    }
    
    /**
     * Username getter.
     * @return the username
     */
    public String getUserName() {
        return userName;
    }

    /**
     * Username setter.
     * @param userName username to set
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * EmpNumber getter.
     * @return the empNumber
     */
    public Integer getEmpNumber() {
        return empNumber;
    }

    /**
     * EmpNumber setter.
     * @param empNumber empNumber to set
     */
    public void setEmpNumber(Integer empNumber) {
        this.empNumber = empNumber;
    }

    /**
     * Name getter.
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Name setter.
     * @param name name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * foundEmp getter.
     * @return the foundEmp
     */
    public boolean isFoundEmp() {
        return foundEmp;
    }

    /**
     * foundEmp setter.
     * @param foundEmp boolean to set
     */
    public void setFoundEmp(boolean foundEmp) {
        this.foundEmp = foundEmp;
    }
    
    /**
     * UpdateEmp getter.
     * @return the updateEmp
     */
    public boolean isupdateEmp() {
        return updateEmp;
    }

    /**
     * UpdateEmp setter.
     * @param newValue boolean to set
     */
    public void setupdateEmp(boolean newValue) {
        this.updateEmp = newValue;
    }

    /**
     * AddEmp getter.
     * @return the addEmp
     */
    public boolean isAddEmp() {
        return addEmp;
    }

    /**
     * AddEmp setter.
     * @param addEmp boolean to set
     */
    public void setAddEmp(boolean addEmp) {
        this.addEmp = addEmp;
    }

    /**
     * RemoveEmp getter.
     * @return the removeEmp
     */
    public boolean isRemoveEmp() {
        return removeEmp;
    }

    /**
     * RemoveEmp setter.
     * @param removeEmp boolean to set
     */
    public void setRemoveEmp(boolean removeEmp) {
        this.removeEmp = removeEmp;
    }

    /**
     * Employee getter.
     * @return the employee
     */
    public Employee getEmployee() {
        return employee;
    }

    /**
     * Employee setter.
     * @param emp employee to set
     */
    public void setEmployee(Employee emp) {
        this.employee = emp;
    }
}
