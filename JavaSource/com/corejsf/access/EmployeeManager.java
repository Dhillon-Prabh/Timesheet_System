package com.corejsf.access;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.enterprise.context.Dependent;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import com.corejsf.model.Employee;

/**
 * Employee Manager handles the database calls.
 * @author jham
 * @author psingh
 * @version 1.0
 */
@Dependent
@Stateless
public class EmployeeManager implements Serializable {
    private static final long serialVersionUID = 1L;
    /** Connection to the database. */
    @PersistenceContext(unitName = "timesheet-jpa") private EntityManager em;

    /**
     * finds the employee with this id.
     * @param id Employee id
     * @return Employee
     */
    public Employee find(int id) {
        return em.find(Employee.class, id);
    }
    
    /**
     * adds the employee to the employees table.
     * @param emp Employee
     */
    public void persist(Employee emp) {
        em.persist(emp);
    }
    
    /**
     * updates the employees in the employee table.
     * @param emp Employee
     */
    public void merge(Employee emp) {
        em.merge(emp);
    }
    
    /**
     * removes the employee from the employee table.
     * @param emp Employee
     */
    public void remove(Employee emp) {
        emp = find(emp.getId());
        em.remove(emp);
    }
    
    /**
     * returns all the employees.
     * @return List of Employee
     */
    public List<Employee> getAll() {
        TypedQuery<Employee> query = em.createQuery("select e from Employee e "
                + "order by e.empNumber", Employee.class); 
        List<Employee> employees = query.getResultList();
        ArrayList<Employee> empList = new ArrayList<Employee>();
        for (int i = 0; i < employees.size(); i++) {
            empList.add(employees.get(i));
        }
        return empList;
    }
    
    /**
     * finds an employee with this name.
     * @param name name
     * @return Employee
     */
    public Employee getByName(String name) {
        for (Employee e : getAll()) {
            if (e.getName().equals(name)) {
                return e;
            }
        }
        return null;
    }
}
