package com.f1insider.storage;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.springframework.dao.EmptyResultDataAccessException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MySqlTeamDaoTest {
    TeamDao teamDao = DaoFactory.INSTANCE.getTeamDao();
    @Test
    public void testAddAndDelete() {
        Team team = new Team();
        team.setTeamName("test");
        team.setNameEngine("test");
        team.setNamePrincipal("test");
        team.setNameFounder("test");
        team.setCountry("TST");
        team.setNameMonopost("test");
        team.setYear(2023);
        team.setTeamColor("FFFFFF");
        teamDao.add(team);
        assertEquals(team,teamDao.getTeamByName("test", 2023));
        teamDao.deleteByName("test", 2023);
        assertThrows(EmptyResultDataAccessException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                teamDao.getTeamByName("test", 2023);
            }
        });
    }
    @Test
    public void testGetTeamsByYear() {
        List<Team> result = teamDao.getTeamsByYear(2021);
        assertEquals(5, result.size());
    }
    @Test
    public void testGetTeamsByDriver() {
        int driverId = 8;
        List<Team> teams = teamDao.getTeamsbyDriver(driverId);
        assertTrue(teams.size() > 0);
        driverId = 100;
        List<Team> notTeam = teamDao.getTeamsbyDriver(driverId);
        assertTrue(notTeam.isEmpty());
    }
    @Test
    public void testGetID() {
        //TODO zmena mena pri hotovej databaze
        String name = "fer";
        int year = 2023;
        int id = teamDao.getID(name, year);
        assertEquals(57, id);
    }
}