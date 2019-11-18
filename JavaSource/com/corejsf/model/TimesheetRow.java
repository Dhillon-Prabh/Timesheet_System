package com.corejsf.model;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="RowID")
    private int id;
    
    @ManyToOne
    @JoinColumn(name="TimesheetID")
    private Timesheet timesheet;

    @Column(name="ProjectID")
    private int projectID;

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
    
    /**
     * default constructor for the timesheetrow
     */
    public TimesheetRow() {}
    
    /**
     * constructs the timesheetrow with the timesheet attached
     * @param ts
     */
    public TimesheetRow(Timesheet ts) {
        this.timesheet = ts;
    }
    
    /**
     * gets the id
     * @return
     */
    public int getId() {
        return id;
    }
    
    /**
     * gets the timesheet
     * @return
     */
    public Timesheet getTimesheet() {
        return timesheet;
    }

    /**
     * sets the timesheet
     */
    public void setTimesheet(Timesheet timesheet) {
        this.timesheet = timesheet;
    }

    /**
     * gets the projectid
     * @return
     */
    public int getProjectID() {
        return projectID;
    }

    /**
     * setst the projectid
     * @param projectID
     */
    public void setProjectID(int projectID) {
        this.projectID = projectID;
    }

    /**
     * gets the work package
     * @return
     */
    public String getWp() {
        return wp;
    }

    /**
     * sets the work package
     * @param wp
     */
    public void setWp(String wp) {
        this.wp = wp;
    }

    /**
     * get hourMon
     * @return
     */
    public double getHourMon() {
        return hourMon;
    }

    /**
     * set hourMon
     * @param hourMon
     */
    public void setHourMon(double hourMon) {
        this.hourMon = hourMon;
    }

    /**
     * get hourTue
     * @return
     */
    public double getHourTue() {
        return hourTue;
    }

    /**
     * set hourTue
     * @param hourTue
     */
    public void setHourTue(double hourTue) {
        this.hourTue = hourTue;
    }

    /**
     * get hourWed
     * @return
     */
    public double getHourWed() {
        return hourWed;
    }

    /**
     * set hourWed
     * @param hourWed
     */
    public void setHourWed(double hourWed) {
        this.hourWed = hourWed;
    }

    /**
     * get hourThur
     * @return
     */
    public double getHourThur() {
        return hourThur;
    }

    /**
     * set hourThur
     * @param hourThur
     */
    public void setHourThur(double hourThur) {
        this.hourThur = hourThur;
    }

    /**
     * get hourFri
     * @return
     */
    public double getHourFri() {
        return hourFri;
    }

    
    /**
     * set hourFri
     * @param hourFri
     */
    public void setHourFri(double hourFri) {
        this.hourFri = hourFri;
    }

    /**
     * get hourSat
     * @return
     */
    public double getHourSat() {
        return hourSat;
    }

    /**
     * sets hourSat
     * @param hourSat
     */
    public void setHourSat(double hourSat) {
        this.hourSat = hourSat;
    }

    /**
     * get hourSun
     * @return
     */
    public double getHourSun() {
        return hourSun;
    }

    /**
     * set hourSun
     * @param hourSun
     */
    public void setHourSun(double hourSun) {
        this.hourSun = hourSun;
    }
    
    /**
     * gets the total hours
     * @return
     */
    public double getTotalHours() {
        return hourMon + hourTue + hourWed + hourThur + hourFri + hourSat + hourSun;
    }
    
    /**
     * gets the note
     * @return
     */
    public String getNote() {
        return note;
    }

    /**
     * sets the note
     * @param note
     */
    public void setNote(String note) {
        this.note = note;
    }

    /**
     * gets the total hours of the day
     * @param day
     * @return
     */
    public double getDayhours(int day) {
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
            case 3:
                result = hourTue;
                break;
            case 4:
                result = hourWed;
                break;
            case 5:
                result = hourThur;
                break;
            case 6:
                result = hourFri;
                break;
            default:
                result = 0;
        }
        return result;
    }

}
