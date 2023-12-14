package f1.f1insider.storage;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Race {
    private int id;
    private int year;
    private LocalDateTime whenRace;
    private LocalDateTime whenQuali;
    private LocalDateTime whenFirstSession;
    private LocalDateTime whenSecondSession;
    private LocalDateTime whenThirdSession;
    private boolean isSprintWeekend;
    private String place;
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isSprintWeekend() {
        return isSprintWeekend;
    }

    public void setSprintWeekend(boolean sprintWeekend) {
        isSprintWeekend = sprintWeekend;
    }

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

    public LocalDateTime getWhenRace() {
        return whenRace;
    }

    public void setWhenRace(LocalDateTime whenRace) {
        this.whenRace = whenRace;
    }

    public LocalDateTime getWhenQuali() {
        return whenQuali;
    }

    public void setWhenQuali(LocalDateTime whenQuali) {
        this.whenQuali = whenQuali;
    }

    public LocalDateTime getWhenFirstSession() {
        return whenFirstSession;
    }

    public void setWhenFirstSession(LocalDateTime whenFirstSession) {
        this.whenFirstSession = whenFirstSession;
    }

    public LocalDateTime getWhenSecondSession() {
        return whenSecondSession;
    }

    public void setWhenSecondSession(LocalDateTime whenSecondSession) {
        this.whenSecondSession = whenSecondSession;
    }

    public LocalDateTime getWhenThirdSession() {
        return whenThirdSession;
    }

    public void setWhenThirdSession(LocalDateTime whenThirdSession) {
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
