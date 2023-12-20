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
    public void addTest() throws EntityNotFoundException {
        User userFirst = new User();
        userFirst.setUsername("admin");
        userFirst.setPassUser("hashPassword");
        assertFalse(userDao.add(userFirst));

        User userSecond = new User();
        userSecond.setUsername("test");
        userSecond.setPassUser("hashPassword");
        assertTrue(userDao.add(userSecond));
        userDao.delete("test");
        assertThrows(EmptyResultDataAccessException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                userDao.getIdbyUsername("test");
            }
        });

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
    @Test
    public void deleteTest() throws EntityNotFoundException {
        User user = new User();
        user.setUsername("deletedUser");
        user.setPassUser("Password");
        userDao.add(user);

        userDao.delete("deletedUser");
        assertThrows(EmptyResultDataAccessException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                userDao.getIdbyUsername("deletedUser");
            }
        });
    }


}