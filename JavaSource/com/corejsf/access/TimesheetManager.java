package com.corejsf.access;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.enterprise.context.Dependent;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import com.corejsf.model.Employee;
import com.corejsf.model.Timesheet;
import com.corejsf.model.TimesheetRow;

@Dependent
@Stateless
public class TimesheetManager implements Serializable{
    private static final long serialVersionUID = 1L;
    @PersistenceContext(unitName="timesheet-jpa") EntityManager em;
    
    @EJB
    private TimesheetRowManager tsrm;
    
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
   
   /**
    * returns all the timesheets for this employee
    * @param e
    * @return
    */
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
   
   /**
    * gets the current timesheet for this employee
    * @param e
    * @return
    */
   public Timesheet getCurrentTimesheet(Employee e) {
       Timesheet t = new Timesheet();
       int we = t.getWeekNumber();
       for (Timesheet ts : getTimesheets(e)) {
           if (ts.getWeekNumber() == we) {
               return ts;
           }
       }
       t.setEmp(e);
       persist(t);
       for (int i = 0; i < 5; i++) {
           addRow(t);
       }
       return t;
   }

   /**
    * Deletes the specified row from the timesheet.
    *
    * @param rowToRemove
    *            the row to remove from the timesheet.
    */
   public void deleteRow(final TimesheetRow rowToRemove) {
       tsrm.remove(rowToRemove);
   }

   /**
    * Add an empty row to to the timesheet.
    */
   public void addRow(Timesheet ts) {
       tsrm.persist(new TimesheetRow(ts));
   }
   
   /**
    * Calculates the total hours.
    *
    * @return total hours for timesheet.
    */
   public BigDecimal getTotalHours(Timesheet ts) {
       BigDecimal sum = BigDecimal.ZERO;
       List<TimesheetRow> details = tsrm.getByTimesheet(ts.getId());
       for (TimesheetRow row : details) {
           sum = sum.add(BigDecimal.valueOf(row.getTotalHours()));
       }
       return sum;
   }

   /**
    * Sets the details of the timesheet.
    *
    * @param newDetails new weekly charges to set
    */
   public void setDetails(final ArrayList<TimesheetRow> newDetails) {
       for(int i = 0; i < newDetails.size(); i++) {
           tsrm.persist(newDetails.get(i));
       }
   }
}
