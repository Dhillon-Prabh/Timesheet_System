package com.corejsf.access;

import java.io.Serializable;
import java.math.BigDecimal;
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
        System.out.println("Adding row to database");
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

    public BigDecimal getTimesheetHours(Timesheet ts) {
        BigDecimal result = BigDecimal.ZERO;
        TypedQuery<TimesheetRow> query = em.createQuery("select tsr from " +
                "TimesheetRow tsr where tsr.timesheet.id = " + ts.getId(), TimesheetRow.class); 
         List<TimesheetRow> tsrs = query.getResultList();
         for (TimesheetRow tsr : tsrs) {
             result.add(tsr.getTotalHours());
         }
         return result;
    }

    public BigDecimal getDayHours(Timesheet ts, int day) {
        BigDecimal result = BigDecimal.ZERO;
        TypedQuery<TimesheetRow> query = em.createQuery("select tsr from " +
                "TimesheetRow tsr where tsr.timesheet.id = " + ts.getId(), TimesheetRow.class); 
         List<TimesheetRow> tsrs = query.getResultList();
         for (TimesheetRow tsr : tsrs) {
             result.add(tsr.getDayhours(day));
         }
        return result;
    }
}
