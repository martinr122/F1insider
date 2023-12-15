package f1.f1insider.storage;

public interface RaceResultsDao {
    void saveRaceResults(RaceResults raceResults);
    void addDriversToRaceResults(int raceId, int driverId);
}
