package storage;

import java.util.List;

public interface SeasonDao {
    void addSeason(int year, String wdc, String wcc) throws EntityNotFoundException;
    boolean isSeason(int year);
    List<String> getAllSeason();
}
