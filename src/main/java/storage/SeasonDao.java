package storage;

public interface SeasonDao {
    void addSeason(int year, String wdc, String wcc) throws EntityNotFoundException;
}
