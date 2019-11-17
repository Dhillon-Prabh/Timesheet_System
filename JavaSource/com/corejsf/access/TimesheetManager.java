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
<<<<<<< HEAD

=======
    
    @EJB
    private TimesheetRowManager tsrm;
    
>>>>>>> 93a894025b230b60ad1898c4ded9d376e1a1990d
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
   public void addRow() {
       tsrm.persist(new TimesheetRow(this));
   }
   
   /**
    * Calculates the total hours.
    *
    * @return total hours for timesheet.
    */
   public BigDecimal getTotalHours() {
       BigDecimal sum = BigDecimal.ZERO;
       List<TimesheetRow> details = tsrm.getByTimesheet(this.id);
       for (TimesheetRow row : details) {
           sum = sum.add(BigDecimal.valueOf(row.getTotalHours()));
       }
       return sum;
   }
   
   /**
    * Getter for timesheet row details.
    * @return the details
    */
   public List<TimesheetRow> getDetails() {
      return tsrm.getByTimesheet(this.id);
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

   /**
    * Checks to see if timesheet total nets 40 hours.
    * @return true if FULL_WORK_WEEK == hours -flextime - overtime
    */
   public boolean isValid() {
       BigDecimal net = getTotalHours();
       if (overtime != null) {
           net = net.subtract(overtime);
       }
       if (flextime != null) {
           net = net.subtract(flextime);
       }
       return net.equals(FULL_WORK_WEEK);
   }
}
