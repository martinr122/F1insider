package com.f1insider.storage;

import java.util.List;

public interface TeamDao {
    void add(Team team);

    List<Team> getTeamsByYear(int year);

    Team getById(int id);

    List<Team> getTeamsbyDriver(int idDriver);

    Team getTeamByName(String name, int year);

    void deleteByName(String name, int year);

    int getID(String name, int year);
    void addDriversToTeam(int teamId, int driverId);
    void changeContract(int teamId, int driverId);
    int getContract(int teamId, int driverId);
    Team getTeamByDriver(int idDriver, int year);
}
