package com.f1insider.storage;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MySqlCommentDaoTest {
    CommentDao commentDao = DaoFactory.INSTANCE.getCommentDao();

    @Test
    void allCommentByRace() {
        assertEquals(20, commentDao.allCommentByRace(12).size());
    }
}