package com.f1insider;

import com.f1insider.storage.Driver;
import com.f1insider.storage.Team;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class TeamFxModel {

    private int idTeam;
    private StringProperty teamName = new SimpleStringProperty();
    private StringProperty nameEngine = new SimpleStringProperty();
    private StringProperty namePrincipal = new SimpleStringProperty();
    private StringProperty nameFounder = new SimpleStringProperty();
    private StringProperty country = new SimpleStringProperty();
    private StringProperty nameMonopost = new SimpleStringProperty();
    private int year;
    private int points;
    private StringProperty teamColor = new SimpleStringProperty();
    private Driver firstDriver;
    private Driver secondDriver;
    public TeamFxModel(Team team){
        idTeam = team.getIdTeam();
        setTeamName(team.getTeamName());
        setNameEngine(team.getNameEngine());
        setNamePrincipal(team.getNamePrincipal());
        setNameFounder(team.getNameFounder());
        setCountry(team.getCountry());
        setNameMonopost(team.getNameMonopost());
        year = team.getYear();
        points = team.getPoints();
        setTeamColor(team.getTeamColor());
    }

    public int getIdTeam() {
        return idTeam;
    }
    public void setIdTeam(int idTeam) {
        this.idTeam = idTeam;
    }
    public String getTeamName() {
        return teamName.get();
    }
    public StringProperty teamNameProperty() {
        return teamName;
    }
    public void setTeamName(String teamName) {
        this.teamName.setValue(teamName);
    }
    public String getNameEngine() {
        return nameEngine.get();
    }
    public StringProperty nameEngineProperty() {
        return nameEngine;
    }
    public void setNameEngine(String nameEngine) {
        this.nameEngine.set(nameEngine);
    }
    public String getNamePrincipal() {
        return namePrincipal.get();
    }
    public StringProperty namePrincipalProperty() {
        return namePrincipal;
    }
    public void setNamePrincipal(String namePrincipal) {
        this.namePrincipal.set(namePrincipal);
    }
    public String getNameFounder() {
        return nameFounder.get();
    }
    public StringProperty nameFounderProperty() {
        return nameFounder;
    }

    public void setNameFounder(String nameFounder) {
        this.nameFounder.set(nameFounder);
    }

    public String getCountry() {
        return country.get();
    }

    public StringProperty countryProperty() {
        return country;
    }

    public void setCountry(String country) {
        this.country.set(country);
    }

    public String getNameMonopost() {
        return nameMonopost.get();
    }

    public StringProperty nameMonopostProperty() {
        return nameMonopost;
    }

    public void setNameMonopost(String nameMonopost) {
        this.nameMonopost.set(nameMonopost);
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public String getTeamColor() {
        return teamColor.get();
    }

    public StringProperty teamColorProperty() {
        return teamColor;
    }

    public void setTeamColor(String teamColor) {
        this.teamColor.set(teamColor);
    }

    public Driver getFirstDriver() {
        return firstDriver;
    }

    public void setFirstDriver(Driver firstDriver) {
        this.firstDriver = firstDriver;
    }

    public Driver getSecondDriver() {
        return secondDriver;
    }

    public void setSecondDriver(Driver secondDriver) {
        this.secondDriver = secondDriver;
    }
    public Team getTeam(){return new Team(idTeam, getTeamName(), getNameEngine(), getNamePrincipal(), getNameFounder(),
            getCountry(), getNameMonopost(), year, points, getTeamColor(), getFirstDriver(), getSecondDriver());}
}
