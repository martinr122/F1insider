package storage;

import java.util.Date;

public interface RaceDao {

        Race getNextRace() throws EntityNotFoundException;

        Race getLastRace() throws EntityNotFoundException;
}
