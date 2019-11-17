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
    
    public void save(Timesheet ts) {
        System.out.println(ts);
//        List<TimesheetRow> trList = details;
        System.out.println("These are the details2 " + details.get(0));
        for (int i = 0; i < details.size(); i++) {
            System.out.println("merging");
            tsrm.merge(details.get(i));
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
        if (details == null) {
            refreshList(ts);
        }
        return details;
    }

    public void setDetails(List<TimesheetRow> details) {
        System.out.println("SetDetails()");
        this.details = details;
    }
    
    private void refreshList(Timesheet ts) {
        details = new ArrayList<TimesheetRow>();
        List<TimesheetRow> listts = tsrm.getByTimesheet(ts.getId());
        for (TimesheetRow tr : listts) {
            details.add(tr);
        }
    }
}
