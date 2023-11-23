package storage;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

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
                race.setWhenRace(rs.getDate("when_race"));
                race.setWhenQuali(rs.getDate("when_quali"));
                race.setWhenFirstSession(rs.getDate("when_1_session"));
                race.setWhenSecondSession(rs.getDate("when_2_session"));
                race.setWhenThirdSession(rs.getDate("when_3_session"));
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
}
