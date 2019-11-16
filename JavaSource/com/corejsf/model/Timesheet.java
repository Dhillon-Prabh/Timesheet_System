package com.corejsf.model;


import java.util.Date;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.corejsf.access.TimesheetRowManager;

@Entity
@Table(name="timesheet")
public class Timesheet {
    /** Manager for category objects.*/
    @Inject private TimesheetRowManager tsRowManager;
    
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
    
    @Id
    @Column(name="TimesheetID")
    private int id;
    
    @ManyToOne
    @JoinColumn(name="EmployeeID")
    private Employee emp;

    @Column(name="EndWeek")
    private Date endWeek;
    
    @Column(name="Overtime")
    private BigDecimal overtime;
    
    @Column(name="Flextime")
    private BigDecimal flextime;
    
    /**
     * Constructor for Timesheet.
     * Initialize a Timesheet with no rows, no employee and
     * to the current date.
     */
    public Timesheet() {}

    /**
     * Creates a Timesheet object with all fields set. 
     * 
     * @param user The owner of the timesheet
     * @param end The date of the end of the week for the timesheet (Friday)
     * @param charges The detailed hours charged for the week for this 
     *        timesheet
     */
    public Timesheet(final Employee user, final Date end,
            final List<TimesheetRow> charges) {
        emp = user;
        checkFriday(end);
        endWeek = end;
        for (TimesheetRow row : charges) {
            tsRowManager.persist(row);
        }
    }
    
    public int getId() {
        return id;
    }

    public Employee getEmp() {
        return emp;
    }

    public void setEmp(Employee empID) {
        this.emp = empID;
    }

    public Date getEndWeek() {
        return endWeek;
    }

    public void setEndWeek(Date endWeek) {
        this.endWeek = endWeek;
    }

    public BigDecimal getOvertime() {
        return overtime;
    }

    public void setOvertime(BigDecimal overtime) {
        this.overtime = overtime;
    }

    public BigDecimal getFlextime() {
        return flextime;
    }

    public void setFlextime(BigDecimal flextime) {
        this.flextime = flextime;
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
    
    /**
     * Getter for timesheet row details.
     * @return the details
     */
    public List<TimesheetRow> getDetails() {
       return tsRowManager.getByTimesheet(this.id);
    }

    /**
     * Sets the details of the timesheet.
     *
     * @param newDetails new weekly charges to set
     */
    public void setDetails(final ArrayList<TimesheetRow> newDetails) {
        for(int i = 0; i < newDetails.size(); i++) {
            tsRowManager.persist(newDetails.get(i));
        }
    }
    
    /**
     * Calculates the total hours.
     *
     * @return total hours for timesheet.
     */
    public BigDecimal getTotalHours() {
        BigDecimal sum = BigDecimal.ZERO;
        List<TimesheetRow> details = tsRowManager.getByTimesheet(this.id);
        for (TimesheetRow row : details) {
            sum = sum.add(BigDecimal.valueOf(row.getTotalHours()));
        }
        return sum;
    }

    /**
     * Checks to see if timesheet total nets 40 hours.
     * @return true if FULL_WORK_WEEK == hours -flextime - overtime
     */
    public boolean isValid() {
        BigDecimal net = getTotalHours();
        if (overtime != null) {
            net = net.subtract(overtime);
        }
        if (flextime != null) {
            net = net.subtract(flextime);
        }
        return net.equals(FULL_WORK_WEEK);
    }

    /**
     * Deletes the specified row from the timesheet.
     *
     * @param rowToRemove
     *            the row to remove from the timesheet.
     */
    public void deleteRow(final TimesheetRow rowToRemove) {
        tsRowManager.remove(rowToRemove);
    }

    /**
     * Add an empty row to to the timesheet.
     */
    public void addRow() {
        tsRowManager.persist(new TimesheetRow(this));
    }

}
