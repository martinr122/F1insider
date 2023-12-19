package com.f1insider.storage;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class MysqlRaceDaoTest {
    RaceDao raceDao = DaoFactory.INSTANCE.getRaceDao();

    @Test
    void getNextRace() throws EntityNotFoundException {
        assertEquals(null, raceDao.getNextRace());
    }

    @Test
    void getLastRace() throws EntityNotFoundException {
        Race race = new Race();
        race.setYear(2023);
        race.setPlace("dsa");
        race.setName("sdad");
        race.setWhenRace(LocalDateTime.of(2023, 12, 5, 12, 0));
        race.setWhenQuali(LocalDateTime.of(2023, 12, 4, 12, 0));
        race.setWhenFirstSession(LocalDateTime.of(2023, 12, 3, 12, 0));
        race.setWhenSecondSession(LocalDateTime.of(2023, 12, 3, 12, 0));
        race.setWhenThirdSession(LocalDateTime.of(2023, 12, 4, 12, 0));
        race.setSprintWeekend(false);

        assertEquals(race, raceDao.getLastRace());
    }


    @Test
    void getRaceId() {
        Race race = new Race();
        race.setYear(2023);
        race.setPlace("dsa");
        race.setName("sdad");
        race.setWhenRace(LocalDateTime.of(2023, 12, 5, 12, 0));
        race.setWhenQuali(LocalDateTime.of(2023, 12, 4, 12, 0));
        race.setWhenFirstSession(LocalDateTime.of(2023, 12, 3, 12, 0));
        race.setWhenSecondSession(LocalDateTime.of(2023, 12, 3, 12, 0));
        race.setWhenThirdSession(LocalDateTime.of(2023, 12, 4, 12, 0));
        race.setSprintWeekend(false);
        assertEquals(15, raceDao.getRaceId(race));
    }
}