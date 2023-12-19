package f1.f1insider.storage;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;

import java.sql.*;
import java.util.List;

public class MysqlRaceResultsDao implements RaceResultsDao{
    private JdbcTemplate jdbcTemplate;
    private DriverDao driverDao = DaoFactory.INSTANCE.getDriverDao();
    public MysqlRaceResultsDao(JdbcTemplate jdbcTemplate) {this.jdbcTemplate = jdbcTemplate; }

    private RowMapper<RaceResults> raceResultsRM(){
        return new RowMapper<RaceResults>() {
            @Override
            public RaceResults mapRow(ResultSet rs, int rowNum) throws SQLException {
                RaceResults raceResults = new RaceResults();
                raceResults.setId(rs.getInt("Race_idRace"));
                raceResults.setDriver(driverDao.getById(rs.getInt("driver_idDriver")));
                raceResults.setPosition(rs.getInt("position"));
                raceResults.setFinished(rs.getBoolean("finished"));
                raceResults.setIntervalToWinner(rs.getDouble("interval_to_winner"));
                raceResults.setReason(rs.getString("reason"));
                return raceResults;
            }
        };
    }
    @Override
    public void saveRaceResults(RaceResults raceResults) {
        String query = "INSERT INTO race_results (Race_idRace, driver_idDriver, position, finished, interval_to_winner, reason)" +
                " VALUES (?,?,?,?,?,?)";
        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement statement = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
                statement.setInt(1, raceResults.getId());
                statement.setInt(2,raceResults.getDriver().getId());
                statement.setInt(3, raceResults.getPosition());
                if (raceResults.isFinished()){
                    statement.setInt(4, 1);
                }else {
                    statement.setInt(4, 0);
                }
                statement.setDouble(5, raceResults.getIntervalToWinner());
                statement.setString(6, raceResults.getReason());
                return statement;
            }
        });
    }

    @Override
    public List<RaceResults> getRaceResults(int idRace) {
            String sql ="SELECT rr.* FROM race_results rr " +
                    "JOIN race r ON rr.Race_idRace = r.idRace " +
                    "WHERE r.idRace = ? " +
                    "ORDER BY rr.position";
            return jdbcTemplate.query(sql, raceResultsRM(), idRace);
    }

    @Override
    public List<RaceResults> getPodiumOfRace(int idRace) {
        String sql ="SELECT rr.* FROM race_results rr " +
                "JOIN race r ON rr.Race_idRace = r.idRace " +
                "WHERE r.idRace = ? " +
                "ORDER BY rr.position asc " +
                "LIMIT 3";
        return jdbcTemplate.query(sql, raceResultsRM(), idRace);
    }

    @Override
    public int getWinsCount(int idDriver) {
        String sql = "SELECT COUNT(*) FROM race_results " +
                "WHERE driver_idDriver = ? AND position = ?";
        int result = 0;

        try {
            result = jdbcTemplate.queryForObject(sql, Integer.class,  idDriver, 1);
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }
        return result;    }

    @Override
    public int getSecondCount(int idDriver) {
        String sql = "SELECT COUNT(*) FROM race_results " +
                "WHERE driver_idDriver = ? AND position = ?";
        int result = 0;

        try {
            result = jdbcTemplate.queryForObject(sql, Integer.class,  idDriver, 2);
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }
        return result;    }

    @Override
    public int getThirdCount(int idDriver) {
        String sql = "SELECT COUNT(*) FROM race_results " +
                "WHERE driver_idDriver = ? AND position = ?";
        int result = 0;

        try {
            result = jdbcTemplate.queryForObject(sql, Integer.class, idDriver, 3);
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }
        return result;    }

    @Override
    public int getTotalRaces(int idDriver) {
        String sql = "SELECT COUNT(*) FROM race_results " +
                "WHERE driver_idDriver = ?";

        int result = 0;

        try {
            result = jdbcTemplate.queryForObject(sql, Integer.class,  idDriver);
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    @Override
    public int getHighestFinish(int idDriver) {
        String sql = "SELECT position FROM race_results " +
                "WHERE driver_idDriver = ? " +
                "ORDER BY position ASC";
        return jdbcTemplate.queryForObject(sql, Integer.class, idDriver);
    }


}
