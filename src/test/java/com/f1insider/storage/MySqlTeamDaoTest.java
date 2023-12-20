package com.f1insider.storage;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.springframework.dao.EmptyResultDataAccessException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MySqlTeamDaoTest {
    TeamDao teamDao = DaoFactory.INSTANCE.getTeamDao();

    @Test
    public void AddAndDeleteTest() {
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
        assertEquals(team, teamDao.getTeamByName("test", 2023));
        teamDao.deleteByName("test", 2023);
        assertThrows(EmptyResultDataAccessException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                teamDao.getTeamByName("test", 2023);
            }
        });
    }

    @Test
    public void GetTeamsByYearTest() {
        List<Team> result = teamDao.getTeamsByYear(2024);
        assertEquals(3, result.size());
    }

    @Test
    public void GetTeamsByDriverTest() {
        int driverId = 8;
        List<Team> teams = teamDao.getTeamsbyDriver(driverId);
        assertTrue(teams.size() > 0);
        driverId = 100;
        List<Team> notTeam = teamDao.getTeamsbyDriver(driverId);
        assertTrue(notTeam.isEmpty());
    }

    @Test
    public void GetIDTest() {
        String name = "Oracle Red Bull Racing";
        int year = 2023;
        int id = teamDao.getID(name, year);
        assertEquals(66, id);
    }

    @Test
    public void GetByIdTest() {
        Team team = teamDao.getById(66);
        assertEquals("Oracle Red Bull Racing", team.getTeamName());
    }

    @Test
    public void GetTeamByNameTest() {
        Team team = teamDao.getTeamByName("Oracle Red Bull Racing", 2023);
        assertEquals("Oracle Red Bull Racing", team.getTeamName());
    }


    @Test
    public void GetTeamByDriverTest() {
        assertEquals("Oracle Red Bull Racing", teamDao.getTeamByDriver(5, 2023).getTeamName());
    }
}