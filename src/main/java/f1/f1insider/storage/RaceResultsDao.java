package f1.f1insider.storage;

import java.util.List;

public interface RaceResultsDao {
    void saveRaceResults(RaceResults raceResults);
    void addDriversToRaceResults(int raceId, int driverId, int position);
    List<RaceResults> getRaceResults(int raceId);
}
