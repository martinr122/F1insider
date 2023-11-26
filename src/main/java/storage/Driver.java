package storage;

import java.util.Date;

public class Driver {
    private int id;
    private String firstName;
    private String surname;
    private String country;
    private Date birthday;
    private int raceNumber;
    private int points;

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

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
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

    public Driver(String firstName, String surname, int points) {
        this.firstName = firstName;
        this.surname = surname;
        this.points = points;
    }
}
