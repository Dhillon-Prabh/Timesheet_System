package com.corejsf.model;


import java.util.Date;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="timesheet")
public class Timesheet {
    private static final long serialVersionUID = 1L;
    
    @Id
    @Column(name="TimesheetID")
    private int id;
    
    @ManyToOne
    @JoinColumn(name="EmployeeID")
    private Employee emp;

    @Column(name="EndWeek")
    private Date endWeek;
    
    @Column(name="Overtime")
    private float overtime;
    
    @Column(name="Flextime")
    private float flextime;

    public Timesheet() {
        
    }
    
    public Timesheet() {
        
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public float getOvertime() {
        return overtime;
    }

    public void setOvertime(float overtime) {
        this.overtime = overtime;
    }

    public float getFlextime() {
        return flextime;
    }

    public void setFlextime(float flextime) {
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
}
