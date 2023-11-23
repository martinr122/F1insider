package storage;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;

import java.sql.*;
import java.time.LocalDate;
import java.util.List;

public class MysqlRaceDao implements RaceDao{

    private JdbcTemplate jdbcTemplate;

    public MysqlRaceDao(JdbcTemplate jdbcTemplate){this.jdbcTemplate = jdbcTemplate;}

    private RowMapper<Race> raceRM(){
        return new RowMapper<Race>() {

            @Override
            public Race mapRow(ResultSet rs, int rowNum) throws SQLException {
                Race race = new Race();
                race.setId(rs.getInt("idRace"));
                race.setYear(rs.getInt("Season_year"));
                race.setWhenRace(rs.getDate("when_race").toLocalDate());
                race.setWhenQuali(rs.getDate("when_quali").toLocalDate());
                race.setWhenFirstSession(rs.getDate("when_1_session").toLocalDate());
                race.setWhenSecondSession(rs.getDate("when_2_session").toLocalDate());
                race.setWhenThirdSession(rs.getDate("when_3_session").toLocalDate());
                if (rs.getInt("is_sprint_weekend") == 1){
                    race.setSprintWeekend(true);
                }else {
                    race.setSprintWeekend(false);
                }
                race.setPlace(rs.getString("place"));
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
                "WHERE when_race = (Select MAX(race.when_race) from race " +
                "JOIN race_results ON race.idRace = race_results.Race_idRace)";

        try {
            return jdbcTemplate.queryForObject(sql, raceRM());
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public List<Race> getAllRaces() {
        String sql = "SELECT * from race"
                + " ORDER BY when_race";
        return jdbcTemplate.query(sql, raceRM());
    }
    @Override
    public void saveRace(Race race){
                String query = "INSERT INTO race (Season_year, when_race, when_quali, when_1_session" +
                        ", when_2_session, when_3_session, is_sprint_weekend, place) "
                        + "VALUES (?,?,?,?,?,?,?,?)";
                jdbcTemplate.update(new PreparedStatementCreator() {
                    @Override
                    public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                        PreparedStatement statement = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
                        statement.setInt(1, race.getYear());
                        statement.setDate(2, Date.valueOf(race.getWhenRace()));
                        statement.setDate(3, Date.valueOf(race.getWhenQuali()));
                        statement.setDate(4, Date.valueOf(race.getWhenFirstSession()));
                        statement.setDate(5, Date.valueOf(race.getWhenSecondSession()));
                        statement.setDate(6, Date.valueOf(race.getWhenThirdSession()));
                        if (race.isSprintWeekend()){
                            statement.setInt(7, 1);
                        }else {
                            statement.setInt(7, 0);
                        }
                        statement.setString(8, race.getPlace());
                        return statement;
                    }
                });
            }
        }


