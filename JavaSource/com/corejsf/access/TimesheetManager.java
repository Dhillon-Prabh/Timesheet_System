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

/**
 * Timesheet Manager handles the database calls.
 * @author jham
 * @author psingh
 * @version 1.0
 */
@Dependent
@Stateless
public class TimesheetManager implements Serializable {
    private static final long serialVersionUID = 1L;
    /** Connection to the database. */
    @PersistenceContext(unitName = "timesheet-jpa") private EntityManager em;
    
    /** Deals with TimesheetRow in database. */
    @EJB
    private TimesheetRowManager tsrm;
    
    /**
    * Find Timesheet record from database.
    * 
    * @param id
    *            primary key of record to be returned.
    * @return the Timesheet record with key = id, null if not found.
    */
   public Timesheet find(int id) {
       return em.find(Timesheet.class, id);
   }

   /**
    * Persist Timesheet record into database.
    * 
    * @param timesheet
    *            the timesheet to be persisted.
    */
   public void persist(Timesheet timesheet) {
       em.persist(timesheet);
   }

   /**
    * merge Timesheet record fields into existing database record.
    * 
    * @param timesheet
    *            the record to be merged.
    */
   public void merge(Timesheet timesheet) {
       em.merge(timesheet);
   }

   /**
    * Remove Timesheet item from database.
    * 
    * @param timesheet
    *            record to be removed from database
    */
   public void remove(Timesheet timesheet) {
       timesheet = find(timesheet.getId());
       em.remove(timesheet);
   }
   
   /**
    * returns all the timesheets for this employee.
    * @param e Employee
    * @return Array of Timesheets
    */
   public Timesheet[] getTimesheets(Employee e) {
       TypedQuery<Timesheet> query = em.createQuery("select t from "
               + "Timesheet t where t.emp.id = " + e.getId(), Timesheet.class); 
       java.util.List<Timesheet> timesheets = query.getResultList();
       Timesheet[] tsArray = new Timesheet[timesheets.size()];
       for (int i = 0; i < tsArray.length; i++) {
           tsArray[i] = timesheets.get(i);
       }
       return tsArray;
   }
   
   /**
    * gets the current timesheet for this employee.
    * @param e Employee
    * @return Timesheet
    */
   public Timesheet getCurrentTimesheet(Employee e) {
       final int defaultRows = 5;
       Timesheet t = new Timesheet();
       int we = t.getWeekNumber();
       for (Timesheet ts : getTimesheets(e)) {
           if (ts.getWeekNumber() == we) {
               return ts;
           }
       }
       t.setEmp(e);
       persist(t);
       for (int i = 0; i < defaultRows; i++) {
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
    * 
    * @param ts Timesheet
    */
   public void addRow(Timesheet ts) {
       tsrm.persist(new TimesheetRow(ts));
   }
   
   /**
    * Calculates the total hours.
    *
    * @param ts Timesheet
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
       for (int i = 0; i < newDetails.size(); i++) {
           tsrm.persist(newDetails.get(i));
       }
   }
}
