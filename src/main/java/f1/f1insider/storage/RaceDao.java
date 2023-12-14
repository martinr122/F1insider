package f1.f1insider.storage;

import java.util.List;

public interface RaceDao {

    Race getNextRace() throws EntityNotFoundException;

    Race getLastRace() throws EntityNotFoundException;

    List<Race> getAllRaces(String season);

    List<Race> getAllRaces();

    void saveRace(Race race);

    List<String> getAllSeason();

    int getRaceId(Race race);
}
