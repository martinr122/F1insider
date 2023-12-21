package com.f1insider.storage;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MySqlSeasonDaoTest {
    SeasonDao seasonDao = DaoFactory.INSTANCE.getSeasonDao();


    @Test
    void isSeason() {
        assertEquals(true,seasonDao.isSeason(2021));
        assertEquals(false,seasonDao.isSeason(1500));
    }

    @Test
    void getAllSeason() {
        assertEquals(6,seasonDao.getAllSeason().size());
    }
    @Test
    void getWcc(int year){
        assertNull(seasonDao.getWcc(2023));
    }
    @Test
    void getWdc(int year) {
        assertNull(seasonDao.getWdc(2023));
    }

}