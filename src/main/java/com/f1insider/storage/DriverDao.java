package com.f1insider.storage;

import java.util.List;

public interface DriverDao {

    List<Driver> getAllDriversWithoutPhoto();
    List<Driver> getAllFromSeason(int season);
    List<Driver> getDriversbyTeam(int idTeam);
    void add(Driver driver);
    Driver getByName (String firstName, String surname);
    List<Driver> getByNameLike (String nameLike);
    boolean contains(Driver driver);
    int getID(String firstName, String surname);
    Driver getById(int id);
}
