package storage;

import java.time.LocalDate;

public class Race {
    private int id;
    private int year;
    private LocalDate whenRace;
    private LocalDate whenQuali;
    private LocalDate whenFirstSession;
    private LocalDate whenSecondSession;
    private LocalDate whenThirdSession;
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

    public LocalDate getWhenRace() {
        return whenRace;
    }

    public void setWhenRace(LocalDate whenRace) {
        this.whenRace = whenRace;
    }

    public LocalDate getWhenQuali() {
        return whenQuali;
    }

    public void setWhenQuali(LocalDate whenQuali) {
        this.whenQuali = whenQuali;
    }

    public LocalDate getWhenFirstSession() {
        return whenFirstSession;
    }

    public void setWhenFirstSession(LocalDate whenFirstSession) {
        this.whenFirstSession = whenFirstSession;
    }

    public LocalDate getWhenSecondSession() {
        return whenSecondSession;
    }

    public void setWhenSecondSession(LocalDate whenSecondSession) {
        this.whenSecondSession = whenSecondSession;
    }

    public LocalDate getWhenThirdSession() {
        return whenThirdSession;
    }

    public void setWhenThirdSession(LocalDate whenThirdSession) {
        this.whenThirdSession = whenThirdSession;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    @Override
    public String toString() {
        return place.trim() + " / " + year;
    }
}
