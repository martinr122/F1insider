package storage;

import java.util.Date;

public class Race {
    private int id;
    private int year;
    private Date whenRace;
    private Date whenQuali;
    private Date whenFirstSession;
    private Date whenSecondSession;
    private Date whenThirdSession;
    private boolean isSprintWeekend;

    public boolean isSprintWeekend() {
        return isSprintWeekend;
    }

    public void setSprintWeekend(boolean sprintWeekend) {
        isSprintWeekend = sprintWeekend;
    }

    private String place;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public Date getWhenRace() {
        return whenRace;
    }

    public void setWhenRace(Date whenRace) {
        this.whenRace = whenRace;
    }

    public Date getWhenQuali() {
        return whenQuali;
    }

    public void setWhenQuali(Date whenQuali) {
        this.whenQuali = whenQuali;
    }

    public Date getWhenFirstSession() {
        return whenFirstSession;
    }

    public void setWhenFirstSession(Date whenFirstSession) {
        this.whenFirstSession = whenFirstSession;
    }

    public Date getWhenSecondSession() {
        return whenSecondSession;
    }

    public void setWhenSecondSession(Date whenSecondSession) {
        this.whenSecondSession = whenSecondSession;
    }

    public Date getWhenThirdSession() {
        return whenThirdSession;
    }

    public void setWhenThirdSession(Date whenThirdSession) {
        this.whenThirdSession = whenThirdSession;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }
}
