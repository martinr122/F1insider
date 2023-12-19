package com.f1insider.storage;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.springframework.dao.EmptyResultDataAccessException;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class MySqlDriverDaoTest {

    DriverDao driverDao = DaoFactory.INSTANCE.getDriverDao();
    @Test
    void getAllDriversWithoutPhoto() {
        assertEquals(2, driverDao.getAllDriversWithoutPhoto().size());
    }

    @Test
    void getDriversbyTeam() {
        assertEquals(2, driverDao.getDriversbyTeam(65).size());
    }


    @Test
    void getByName() {
        Driver driver = new Driver();
        driver.setFirstName("gtr");
        driver.setSurname("grt");
        driver.setCountry("gtr");
        driver.setRaceNumber(95);
        driver.setBirthday(LocalDate.of(2005, 12, 8));

        assertEquals(driver, driverDao.getByName("gtr", "grt"));

    }

    @Test
    void getByNameLike() {
        assertEquals(2, driverDao.getByNameLike("g").size());
    }

    @Test
    void contains() {
        Driver driver = new Driver();
        driver.setFirstName("gtr");
        driver.setSurname("grt");
        driver.setCountry("gtr");
        driver.setRaceNumber(95);
        driver.setBirthday(LocalDate.of(2005, 12, 8));
        assertEquals(true, driverDao.contains(driver));
        Driver secondDriver = new Driver();
        driver.setFirstName("test");
        driver.setSurname("test");
        driver.setCountry("tst");
        driver.setRaceNumber(99);
        assertEquals(false, driverDao.contains(new Driver()));
    }

    @Test
    void getID() {
        assertEquals(7, driverDao.getID("gtr", "grt"));
        assertThrows(EmptyResultDataAccessException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                driverDao.getID("test", "test");
            }
        });
    }
}