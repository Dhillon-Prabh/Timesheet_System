package com.corejsf.access;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.enterprise.context.Dependent;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import com.corejsf.model.Timesheet;
import com.corejsf.model.TimesheetRow;

/**
 * TimesheetRow Manager handles the database calls.
 * @author psing
 * @author jham
 * @version 1.0
 */
@Dependent
@Stateless
public class TimesheetRowManager implements Serializable {
    private static final long serialVersionUID = 1L;
    /** Connection to the database. */
    @PersistenceContext(unitName = "timesheet-jpa") private EntityManager em;
    
    /**
     * returns timesheetrow with this id.
     * @param id Id of timesheet
     * @return  Timesheet
     */
    public TimesheetRow find(int id) {
        return em.find(TimesheetRow.class, id);
    }

    /**
     * adds the timesheetrow to the timesheetrow table.
     * @param tsr TimesheetRow
     */
    public void persist(TimesheetRow tsr) {
        System.out.println("Adding row to database");
        em.persist(tsr);
    }
    
    /**
     * updates the timesheet row to the timesheetrow table.
     * @param tsr TimesheetRow
     */
    public void merge(TimesheetRow tsr) {
        System.out.println("tsrm merging:" + tsr.getWp());
        em.merge(tsr);
    }

    /**
     * removes the timesheetrow from the timesheetrow table.
     * @param tsr TimesheetRow
     */
    public void remove(TimesheetRow tsr) {
        tsr = find(tsr.getId());
        em.remove(tsr);
    }
    
    /**
     * returns the timesheetrow for this timesheet.
     * @param timesheetId Timesheet Id
     * @return ArrayList of TimesheetRow
     */
    public ArrayList<TimesheetRow> getByTimesheet(int timesheetId) {
        TypedQuery<TimesheetRow> query = em.createQuery("select tsr from "
                + "TimesheetRow tsr where tsr.timesheet.id = " 
                + timesheetId, TimesheetRow.class); 
         List<TimesheetRow> tsrs = query.getResultList();
         ArrayList<TimesheetRow> tsrList = new ArrayList<TimesheetRow>();
         for (TimesheetRow tsr : tsrs) {
             tsrList.add(tsr);
         }
         return tsrList;
    }

    /**
     * returns the total hours for this timesheet.
     * @param ts Timesheet
     * @return double
     */
    public double getTimesheetHours(Timesheet ts) {
        double result = 0;
        TypedQuery<TimesheetRow> query = em.createQuery("select tsr from "
                + "TimesheetRow tsr where tsr.timesheet.id = " 
                + ts.getId(), TimesheetRow.class); 
         List<TimesheetRow> tsrs = query.getResultList();
         for (TimesheetRow tsr : tsrs) {
             result += tsr.getTotalHours();
         }
         return result;
    }

    /**
     * gets the total hours for this day.
     * @param ts Timesheet
     * @param day int for day
     * @return double
     */
    public double getDayHours(Timesheet ts, int day) {
        double result = 0;
        TypedQuery<TimesheetRow> query = em.createQuery("select tsr from " 
                + "TimesheetRow tsr where tsr.timesheet.id = " 
                + ts.getId(), TimesheetRow.class); 
         List<TimesheetRow> tsrs = query.getResultList();
         for (TimesheetRow tsr : tsrs) {
             result += tsr.getDayhours(day);
         }
        return result;
    }
}
