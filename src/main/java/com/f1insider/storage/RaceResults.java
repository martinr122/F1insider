package com.f1insider.storage;

public class RaceResults {

    private int id;
    private Driver driver;
    private int position;
    private boolean finished;
    private double intervalToWinner;
    private String reason;

    public RaceResults() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public Driver getDriver() {
        return driver;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public boolean isFinished() {
        return finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }

    public double getIntervalToWinner() {
        return intervalToWinner;
    }

    public void setIntervalToWinner(double intervalToWinner) {
        this.intervalToWinner = intervalToWinner;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
