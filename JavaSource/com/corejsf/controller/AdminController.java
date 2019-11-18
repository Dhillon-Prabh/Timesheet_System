package com.corejsf.controller;

import java.io.Serializable;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.corejsf.access.CredentialManager;
import com.corejsf.access.EmployeeManager;
import com.corejsf.access.TimesheetManager;
import com.corejsf.access.TimesheetRowManager;
import com.corejsf.model.Credential;
import com.corejsf.model.Employee;
import com.corejsf.model.Timesheet;
import com.corejsf.model.TimesheetRow;

/**
 * Controller for administrator. Deals with administrator rights.
 * @author jham
 * @author psingh
 * @version 1.0
 */
@Named("adminController")
@ViewScoped
public class AdminController implements Serializable {
    private static final long serialVersionUID = 1L;

    /** Connection to Employee table. */
    @EJB
    private EmployeeManager empManager;
    
    /** Connection to Credential table. */
    @EJB
    private CredentialManager credManager;
    
    /** Connection to Timesheet table. */
    @EJB
    private TimesheetManager tsManager;
    
    /** Connection to TimesheetRow table. */
    @EJB
    private TimesheetRowManager tsrManager;
    
    /** Employee controller. */
    @Inject
    private EmployeeController empController;
    
    /** Timesheet controller. */
    @Inject
    private TimesheetController tsController;
    
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
    
    /**
     * deletes the employee.
     */
    public void deleteEmployee() {
        Credential cred = credManager.findByUserName(userName);
        credManager.remove(cred);
        
        List<Timesheet> timesheets = empController.getAllTimesheets(employee);
        for (Timesheet ts : timesheets) {
            List<TimesheetRow> rows = tsController.getDetails(ts);
            for (TimesheetRow row : rows) {
                tsrManager.remove(row);
            }
            tsManager.remove(ts);
        }
        
        empManager.remove(employee);
        remove();

        FacesMessage facesMessage = com.corejsf.util.Messages.getMessage(
                "com.corejsf.controller.messages", "deleteEmployee", null);
        FacesContext.getCurrentInstance().addMessage(null, facesMessage);
    }
    
    /**
     * adds the employee to the database.
     * @param password new password
     * @param newPassword confirm new password
     */
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
        cred.setEmp(empController.getEmployee(name));
        credManager.persist(cred);
        add();
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
     * @param newName Employee to find
     */
    public void findEmp(String newName) {
        Employee emp = empController.getEmployee(newName);
        if (emp == null) {
            foundEmp = false;
            FacesMessage facesMessage = com.corejsf.util.Messages.getMessage(
                    "com.corejsf.controller.messages", "findEmp", null);
            FacesContext.getCurrentInstance().addMessage(null, facesMessage);
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
        update();

        FacesMessage facesMessage = com.corejsf.util.Messages.getMessage(
                "com.corejsf.controller.messages", "updateEmployee", null);
        FacesContext.getCurrentInstance().addMessage(null, facesMessage);
    }

    
    /**
     * Do not allow the update of the admin user. This validates it.
     * @param context Context
     * @param component Component
     * @param value Value
     */
    public void validateUpdateAdmin(FacesContext context, 
            UIComponent component, Object value) {

        UIInput nameInput = (UIInput) component.findComponent("updateName");
        
        String nameStr = (String) nameInput.getSubmittedValue();

        if (nameStr.equals("admin")) {
            FacesMessage message = com.corejsf.util.Messages.getMessage(
                    "com.corejsf.controller.messages", "updateAdmin", null);
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(message);
        }
    }
    
    /**
     * Do not allow the delete of the admin user. This validates it.
     * @param context Context
     * @param component Component
     * @param value Value
     */
    public void validateDeleteAdmin(FacesContext context, 
            UIComponent component, Object value) {

        UIInput nameInput = (UIInput) component.findComponent("deleteName");
        
        String nameStr = (String) nameInput.getSubmittedValue();

        if (nameStr.equals("admin")) {
            FacesMessage message = com.corejsf.util.Messages.getMessage(
                    "com.corejsf.controller.messages", "deleteAdmin", null);
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(message);
        }
    }
    
    /**
     * checks if the employee number already exists.
     * @param context Context
     * @param component Component
     * @param value Value
     */
    public void validateEmpNum(FacesContext context, 
            UIComponent component, Object value) {
        
        Integer empNumberInt = ((Integer) value).intValue();

        for (Employee e : empController.getEmployees()) {
            if (e.getEmpNumber() == empNumberInt) {
                FacesMessage message = com.corejsf.util.Messages.getMessage(
                        "com.corejsf.controller.messages", "addEmpNum", null);
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                throw new ValidatorException(message);
            }
        }
    }
    
    /**
     * validates the username. If this already exists do not allow that.
     * @param context Context
     * @param component Component
     * @param value Value
     */
    public void validateUserName(FacesContext context, 
            UIComponent component, Object value) {

        UIInput getUsername = (UIInput) component.findComponent("addUserName");
        
        String username = (String) getUsername.getSubmittedValue();

        for (Employee e : empController.getEmployees()) {
            if (e.getUserName().equals(username)) {
                FacesMessage message = com.corejsf.util.Messages.getMessage(
                        "com.corejsf.controller.messages", "addUsername", null);
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                throw new ValidatorException(message);
            }
        }
    }

    /**
     * newPassword and repeated password should be the same.
     * @param context Context
     * @param component Component
     * @param value Value
     */
    public void validateNewPassword(FacesContext context, 
            UIComponent component, Object value) {

        UIInput newPassword 
            = (UIInput) component.findComponent("addNewPassword");
        UIInput confirmNewPassword 
            = (UIInput) component.findComponent("addConfirmPassword");
        
        String password = (String) newPassword.getLocalValue();
        String password2 = (String) confirmNewPassword.getSubmittedValue();

        if (!password.equals(password2)) {
            FacesMessage message = com.corejsf.util.Messages.getMessage(
                    "com.corejsf.controller.messages", "checkPasswords", null);
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(message);
        }
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
