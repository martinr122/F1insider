package f1.f1insider.storage;

import java.util.List;

public interface RaceResultsDao {
    void saveRaceResults(RaceResults raceResults);
    List<RaceResults> getRaceResults(int idRace);
    List<RaceResults> getPodiumOfRace(int idRace);
    int getWinsCount(int idDriver);
    int getSecondCount(int idDriver);
    int getThirdCount(int idDriver);
    int getTotalRaces(int idDriver);
    int getHighestFinish(int idDriver);
}