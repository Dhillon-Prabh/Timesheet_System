package com.corejsf.access;

import java.io.Serializable;

import javax.ejb.Stateless;
import javax.enterprise.context.Dependent;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import com.corejsf.model.Employee;
import com.corejsf.model.Timesheet;

@Dependent
@Stateless
public class TimesheetManager implements Serializable{
    private static final long serialVersionUID = 1L;
    @PersistenceContext(unitName="timesheet-jpa") EntityManager em;

    /**
    * Find Inventory record from database.
    * 
    * @param id
    *            primary key of record to be returned.
    * @return the Inventory record with key = id, null if not found.
    */
   public Timesheet find(int id) {
       return em.find(Timesheet.class, id);
   }

   /**
    * Persist Product record into inventory database. productId must be unique.
    * 
    * @param product
    *            the record to be persisted.
    */
   public void persist(Timesheet timesheet) {
       em.persist(timesheet);
   }

   /**
    * merge Product record fields into existing inventory database record.
    * 
    * @param product
    *            the record to be merged.
    */
   public void merge(Timesheet timesheet) {
       em.merge(timesheet);
   }

   /**
    * Remove product item from database.
    * 
    * @param product
    *            record to be removed from database
    */
   public void remove(Timesheet timesheet) {
       timesheet = find(timesheet.getId());
       em.remove(timesheet);
   }
   
   public Timesheet[] getTimesheets(Employee e) {
       TypedQuery<Timesheet> query = em.createQuery("select t from " +
               "Timesheet t where t.emp.id = " + e.getId(), Timesheet.class); 
       java.util.List<Timesheet> timesheets = query.getResultList();
       Timesheet[] tsArray = new Timesheet[timesheets.size()];
       for (int i=0; i < tsArray.length; i++) {
           tsArray[i] = timesheets.get(i);
       }
       return tsArray;
   }
   
   public Timesheet getCurrentTimesheet(Employee e) {
       Timesheet t = new Timesheet();
       int we = t.getWeekNumber();
       for (Timesheet ts : getTimesheets(e)) {
           if (ts.getWeekNumber() == we) {
               return ts;
           }
       }
       t.setEmp(e);
       return t;
   }
//   public Timesheet getCurrentTimesheet(Employee e) {
//       Timesheet t = new Timesheet();
//       int we = t.getWeekNumber();
//       for (Timesheet ts : getTimesheets(e)) {
//           if (ts.getWeekNumber() == we) {
//               currentTimesheet = ts;
//               return ts;
//           }
//       }
//       t.setEmployee(em.getEmployee(e.getName()));
//       t.addRow();
//       t.addRow();
//       t.addRow();
//       t.addRow();
//       t.addRow();
//       currentTimesheet = t;
//       addTimesheet();
//       return t;
//   }
}
