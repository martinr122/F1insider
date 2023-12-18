package com.f1insider.storage;

import java.util.List;

public interface RaceDao {

    Race getNextRace() throws EntityNotFoundException;

    Race getLastRace() throws EntityNotFoundException;

    List<Race> getAllRaces(String season);

    void saveRace(Race race);

    int getRaceId(Race race);
}
