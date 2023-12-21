package com.f1insider.storage;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.*;
import java.util.List;

public class MySqlSeasonDao implements SeasonDao {
    private JdbcTemplate jdbcTemplate;

    public MySqlSeasonDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void addSeason(int year, String wdc, String wcc) throws EntityNotFoundException {
        String query = "SELECT COUNT(*) FROM season WHERE year = ?";
        int count = jdbcTemplate.queryForObject(query, Integer.class, year);
        if (count == 0) {

            String finalquery = "INSERT INTO season (year, wdc, wcc) "
                    + "VALUES (?,?,?)";

            jdbcTemplate.update(new PreparedStatementCreator() {
                public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                    PreparedStatement preparedStatement = connection.prepareStatement(finalquery, Statement.RETURN_GENERATED_KEYS);
                    preparedStatement.setInt(1, year);
                    preparedStatement.setString(2, wdc);
                    preparedStatement.setString(3, wcc);
                    return preparedStatement;


                }
            });
        }
    }

    @Override
    public boolean isSeason(int year) {
        String sql = "SELECT COUNT(*) FROM season WHERE year = ?";
        int count = jdbcTemplate.queryForObject(sql, Integer.class, year);
        if (count > 0) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public List<String> getAllSeason() {
        String sql = "SELECT DISTINCT year FROM season ORDER BY year DESC";
        return jdbcTemplate.queryForList(sql, String.class);
    }

    @Override
    public int getChampionshipsOfDriver(int idDriver) {
        String sql = "SELECT count(*) FROM season " +
                "WHERE wdc = ?";
        return jdbcTemplate.queryForObject(sql, Integer.class, idDriver);
    }

    @Override
    public String getWcc(int year) {
        String sql = "SELECT wcc FROM season WHERE year = ?";
        try{
            return jdbcTemplate.queryForObject(sql, String.class, year);
        }catch (EmptyResultDataAccessException e){
            return null;
        }
    }

    @Override
    public String getWdc(int year) {
        String sql = "SELECT wdc FROM season WHERE year = ?";
        try{
            return jdbcTemplate.queryForObject(sql, String.class, year);
        }catch (EmptyResultDataAccessException e){
            return null;
        }

    }

    public void setWcc(int year, int wcc) {
        String sql = "UPDATE season SET wcc = ? WHERE year = ?";
        jdbcTemplate.update(sql, wcc, year);
    }

    public void setWdc(int year, int wdc) {
        String sql = "UPDATE season SET wdc = ? WHERE year = ?";
        jdbcTemplate.update(sql, wdc, year);
    }

}
