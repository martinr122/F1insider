package com.f1insider.storage;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.springframework.dao.EmptyResultDataAccessException;

import static org.junit.jupiter.api.Assertions.*;

class MysqlRaceResultsDaoTest {

    RaceResultsDao raceResults = DaoFactory.INSTANCE.getRaceResultsDao();

    @Test
    void getRaceResults() {
        assertEquals(20, raceResults.getRaceResults(27).size());

    }

    @Test
    void getPodiumOfRace() {
        assertEquals(42, raceResults.getPodiumOfRace(27).get(0).getDriver().getId());
    }

    @Test
    void getWinsCount() {
        assertEquals(1, raceResults.getWinsCount(42));
    }

    @Test
    void getSecondCount() {
        assertEquals(1, raceResults.getSecondCount(35));
    }

    @Test
    void getThirdCount() {
        assertEquals(1, raceResults.getThirdCount(39));
    }

    @Test
    void getTotalRaces() {
        assertEquals(1, raceResults.getTotalRaces(42));
    }

    @Test
    void getHighestFinish() {
        assertEquals(1, raceResults.getHighestFinish(42));
    }
}