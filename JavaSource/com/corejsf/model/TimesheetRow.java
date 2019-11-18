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
    private BigDecimal hourMon = BigDecimal.ZERO;

    @Column(name="HourTue")
    private BigDecimal hourTue = BigDecimal.ZERO;

    @Column(name="HourWed")
    private BigDecimal hourWed = BigDecimal.ZERO;

    @Column(name="HourThur")
    private BigDecimal hourThur = BigDecimal.ZERO;

    @Column(name="HourFri")
    private BigDecimal hourFri = BigDecimal.ZERO;

    @Column(name="HourSat")
    private BigDecimal hourSat = BigDecimal.ZERO;

    @Column(name="HourSun")
    private BigDecimal hourSun = BigDecimal.ZERO;

    @Column(name="Note")
    private String note;
    
    public TimesheetRow() {}
    
    public TimesheetRow(Timesheet ts) {
        this.timesheet = ts;
        hourMon = BigDecimal.ZERO;
        hourTue = BigDecimal.ZERO;
        hourWed = BigDecimal.ZERO;
        hourThur = BigDecimal.ZERO;
        hourFri = BigDecimal.ZERO;
        hourSat = BigDecimal.ZERO;
        hourSun = BigDecimal.ZERO;
    }
    
    public int getId() {
        return id;
    }
    
    public Timesheet getTimesheet() {
        return timesheet;
    }

    public void setTimesheet(Timesheet timesheet) {
        this.timesheet = timesheet;
    }

    public int getProjectID() {
        return projectID;
    }

    public void setProjectID(int projectID) {
        this.projectID = projectID;
    }

    public String getWp() {
        return wp;
    }

    public void setWp(String wp) {
        this.wp = wp;
    }

    public BigDecimal getHourMon() {
        return hourMon;
    }

    public void setHourMon(BigDecimal hourMon) {
        this.hourMon = hourMon;
    }

    public BigDecimal getHourTue() {
        return hourTue;
    }

    public void setHourTue(BigDecimal hourTue) {
        this.hourTue = hourTue;
    }

    public BigDecimal getHourWed() {
        return hourWed;
    }

    public void setHourWed(BigDecimal hourWed) {
        this.hourWed = hourWed;
    }

    public BigDecimal getHourThur() {
        return hourThur;
    }

    public void setHourThur(BigDecimal hourThur) {
        this.hourThur = hourThur;
    }

    public BigDecimal getHourFri() {
        return hourFri;
    }

    public void setHourFri(BigDecimal hourFri) {
        this.hourFri = hourFri;
    }

    public BigDecimal getHourSat() {
        return hourSat;
    }

    public void setHourSat(BigDecimal hourSat) {
        this.hourSat = hourSat;
    }

    public BigDecimal getHourSun() {
        return hourSun;
    }

    public void setHourSun(BigDecimal hourSun) {
        this.hourSun = hourSun;
    }
    
    public BigDecimal getTotalHours() {
        BigDecimal sum = BigDecimal.ZERO;
        sum.add(hourMon);
        sum.add(hourTue);
        sum.add(hourWed);
        sum.add(hourThur);
        sum.add(hourFri);
        sum.add(hourSat);
        sum.add(hourSun);
        return sum;
    }
    
    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public BigDecimal getDayhours(int day) {
        BigDecimal result;
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
                result = BigDecimal.ZERO;
        }
        return result;
    }

}
