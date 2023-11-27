package storage;

public class Team {
    private int idTeam;
    private String nameTeam;
    private String nameEngine;
    private String namePrincipal;
    private String nameFounder;
    private String country;
    private String nameMonopost;
    private int year;
    private int points;

    public Team(String nameTeam, int points) {
        this.nameTeam = nameTeam;
        this.points = points;
    }
}
