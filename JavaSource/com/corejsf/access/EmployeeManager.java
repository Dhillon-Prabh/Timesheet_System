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

    public Employee find(int id) {
        return em.find(Employee.class, id);
    }
    
    public void persist(Employee emp) {
        em.persist(emp);
    }
    
    public void merge(Employee emp) {
        em.merge(emp);
    }
    
    public void remove(Employee emp) {
        emp = find(emp.getId());
        em.remove(emp);
    }
    
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
    
    public Employee getByName(String name) {
        for (Employee e : getAll()) {
            if (e.getName().equals(name)) {
                return e;
            }
        }
        return null;
    }
}
