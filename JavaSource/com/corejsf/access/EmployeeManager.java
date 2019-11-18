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

@Dependent
@Stateless
public class EmployeeManager implements Serializable {
    private static final long serialVersionUID = 1L;
    @PersistenceContext(unitName="timesheet-jpa") EntityManager em;

    /**
     * finds the employee with this id
     * @param id
     * @return
     */
    public Employee find(int id) {
        return em.find(Employee.class, id);
    }
    
    /**
     * adds the employee to the employees table
     * @param emp
     */
    public void persist(Employee emp) {
        em.persist(emp);
    }
    
    /**
     * updates the employees in the employee table
     * @param emp
     */
    public void merge(Employee emp) {
        em.merge(emp);
    }
    
    /**
     * removes the employee from the employee table
     * @param emp
     */
    public void remove(Employee emp) {
        emp = find(emp.getId());
        em.remove(emp);
    }
    
    /**
     * returns all the employees
     * @return
     */
    public List<Employee> getAll() {
        TypedQuery<Employee> query = em.createQuery("select e from Employee e order by e.empNumber",
                Employee.class); 
        List<Employee> employees = query.getResultList();
        ArrayList<Employee> empList = new ArrayList<Employee>();
        for (int i = 0; i < employees.size(); i++) {
            empList.add(employees.get(i));
        }
        return empList;
    }
    
    /**
     * finds an employee with this name
     * @param name
     * @return
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
