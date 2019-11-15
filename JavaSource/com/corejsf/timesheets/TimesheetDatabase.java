package com.corejsf.timesheets;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;

import ca.bcit.infosys.timesheet.Timesheet;

/**
 * Simulated database holding timesheet data.
 * 
 * @author jham
 * @version 1.0
 */
@ApplicationScoped
public class TimesheetDatabase implements Serializable {

    /** List of existing timesheets. */
    private List<Timesheet> timesheets = new ArrayList<Timesheet>();

    /**
     * Default no-argument constructor.
     */
    public TimesheetDatabase() { };
    
    /**
     * Timesheet list getter.
     * @return ArrayList of timesheets
     */
    public List<Timesheet> getTimesheets() {
        return timesheets;
    }
}
