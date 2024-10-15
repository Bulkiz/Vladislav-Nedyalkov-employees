package com.example.vladislavnedyalkovemployees.entities;

public class PairProjectWorkDuration {

    private int firstEmpId;

    private int secondEmpId;

    private int projectID;

    private long daysWorked;


    public PairProjectWorkDuration(int firstEmpId, int secondEmpId, int projectID, long daysWorked) {
        this.firstEmpId = firstEmpId;
        this.secondEmpId = secondEmpId;
        this.projectID = projectID;
        this.daysWorked = daysWorked;
    }

    public int getFirstEmpId() {
        return firstEmpId;
    }

    public void setFirstEmpId(int firstEmpId) {
        this.firstEmpId = firstEmpId;
    }

    public int getSecondEmpId() {
        return secondEmpId;
    }

    public void setSecondEmpId(int secondEmpId) {
        this.secondEmpId = secondEmpId;
    }

    public int getProjectID() {
        return projectID;
    }

    public void setProjectID(int projectID) {
        this.projectID = projectID;
    }

    public long getDaysWorked() {
        return daysWorked;
    }

    public void setDaysWorked(long daysWorked) {
        this.daysWorked = daysWorked;
    }
}
