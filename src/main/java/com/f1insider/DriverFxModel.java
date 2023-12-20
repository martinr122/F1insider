package com.f1insider;

import com.f1insider.storage.Driver;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.image.Image;

import java.time.LocalDate;

public class DriverFxModel {

    private int id;
    private StringProperty firstName = new SimpleStringProperty();
    private StringProperty surname = new SimpleStringProperty();
    private StringProperty country = new SimpleStringProperty();
    private LocalDate birthday;
    private int raceNumber;
    private int points;
    private Image photo;

    public DriverFxModel(Driver driver){
        id = driver.getId();
        driver.getFirstName();
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getFirstName() {
        return firstName.get();
    }
    public StringProperty firstNameProperty(){return firstName;}
    public void setFirstName(String firstName) {
        this.firstName.setValue(firstName);
    }
    public String getSurname() {
        return surname.get();
    }
    public StringProperty surnameProperty(){return surname;}
    public void setSurname(String surname) {
        this.surname.setValue(surname);
    }
    public String getCountry() {
        return country.get();
    }
    public StringProperty countryProperty(){return country;}
    public void setCountry(String country) {
        this.country.setValue(country);
    }
    public LocalDate getBirthday() {
        return birthday;
    }
    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }
    public int getRaceNumber() {
        return raceNumber;
    }
    public void setRaceNumber(int raceNumber) {
        this.raceNumber = raceNumber;
    }
    public int getPoints() {
        return points;
    }
    public void setPoints(int points) {
        this.points = points;
    }
    public Image getPhoto() {
        return photo;
    }
    public void setPhoto(Image photo) {
        this.photo = photo;
    }
    public Driver getDriver(){return new Driver(id, getFirstName(), getSurname(), getCountry(),
            getBirthday(), getRaceNumber(), getPoints(), getPhoto());}
}
