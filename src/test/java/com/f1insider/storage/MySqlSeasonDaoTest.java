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
//TODO
    }
    @Test
    void getWdc(int year) {
//TODO
    }
    @Test
    void setWcc(int year, String wcc){
//TODO
    }
    @Test
    void setWdc(int year, String wdc){
//TODO
    }
}