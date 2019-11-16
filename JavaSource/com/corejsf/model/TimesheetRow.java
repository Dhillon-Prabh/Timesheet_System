package com.corejsf.model;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

public class TimesheetRow {

    @Id
    @Column(name="RowID")
    private int id;
    
    @ManyToOne
    @JoinColumn(name="TimesheetID")
    private String timesheetID;

    @Column(name="ProjectID")
    private String projectID;

    @Column(name="WorkPackage")
    private String wp;

    @Column(name="HourMon")
    private String hourMon;

    @Column(name="HourTue")
    private String hourTue;

    @Column(name="HourWed")
    private String hourWed;

    @Column(name="HourThur")
    private String hourThur;

    @Column(name="HourFri")
    private String hourFri;

    @Column(name="HourSat")
    private String hourSat;

    @Column(name="HourSun")
    private String hourSun;

    @Column(name="Note")
    private String note;
    

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTimesheetID() {
        return timesheetID;
    }

    public void setTimesheetID(String timesheetID) {
        this.timesheetID = timesheetID;
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

    public String getHourMon() {
        return hourMon;
    }

    public void setHourMon(String hourMon) {
        this.hourMon = hourMon;
    }

    public String getHourTue() {
        return hourTue;
    }

    public void setHourTue(String hourTue) {
        this.hourTue = hourTue;
    }

    public String getHourWed() {
        return hourWed;
    }

    public void setHourWed(String hourWed) {
        this.hourWed = hourWed;
    }

    public String getHourThur() {
        return hourThur;
    }

    public void setHourThur(String hourThur) {
        this.hourThur = hourThur;
    }

    public String getHourFri() {
        return hourFri;
    }

    public void setHourFri(String hourFri) {
        this.hourFri = hourFri;
    }

    public String getHourSat() {
        return hourSat;
    }

    public void setHourSat(String hourSat) {
        this.hourSat = hourSat;
    }

    public String getHourSun() {
        return hourSun;
    }

    public void setHourSun(String hourSun) {
        this.hourSun = hourSun;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

}
