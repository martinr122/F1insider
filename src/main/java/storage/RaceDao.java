package storage;

import java.util.Date;

public interface RaceDao {

        String getNextRace(Date date) throws EntityNotFoundException;

        String getNextRaceDate(Date date) throws EntityNotFoundException;

        String getLastRace(Date date) throws EntityNotFoundException;
}
