package com.corejsf.model;


import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
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
    
    @OneToMany
    @JoinColumn(name="EmployeeID")
    private String empID;

    @Column(name="EndWeek")
    private Date endWeek;
    
    @Column(name="")
    private Date endWeek;
    
    @Column(name="EndWeek")
    private Date endWeek;
    
}
