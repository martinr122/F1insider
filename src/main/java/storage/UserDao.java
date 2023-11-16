package storage;

import java.util.List;

public interface UserDao {

        String givePassword(String username) throws EntityNotFoundException;
        User getById(long id) throws EntityNotFoundException;
        Boolean add(User user);
        void delete(long id) throws EntityNotFoundException;

    }


