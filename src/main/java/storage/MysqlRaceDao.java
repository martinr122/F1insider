package storage;

import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Date;

public class MysqlRaceDao implements RaceDao{

    private JdbcTemplate jdbcTemplate;

    public MysqlRaceDao(JdbcTemplate jdbcTemplate){this.jdbcTemplate = jdbcTemplate;}

    public String getNextRace(Date date) throws EntityNotFoundException {
        String sql = "SELECT place FROM race WHERE when_race >= ? " +
                "ORDER BY when_race ASC LIMIT 1";
        return jdbcTemplate.queryForObject(sql, new Object[]{date}, String.class);
    }

    @Override
    public String getNextRaceDate(Date date) throws EntityNotFoundException {
        String sql = "SELECT when_race FROM race WHERE when_race >= ? " +
                "ORDER BY when_race ASC LIMIT 1";
        return jdbcTemplate.queryForObject(sql, new Object[]{date}, String.class);
    }

    @Override
    public String getLastRace(Date date) throws EntityNotFoundException {
        String sql = "SELECT place " +
                "FROM race " +
                "WHERE when_race = (Select MAX(race.when_race) from race " +
                "JOIN race_results ON race.idRace = race_results.Race_idRace)";
        return jdbcTemplate.queryForObject(sql, String.class);
    }
}
