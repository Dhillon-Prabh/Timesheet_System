package com.corejsf.timesheets;

import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import ca.bcit.infosys.employee.Employee;
import ca.bcit.infosys.timesheet.Timesheet;
import ca.bcit.infosys.timesheet.TimesheetCollection;

/**
 * Access class to back end to access timesheets.
 * 
 * @author jham
 * @version 1.0
 */
@Named("timesheetDatabase")
@SessionScoped
public class TimesheetModel implements TimesheetCollection {

    /** Database to get timesheet data. */
    @Inject
    private TimesheetDatabase db;
    /** CDI bean to access employees. */
    @Inject
    private EmployeeModel em;
    /** Current timesheet. */
    private Timesheet currentTimesheet;
    
    /**
     * Default no-argument constructor.
     */
    public TimesheetModel() { }
    
    @Override
    public List<Timesheet> getTimesheets() {
        return db.getTimesheets();
    }

    @Override
    public List<Timesheet> getTimesheets(Employee e) {
        List<Timesheet> t = new ArrayList<Timesheet>();
        for (Timesheet ts : getTimesheets()) {
            if (ts.getEmployee().getEmpNumber() == e.getEmpNumber()) {
                t.add(ts);
            }
        }
        return t;
    }
    
    @Override
    public Timesheet getCurrentTimesheet(Employee e) {
        Timesheet t = new Timesheet();
        int we = t.getWeekNumber();
        for (Timesheet ts : getTimesheets(e)) {
            if (ts.getWeekNumber() == we) {
                currentTimesheet = ts;
                return ts;
            }
        }
        t.setEmployee(em.getEmployee(e.getName()));
        t.addRow();
        t.addRow();
        t.addRow();
        t.addRow();
        t.addRow();
        currentTimesheet = t;
        addTimesheet();
        return t;
    }

    @Override
    public String addTimesheet() {
        if (!getTimesheets().contains(currentTimesheet)) {
            getTimesheets().add(currentTimesheet);
        }
        return null;
    }
}
