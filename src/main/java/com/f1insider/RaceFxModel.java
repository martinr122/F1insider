package com.f1insider;

import com.f1insider.storage.Race;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.time.LocalDateTime;

public class RaceFxModel {

    private int id;
    private int year;
    private LocalDateTime whenRace;
    private LocalDateTime whenQuali;
    private LocalDateTime whenFirstSession;
    private LocalDateTime whenSecondSession;
    private LocalDateTime whenThirdSession;
    private boolean isSprintWeekend;
    private StringProperty place = new SimpleStringProperty();
    private StringProperty name = new SimpleStringProperty();

    public RaceFxModel(Race race) {
        this.id = race.getId();
        this.year = race.getYear();
        this.whenRace = race.getWhenRace();
        this.whenQuali = race.getWhenQuali();
        this.whenFirstSession = race.getWhenFirstSession();
        this.whenSecondSession = race.getWhenSecondSession();
        this.whenThirdSession = race.getWhenThirdSession();
        this.isSprintWeekend = race.isSprintWeekend();
        setPlace(race.getPlace());
        setName(race.getName());
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

    public boolean isSprintWeekend() {
        return isSprintWeekend;
    }

    public void setSprintWeekend(boolean sprintWeekend) {
        isSprintWeekend = sprintWeekend;
    }

    public String getPlace() {
        return place.get();
    }
    public StringProperty placeProperty(){return place;}
    public void setPlace(String place) {
        this.place.setValue(place);
    }

    public String getName() {
        return name.get();
    }
    public StringProperty nameProperty(){return name;}

    public void setName(String name) {
        this.name.setValue(name);
    }
    public Race getRace(){return new Race(id, year, whenRace, whenQuali, whenFirstSession, whenSecondSession, whenThirdSession,
            isSprintWeekend, getPlace(), getName());}
}
