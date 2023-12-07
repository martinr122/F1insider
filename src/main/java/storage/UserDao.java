package storage;

import java.util.List;

public interface UserDao {

    User getById(long id) throws EntityNotFoundException;
    int getIdbyUsername(String username);

    String givePassword(String username) throws EntityNotFoundException;
    Boolean add(User user);
    boolean isAdmin(String username) throws EntityNotFoundException;

    void delete(long id) throws EntityNotFoundException;
}


