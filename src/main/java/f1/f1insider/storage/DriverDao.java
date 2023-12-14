package f1.f1insider.storage;

import java.util.List;

public interface DriverDao {

    List<Driver> getAllDriversWithoutPhoto();
    List<Driver> getDriversbyTeam(int idTeam);
    void add(Driver driver);
    Driver getByName (String firstName, String surname);
    List<Driver> getByNameLike (String nameLike);
    boolean contains(Driver driver);
    int getID(String firstName, String surname);
}
