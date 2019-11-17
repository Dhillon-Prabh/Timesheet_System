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

@Named("tsController")
@SessionScoped
public class TimesheetController implements Serializable {
    private static final long serialVersionUID = 1L;

    @EJB
    private TimesheetRowManager tsrm;
    
    @EJB
    private TimesheetManager tsm;

    private List<TimesheetRow> details;

    /**
     * Getter for timesheet row details.
     * @return the details
     */
    public void fillDetails(Timesheet ts) {
       details = tsrm.getByTimesheet(ts.getId());
    }
    
    public void save(Timesheet ts) {
        System.out.println(ts);
        List<TimesheetRow> trList = details;
        for (TimesheetRow tr: trList) {
            tsrm.merge(tr);
        }
    }
    
    public double getTimesheetHours(Timesheet ts) {
        double result = 0;
        result = tsrm.getTimesheetHours(ts);
        return result;
    }
    
    public double getDayHour(Timesheet ts, int day) {
        double result = 0;
        result = tsrm.getDayHours(ts, day);
        return result;
    }
   
    
    public List<TimesheetRow> getDetails(Timesheet ts) {
        fillDetails(ts);
        return details;
    }

    public void setDetails(List<TimesheetRow> details) {
        System.out.println("SetDetails()");
        this.details = details;
    }
}
