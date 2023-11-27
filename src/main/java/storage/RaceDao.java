package storage;

import java.util.Date;
import java.util.List;

public interface RaceDao {

        Race getNextRace() throws EntityNotFoundException;
        Race getLastRace() throws EntityNotFoundException;
        List<Race> getAllRaces(String season);
        List<Race> getAllRaces();
        void saveRace(Race race);
        List<String> getAllSeason();
}
