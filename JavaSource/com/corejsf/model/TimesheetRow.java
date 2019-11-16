package com.corejsf.model;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;


@Entity
@Table(name="timesheetRow")
public class TimesheetRow implements Serializable{
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name="RowID")
    private int id;
    
    @ManyToOne
    @JoinColumn(name="TimesheetID")
    private Timesheet timesheet;

    @Column(name="ProjectID")
    private String projectID;

    @Column(name="WorkPackage")
    private String wp;

    @Column(name="HourMon")
    private double hourMon;

    @Column(name="HourTue")
    private double hourTue;

    @Column(name="HourWed")
    private double hourWed;

    @Column(name="HourThur")
    private double hourThur;

    @Column(name="HourFri")
    private double hourFri;

    @Column(name="HourSat")
    private double hourSat;

    @Column(name="HourSun")
    private double hourSun;

    @Column(name="Note")
    private String note;
    
    public TimesheetRow(Timesheet ts) {
        this.timesheet = ts;
    }
    
    public int getId() {
        return id;
    }
    
    public Timesheet getTimesheetID() {
        return timesheet;
    }

    public void setTimesheetID(Timesheet timesheet) {
        this.timesheet = timesheet;
    }

    public String getProjectID() {
        return projectID;
    }

    public void setProjectID(String projectID) {
        this.projectID = projectID;
    }

    public String getWp() {
        return wp;
    }

    public void setWp(String wp) {
        this.wp = wp;
    }

    public double getHourMon() {
        return hourMon;
    }

    public void setHourMon(double hourMon) {
        this.hourMon = hourMon;
    }

    public double getHourTue() {
        return hourTue;
    }

    public void setHourTue(double hourTue) {
        this.hourTue = hourTue;
    }

    public double getHourWed() {
        return hourWed;
    }

    public void setHourWed(double hourWed) {
        this.hourWed = hourWed;
    }

    public double getHourThur() {
        return hourThur;
    }

    public void setHourThur(double hourThur) {
        this.hourThur = hourThur;
    }

    public double getHourFri() {
        return hourFri;
    }

    public void setHourFri(double hourFri) {
        this.hourFri = hourFri;
    }

    public double getHourSat() {
        return hourSat;
    }

    public void setHourSat(double hourSat) {
        this.hourSat = hourSat;
    }

    public double getHourSun() {
        return hourSun;
    }

    public void setHourSun(double hourSun) {
        this.hourSun = hourSun;
    }
    
    public double getTotalHours() {
        return hourMon + hourTue + hourWed + hourThur + hourFri + hourSat + hourSun;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

}
