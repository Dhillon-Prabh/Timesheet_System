package com.corejsf.model;


import java.sql.Date;

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
    private String empID;

    @Column(name="EndWeek")
    private Date endWeek;
    
    @Column(name="Overtime")
    private float overtime;
    
    @Column(name="Flextime")
    private float flextime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmpID() {
        return empID;
    }

    public void setEmpID(String empID) {
        this.empID = empID;
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
    
}
