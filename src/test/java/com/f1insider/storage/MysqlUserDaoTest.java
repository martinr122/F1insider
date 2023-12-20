package com.f1insider.storage;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.springframework.dao.EmptyResultDataAccessException;

import static org.junit.jupiter.api.Assertions.*;

class MysqlUserDaoTest {

    private UserDao userDao;

    public MysqlUserDaoTest() {
        userDao = DaoFactory.INSTANCE.getUserDao();
    }

    @Test
    void testGetByIdFound() throws EntityNotFoundException {
        int id = 10;
        User user = userDao.getById(id);
        assertNotNull(user);
        assertEquals("admin", user.getUsername());
        assertEquals(true, user.isAdmin());

        int newId = 100;
        assertThrows(EmptyResultDataAccessException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                userDao.getById(newId);
            }
        });
    }

    @Test
    public void addTakenTest() {
        User user = new User();
        user.setUsername("admin");
        user.setPassUser("hashPassword");
        Boolean result = userDao.add(user);
        assertFalse(result);
    }

    @Test
    public void isAdminTest() throws EntityNotFoundException {
        assertThrows(EmptyResultDataAccessException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                userDao.isAdmin("usernameDoesNotExist");
            }
        });
        assertTrue(userDao.isAdmin("admin"));
        assertFalse(userDao.isAdmin("jankohrasko"));
    }


}