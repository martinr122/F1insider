package com.f1insider.storage;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;

import java.sql.*;
import java.util.List;

public class MysqlRaceDao implements RaceDao {

    private JdbcTemplate jdbcTemplate;

    public MysqlRaceDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private RowMapper<Race> raceRM() {
        return new RowMapper<Race>() {

            @Override
            public Race mapRow(ResultSet rs, int rowNum) throws SQLException {
                Race race = new Race();
                race.setId(rs.getInt("idRace"));
                race.setYear(rs.getInt("Season_year"));
                race.setPlace(rs.getString("place"));
                race.setName(rs.getString("name"));
                race.setWhenRace(rs.getDate("when_race").toLocalDate().atTime(rs.getTime("when_race").toLocalTime()));
                race.setWhenQuali(rs.getDate("when_quali").toLocalDate().atTime(rs.getTime("when_quali").toLocalTime()));
                race.setWhenFirstSession(rs.getDate("when_1_session").toLocalDate().atTime(rs.getTime("when_1_session").toLocalTime()));
                race.setWhenSecondSession(rs.getDate("when_2_session").toLocalDate().atTime(rs.getTime("when_2_session").toLocalTime()));
                race.setWhenThirdSession(rs.getDate("when_3_session").toLocalDate().atTime(rs.getTime("when_3_session").toLocalTime()));
                if (rs.getInt("is_sprint_weekend") == 1) {
                    race.setSprintWeekend(true);
                } else {
                    race.setSprintWeekend(false);
                }
                return race;
            }
        };
    }

    public Race getNextRace() throws EntityNotFoundException {
        String sql = "SELECT * FROM race WHERE when_race >= CURRENT_DATE " +
                "ORDER BY when_race ASC LIMIT 1";
        try {
            return jdbcTemplate.queryForObject(sql, raceRM());
        } catch (EmptyResultDataAccessException e) {
            return null;
        }

    }

    @Override
    public Race getLastRace() throws EntityNotFoundException {
        String sql = "SELECT * " +
                "FROM race " +
                "WHERE when_race = (Select MAX(race.when_race) from race WHERE when_race <= CURRENT_DATE)";
        try {
            return jdbcTemplate.queryForObject(sql, raceRM());
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public List<Race> getAllRaces(String season) {
        String sql = "SELECT * from race " +
                "WHERE season_year = ?" +
                " ORDER BY when_race";
        return jdbcTemplate.query(sql, raceRM(), season);
    }

    @Override
    public void saveRace(Race race) {
        if (race.getId() == 0) {
            String query = "INSERT INTO race (Season_year, place, name, when_race, when_quali, when_1_session" +
                    ", when_2_session, when_3_session, is_sprint_weekend) "
                    + "VALUES (?,?,?,?,?,?,?,?,?)";
            jdbcTemplate.update(new PreparedStatementCreator() {
                @Override
                public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                    PreparedStatement statement = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
                    statement.setInt(1, race.getYear());
                    statement.setString(2, race.getPlace());
                    statement.setString(3, race.getName());
                    statement.setTimestamp(4, Timestamp.valueOf(race.getWhenRace()));
                    statement.setTimestamp(5, Timestamp.valueOf(race.getWhenQuali()));
                    statement.setTimestamp(6, Timestamp.valueOf(race.getWhenFirstSession()));
                    statement.setTimestamp(7, Timestamp.valueOf(race.getWhenSecondSession()));
                    statement.setTimestamp(8, Timestamp.valueOf(race.getWhenThirdSession()));
                    if (race.isSprintWeekend()) {
                        statement.setInt(9, 1);
                    } else {
                        statement.setInt(9, 0);
                    }
                    return statement;
                }
            });
        } else {
            String query = "UPDATE race SET Season_year = ?, place = ?, name = ?, when_race = ?, when_quali = ?, " +
                    "when_1_session = ?, when_2_session = ?, when_3_session = ?, is_sprint_weekend = ?";
            int sprintWeekendValue;
            if (race.isSprintWeekend()) {
                sprintWeekendValue = 1;
            } else {
                sprintWeekendValue = 0;
            }
            jdbcTemplate.update(query, race.getYear(), race.getPlace(), race.getName(), race.getWhenRace(), race.getWhenQuali(),
                    race.getWhenFirstSession(), race.getWhenSecondSession(), race.getWhenThirdSession(), sprintWeekendValue);
        }
    }

    @Override
    public int getRaceId(Race race) {
        String sql = "SELECT idRace FROM race WHERE Season_year = ? " +
                "AND place = ? " +
                "AND name = ? " +
                "AND when_race = ?";

        return jdbcTemplate.queryForObject(sql, Integer.class, race.getYear(), race.getPlace(), race.getName(), Timestamp.valueOf(race.getWhenRace()));

    }

}


