package com.f1insider.storage;

import java.util.Objects;

public class Team implements Comparable<Team>{
    private int idTeam;
    private String teamName;
    private String nameEngine;
    private String namePrincipal;
    private String nameFounder;
    private String country;
    private String nameMonopost;
    private int year;
    private int points;
    private String teamColor;
    private Driver firstDriver;
    private Driver secondDriver;

    public Team(String teamName, int points) {
        this.teamName = teamName;
        this.points = points;
    }

    public Team(int idTeam, String teamName, String nameEngine, String namePrincipal, String nameFounder, String country, String nameMonopost, int year, int points, String teamColor, Driver firstDriver, Driver secondDriver) {
        this.idTeam = idTeam;
        this.teamName = teamName;
        this.nameEngine = nameEngine;
        this.namePrincipal = namePrincipal;
        this.nameFounder = nameFounder;
        this.country = country;
        this.nameMonopost = nameMonopost;
        this.year = year;
        this.points = points;
        this.teamColor = teamColor;
        this.firstDriver = firstDriver;
        this.secondDriver = secondDriver;
    }

    public Team() {

    }

    public int getIdTeam() {
        return idTeam;
    }

    public void setIdTeam(int idTeam) {
        this.idTeam = idTeam;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public String getNameEngine() {
        return nameEngine;
    }

    public void setNameEngine(String nameEngine) {
        this.nameEngine = nameEngine;
    }

    public String getNamePrincipal() {
        return namePrincipal;
    }

    public void setNamePrincipal(String namePrincipal) {
        this.namePrincipal = namePrincipal;
    }

    public String getNameFounder() {
        return nameFounder;
    }

    public void setNameFounder(String nameFounder) {
        this.nameFounder = nameFounder;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getNameMonopost() {
        return nameMonopost;
    }

    public void setNameMonopost(String nameMonopost) {
        this.nameMonopost = nameMonopost;
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

    @Override
    public String toString() {
        return teamName;
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

    public String getTeamColor() {
        return teamColor;
    }

    public void setTeamColor(String teamColor) {
        this.teamColor = teamColor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Team team = (Team) o;
        return teamName.equals(team.teamName) && nameEngine.equals(team.nameEngine) && namePrincipal.equals(team.namePrincipal) && nameFounder.equals(team.nameFounder) && country.equals(team.country);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idTeam);
    }

    @Override
    public int compareTo(Team o) {
        return Integer.compare(o.getPoints(), this.getPoints());
    }
}
