package com.f1insider.storage;

import javafx.scene.image.Image;

import java.time.LocalDate;
import java.util.Objects;


public class Driver {
    private int id;
    private String firstName;
    private String surname;
    private String country;
    private LocalDate birthday;
    private int raceNumber;
    private int points;
    private Image photo;

    public Driver() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Override
    public String toString() {
        return Character.toUpperCase(firstName.charAt(0)) + firstName.substring(1) + " "
                + Character.toUpperCase(surname.charAt(0)) + surname.substring(1);
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
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

    public Image getPhoto() {
        return photo;
    }

    public void setPhoto(Image photo) {
        this.photo = photo;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public Driver(String firstName, String surname, int points) {
        this.firstName = firstName;
        this.surname = surname;
        this.points = points;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Driver driver = (Driver) o;
        return raceNumber == driver.raceNumber && points == driver.points && Objects.equals(firstName, driver.firstName) && Objects.equals(surname, driver.surname) && Objects.equals(country, driver.country) && Objects.equals(birthday, driver.birthday);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}
