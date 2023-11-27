package storage;

import java.io.InputStream;

public interface DriverDao {

    InputStream getImage(String firstName, String surname);

}
