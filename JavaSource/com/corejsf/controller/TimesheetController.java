package com.corejsf.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import com.corejsf.access.TimesheetManager;
import com.corejsf.access.TimesheetRowManager;
import com.corejsf.model.Timesheet;
import com.corejsf.model.TimesheetRow;

/**
 * Controller for Timesheet.
 * @author jham
 * @author psingh
 * @version 1.0
 */
@Named("tsController")
@SessionScoped
public class TimesheetController implements Serializable {
    private static final long serialVersionUID = 1L;

    /** Connection to TimesheetRow table. */
    @EJB
    private TimesheetRowManager tsrm;
    
    /** Connection to Timesheet table. */
    @EJB
    private TimesheetManager tsm;

    /**
     * holds the rows of the current timesheet.
     */
    private List<TimesheetRow> details;
    
    /**
     * saves the current timesheet.
     * @param ts Timesheet
     */
    public void save(Timesheet ts) {
        System.out.println(ts);
        System.out.println("These are the details2 " + details.get(0));
        for (int i = 0; i < details.size(); i++) {
            System.out.println("merging");
            tsrm.merge(details.get(i));
        }
    }
    
    /**
     * adds the row to the timesheet.
     * @param ts Timesheet
     */
    public void addRow(Timesheet ts) {
        System.out.println("in here");
        TimesheetRow tr = new TimesheetRow(ts);
        tsrm.persist(tr);
        refreshList(ts);
    }
    
    /**
     * gets the total hours of the current timesheet.
     * @param ts Timesheet
     * @return double
     */
    public double getTimesheetHours(Timesheet ts) {
        double result = 0;
        result = tsrm.getTimesheetHours(ts);
        return result;
    }
    
    /**
     * gets the total hours for a day in the timesheet.
     * @param ts Timesheet
     * @param day int for day
     * @return double
     */
    public double getDayHour(Timesheet ts, int day) {
        double result = 0;
        result = tsrm.getDayHours(ts, day);
        return result;
    }
   
    
    /**
     * gets the rows if the timesheet changes.
     * @param ts Timesheet
     * @return List of Timesheet
     */
    public List<TimesheetRow> getDetails(Timesheet ts) {
        if (details == null) {
            refreshList(ts);
        } else if (details.get(0).getTimesheet().getId() != (ts.getId())) {
            details = null;
            refreshList(ts);
        }
        return details;
    }
    /**
     * sets the details.
     * @param details List of Timesheet
     */
    public void setDetails(List<TimesheetRow> details) {
        System.out.println("SetDetails()");
        this.details = details;
    }
    
    /**
     * refreshes the details when the new user logs in or the timesheet changes.
     * @param ts Timesheet
     */
    private void refreshList(Timesheet ts) {
        details = new ArrayList<TimesheetRow>();
        List<TimesheetRow> listts = tsrm.getByTimesheet(ts.getId());
        for (TimesheetRow tr : listts) {
            details.add(tr);
        }
    }
}
