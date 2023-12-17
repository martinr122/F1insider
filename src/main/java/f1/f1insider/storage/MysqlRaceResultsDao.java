package f1.f1insider.storage;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;

import java.sql.*;
import java.util.List;

public class MysqlRaceResultsDao implements RaceResultsDao{
    private JdbcTemplate jdbcTemplate;
    public MysqlRaceResultsDao(JdbcTemplate jdbcTemplate) {this.jdbcTemplate = jdbcTemplate; }

    private RowMapper<RaceResults> raceResultsRM(){
        return new RowMapper<RaceResults>() {
            @Override
            public RaceResults mapRow(ResultSet rs, int rowNum) throws SQLException {
                RaceResults raceResults = new RaceResults();
                raceResults.setId(rs.getInt("Race_idRace"));
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
        String query = "INSERT INTO race_results (Race_idRace, position, finished, interval_to_winner, reason)" +
                " VALUES (?,?,?,?,?)";
        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement statement = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
                statement.setInt(1, raceResults.getId());
                statement.setInt(2, raceResults.getPosition());
                if (raceResults.isFinished()){
                    statement.setInt(3, 1);
                }else {
                    statement.setInt(3, 0);
                }
                statement.setDouble(4, raceResults.getIntervalToWinner());
                statement.setString(5, raceResults.getReason());
                return statement;
            }
        });
    }

    @Override
    public void addDriversToRaceResults(int driverId, int raceId, int position) {
        String sql = "INSERT INTO driver_has_race_results " +
                "(Driver_idDriver, Race_results_Race_idRace, race_results_position) " +
                " VALUES(?,?,?)";
        jdbcTemplate.update(sql, driverId, raceId, position);
    }

    @Override
    public List<RaceResults> getRaceResults(int idRace) {
        String sql ="SELECT rr.* FROM race_results rr " +
                "JOIN race r ON rr.Race_idRace = r.idRace " +
                "WHERE r.idRace = ? " +
                "ORDER BY dhrr.race_results_position";
        return jdbcTemplate.query(sql, raceResultsRM(), idRace);
    }
}
