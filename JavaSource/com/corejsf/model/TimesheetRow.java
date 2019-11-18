package com.corejsf.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Model for TimesheetRow table.
 * @author jham
 * @author psingh
 * @version 1.0
 */
@Entity
@Table(name = "timesheetRow")
public class TimesheetRow implements Serializable {
    private static final long serialVersionUID = 1L;

    /** primary key. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "RowID")
    private int id;
    
    /** Timesheet associated. */
    @ManyToOne
    @JoinColumn(name = "TimesheetID")
    private Timesheet timesheet;

    /** project id. */
    @Column(name = "ProjectID")
    private int projectID;

    /** work package. */
    @Column(name = "WorkPackage")
    private String wp;

    /** hours for monday. */
    @Column(name = "HourMon")
    private double hourMon;

    /** hours for tuesday. */
    @Column(name = "HourTue")
    private double hourTue;

    /** hours for wednesday. */
    @Column(name = "HourWed")
    private double hourWed;

    /** hours for thursday. */
    @Column(name = "HourThur")
    private double hourThur;

    /** hours for friday. */
    @Column(name = "HourFri")
    private double hourFri;

    /** hours for saturday. */
    @Column(name = "HourSat")
    private double hourSat;

    /** hours for sunday. */
    @Column(name = "HourSun")
    private double hourSun;

    /** notes. */
    @Column(name = "Note")
    private String note;
    
    /**
     * default constructor for the timesheetrow.
     */
    public TimesheetRow() { }
    
    /**
     * constructs the timesheetrow with the timesheet attached.
     * @param ts Timesheet
     */
    public TimesheetRow(Timesheet ts) {
        this.timesheet = ts;
    }
    
    /**
     * gets the id.
     * @return id
     */
    public int getId() {
        return id;
    }
    
    /**
     * gets the timesheet.
     * @return Timesheet
     */
    public Timesheet getTimesheet() {
        return timesheet;
    }

    /**
     * sets the timesheet.
     * @param timesheet Timesheet
     */
    public void setTimesheet(Timesheet timesheet) {
        this.timesheet = timesheet;
    }

    /**
     * gets the projectid.
     * @return project id
     */
    public int getProjectID() {
        return projectID;
    }

    /**
     * setst the projectid.
     * @param projectID project id
     */
    public void setProjectID(int projectID) {
        this.projectID = projectID;
    }

    /**
     * gets the work package.
     * @return work package
     */
    public String getWp() {
        return wp;
    }

    /**
     * sets the work package.
     * @param wp work package
     */
    public void setWp(String wp) {
        this.wp = wp;
    }

    /**
     * get hourMon.
     * @return double
     */
    public double getHourMon() {
        return hourMon;
    }

    /**
     * set hourMon.
     * @param hourMon double
     */
    public void setHourMon(double hourMon) {
        this.hourMon = hourMon;
    }

    /**
     * get hourTue.
     * @return double
     */
    public double getHourTue() {
        return hourTue;
    }

    /**
     * set hourTue.
     * @param hourTue double
     */
    public void setHourTue(double hourTue) {
        this.hourTue = hourTue;
    }

    /**
     * get hourWed.
     * @return double
     */
    public double getHourWed() {
        return hourWed;
    }

    /**
     * set hourWed.
     * @param hourWed double
     */
    public void setHourWed(double hourWed) {
        this.hourWed = hourWed;
    }

    /**
     * get hourThur.
     * @return double
     */
    public double getHourThur() {
        return hourThur;
    }

    /**
     * set hourThur.
     * @param hourThur double
     */
    public void setHourThur(double hourThur) {
        this.hourThur = hourThur;
    }

    /**
     * get hourFri.
     * @return double
     */
    public double getHourFri() {
        return hourFri;
    }

    
    /**
     * set hourFri.
     * @param hourFri double
     */
    public void setHourFri(double hourFri) {
        this.hourFri = hourFri;
    }

    /**
     * get hourSat.
     * @return double
     */
    public double getHourSat() {
        return hourSat;
    }

    /**
     * sets hourSat.
     * @param hourSat double
     */
    public void setHourSat(double hourSat) {
        this.hourSat = hourSat;
    }

    /**
     * get hourSun.
     * @return double
     */
    public double getHourSun() {
        return hourSun;
    }

    /**
     * set hourSun.
     * @param hourSun double
     */
    public void setHourSun(double hourSun) {
        this.hourSun = hourSun;
    }
    
    /**
     * gets the total hours.
     * @return double
     */
    public double getTotalHours() {
        return hourMon + hourTue + hourWed + hourThur 
                + hourFri + hourSat + hourSun;
    }
    
    /**
     * gets the note.
     * @return String
     */
    public String getNote() {
        return note;
    }

    /**
     * sets the note.
     * @param note String
     */
    public void setNote(String note) {
        this.note = note;
    }

    /**
     * gets the total hours of the day.
     * @param day int for day
     * @return double
     */
    public double getDayhours(int day) {
        final int tue = 3;
        final int wed = 4;
        final int thur = 5;
        final int fri = 6;
        double result;
        switch (day) {
            case 0:
                result = hourSat;
                break;
            case 1:
                result = hourSun;
                break;
            case 2:
                result = hourMon;
                break;
            case tue:
                result = hourTue;
                break;
            case wed:
                result = hourWed;
                break;
            case thur:
                result = hourThur;
                break;
            case fri:
                result = hourFri;
                break;
            default:
                result = 0;
        }
        return result;
    }

}
