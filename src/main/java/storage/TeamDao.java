package storage;

import java.util.List;

public interface TeamDao {
    void add(Team team);
    List<Team> getTeamsByYear(int year);
    Team getTeamByName(String name, int year);
    void deleteByName(String name, int year);

}
