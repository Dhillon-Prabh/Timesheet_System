package com.corejsf.model;


import java.util.Date;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Model for timesheet table.
 * @author jham
 * @author psingh
 * @version 1.0
 */
@Entity
@Table(name = "timesheet")
public class Timesheet implements Serializable {
    
    /** Number of days in a week. */
    public static final int DAYS_IN_WEEK = 7;
    
    /** Number of hours in a day as double. */
    public static final double HOURS_IN_A_DAY = 24.0;
    
    /** Number of hours in a day. */
    public static final BigDecimal HOURS_IN_DAY =
           new BigDecimal(HOURS_IN_A_DAY).setScale(1, BigDecimal.ROUND_HALF_UP);
    
    /** Number of work hours in week as double. */
    public static final double WORK_HOURS = 40.0;

    /** Full work week in units of hours. */
    public static final BigDecimal FULL_WORK_WEEK =
            new BigDecimal(WORK_HOURS).setScale(1, BigDecimal.ROUND_HALF_UP);

    /** Serial version number. */
    private static final long serialVersionUID = 2L;
   
    /** primary key. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TimesheetID", updatable = false, nullable = false)
    private int id;
    
    /** employee from employee table. */
    @ManyToOne
    @JoinColumn(name = "EmployeeID")
    private Employee emp;

    /** end week. */
    @Column(name = "EndWeek")
    private Date endWeek;
    
    /**
     * Constructor for Timesheet.
     * Initialize a Timesheet with no rows, no employee and
     * to the current date.
     */
    public Timesheet() {
        Calendar c = new GregorianCalendar();
        int currentDay = c.get(Calendar.DAY_OF_WEEK);
        int leftDays = Calendar.FRIDAY - currentDay;
        c.add(Calendar.DATE, leftDays);
        endWeek = c.getTime();
    }

    /**
     * Creates a Timesheet object with all fields set. 
     * 
     * @param user The owner of the timesheet
     * @param end The date of the end of the week for the timesheet (Friday)
     *        timesheet
     */
    public Timesheet(final Employee user, final Date end) {
        emp = user;
        checkFriday(end);
        endWeek = end;
    }
    
    /**
     * gets the id.
     * @return id
     */
    public int getId() {
        return id;
    }

    /**
     * gets the emp.
     * @return Employee
     */
    public Employee getEmp() {
        return emp;
    }

    /**
     * sets the emp.
     * @param empID employee id
     */
    public void setEmp(Employee empID) {
        this.emp = empID;
    }

    /**
     * gets the ending week.
     * @return Date
     */
    public Date getEndWeek() {
        return endWeek;
    }

    /**
     * sets the ending week.
     * @param endWeek Date
     */
    public void setEndWeek(Date endWeek) {
        this.endWeek = endWeek;
    }
    
    /**
     * Verify date is a friday.
     * @param end a date which should be on a Friday
     * @throws IllegalArgumentException if date is not on Friday.
     */
    private void checkFriday(final Date end) {
        Calendar c = new GregorianCalendar();
        c.setTime(end);
        int currentDay = c.get(Calendar.DAY_OF_WEEK);
        if (currentDay != Calendar.FRIDAY) {
            throw new IllegalArgumentException("EndWeek must be a Friday");
        }
    }
    
    /**
     * Calculate the week number of the timesheet.
     * @return the calculated week number
     */
    public int getWeekNumber() {
        Calendar c = new GregorianCalendar();
        c.setTime(endWeek);
        c.setFirstDayOfWeek(Calendar.SATURDAY);
        return c.get(Calendar.WEEK_OF_YEAR);
    }
    
    /**
     * Sets the end of week based on the week number.
     *
     * @param weekNo the week number of the timesheet week
     * @param weekYear the year of the timesheet
     */
    public void setWeekNumber(final int weekNo, final int weekYear) {
        Calendar c = new GregorianCalendar();
        c.setFirstDayOfWeek(Calendar.SATURDAY);
        c.setTime(endWeek);
        c.setWeekDate(weekYear, weekNo, Calendar.FRIDAY);
        endWeek = c.getTime();
    }
    
    /**
     * Calculate the time sheet's end date as a string.
     * @return the endWeek as string
     */
    public String getWeekEnding() {
        Calendar c = new GregorianCalendar();
        c.setTime(endWeek);
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        month += 1;
        int day = c.get(Calendar.DAY_OF_MONTH);
        return month + "/" + day + "/" + year;
    }

}
