package f1.f1insider.storage;

import java.util.List;

public interface SeasonDao {
    void addSeason(int year, String wdc, String wcc) throws EntityNotFoundException;

    boolean isSeason(int year);

    List<String> getAllSeason();
    int getChampionshipsOfDriver(int idDriver);
}
