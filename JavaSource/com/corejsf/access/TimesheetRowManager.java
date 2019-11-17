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

@Dependent
@Stateless
public class TimesheetRowManager implements Serializable {
    private static final long serialVersionUID = 1L;
    @PersistenceContext(unitName="timesheet-jpa") EntityManager em;
    
    public TimesheetRow find(int id) {
        return em.find(TimesheetRow.class, id);
            }

    public void persist(TimesheetRow tsr) {
        em.persist(tsr);
    }
    
    public void merge(TimesheetRow tsr) {
        System.out.println("tsrm merging:" + tsr.getWp());
        em.merge(tsr);
    }

    public void remove(TimesheetRow tsr) {
        tsr = find(tsr.getId());
        em.remove(tsr);
    }
    
    public ArrayList<TimesheetRow> getByTimesheet(int timesheetId) {
        TypedQuery<TimesheetRow> query = em.createQuery("select tsr from " +
                "TimesheetRow tsr where tsr.timesheet.id = " + timesheetId, TimesheetRow.class); 
         List<TimesheetRow> tsrs = query.getResultList();
         ArrayList<TimesheetRow> tsrList= new ArrayList<TimesheetRow>();
         for (TimesheetRow tsr : tsrs) {
             tsrList.add(tsr);
         }
         return tsrList;
    }

    public double getTimesheetHours(Timesheet ts) {
        double result = 0;
        TypedQuery<TimesheetRow> query = em.createQuery("select tsr from " +
                "TimesheetRow tsr where tsr.timesheet.id = " + ts.getId(), TimesheetRow.class); 
         List<TimesheetRow> tsrs = query.getResultList();
         for (TimesheetRow tsr : tsrs) {
             result += tsr.getTotalHours();
         }
         return result;
    }

    public double getDayHours(Timesheet ts, int day) {
        double result = 0;
        TypedQuery<TimesheetRow> query = em.createQuery("select tsr from " +
                "TimesheetRow tsr where tsr.timesheet.id = " + ts.getId(), TimesheetRow.class); 
         List<TimesheetRow> tsrs = query.getResultList();
         for (TimesheetRow tsr : tsrs) {
             result += tsr.getDayhours(day);
         }
        return result;
    }
}
