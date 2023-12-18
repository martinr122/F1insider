package f1.f1insider.storage;

import java.util.List;

public interface RaceResultsDao {
    void saveRaceResults(RaceResults raceResults);
    List<RaceResults> getRaceResults(int raceId);
}
