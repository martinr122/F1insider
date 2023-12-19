package f1.f1insider.storage;

import java.util.List;

public interface TeamDao {
    void add(Team team);

    List<Team> getTeamsByYear(int year);

    List<Team> getTeamsbyDriver(int idDriver);

    Team getTeamByName(String name, int year);

    void deleteByName(String name, int year);

    int getID(String name, int year);

    void addDriversToTeam(int teamId, int driverId);
    Team getTeamByDriver(int idDriver, int year);
}
