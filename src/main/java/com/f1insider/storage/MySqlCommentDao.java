package com.f1insider.storage;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.*;
import java.util.List;

public class MySqlCommentDao implements CommentDao {
    private JdbcTemplate jdbcTemplate;

    public MySqlCommentDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private RowMapper<Comment> commentRM() {
        return new RowMapper<Comment>() {

            @Override
            public Comment mapRow(ResultSet rs, int rowNum) throws SQLException {
                Comment comment = new Comment();
                comment.setId(rs.getInt("idComment"));
                comment.setComment(rs.getString("comment"));
                comment.setUserId(rs.getInt("User_idUser"));
                comment.setRaceId(rs.getInt("Race_idRace"));
                return comment;
            }
        };
    }


    @Override
    public void add(Comment comment) {
        String sql = "INSERT INTO comments (comment, User_idUser, race_idRace) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, comment.getComment(), comment.getUserId(), comment.getRaceId());
    }

    @Override
    public List<Comment> allCommentByRace(int id) {
        String sql = "SELECT * FROM comments WHERE race_idRace = ?";
        return jdbcTemplate.query(sql, commentRM(), id);
    }
}
