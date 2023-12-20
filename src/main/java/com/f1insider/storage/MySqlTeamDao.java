package com.f1insider.storage;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;

import java.sql.*;
import java.time.Year;
import java.util.List;

public class MySqlTeamDao implements TeamDao {
    private JdbcTemplate jdbcTemplate;

    public MySqlTeamDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private RowMapper<Team> teamRM() {
        return new RowMapper<Team>() {

            @Override
            public Team mapRow(ResultSet rs, int rowNum) throws SQLException {
                Team team = new Team();
                team.setIdTeam(rs.getInt("idTeam"));
                team.setTeamName(rs.getString("team_name"));
                team.setNameEngine(rs.getString("name_engine"));
                team.setNamePrincipal(rs.getString("name_principal"));
                team.setNameFounder(rs.getString("name_founder"));
                team.setCountry(rs.getString("country"));
                team.setNameMonopost(rs.getString("name_monopost"));
                team.setYear(rs.getInt("Season_year"));
                team.setTeamColor(rs.getString("team_color"));
                return team;
            }
        };
    }

    @Override
    public void add(Team team) {
        if (team.getIdTeam() == 0) {
            String query = "INSERT INTO team (team_name, name_engine, name_principal, name_founder, country, name_monopost" +
                    ", Season_year, team_color) "
                    + "VALUES (?,?,?,?,?,?,?,?)";
            jdbcTemplate.update(new PreparedStatementCreator() {
                @Override
                public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                    PreparedStatement statement = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
                    statement.setString(1, team.getTeamName());
                    statement.setString(2, team.getNameEngine());
                    statement.setString(3, team.getNamePrincipal());
                    statement.setString(4, team.getNameFounder());
                    statement.setString(5, team.getCountry());
                    statement.setString(6, team.getNameMonopost());
                    statement.setInt(7, team.getYear());
                    statement.setString(8, team.getTeamColor());
                    return statement;
                }
            });
        } else {
            String query = "UPDATE team SET team_name = ?, name_engine = ?, name_principal = ?, name_founder = ?, " +
                    "country = ?, name_monopost = ?, Season_year = ?, team_color = ? "
                    + "WHERE idTeam = ?";
            jdbcTemplate.update(query, team.getTeamName(), team.getNameEngine(), team.getNamePrincipal(), team.getNameFounder(),
                    team.getCountry(), team.getNameMonopost(), team.getYear(), team.getTeamColor(), team.getIdTeam());
        }
    }

    @Override
    public List<Team> getTeamsByYear(int year) {
        String sql = "SELECT * from team " +
                "WHERE Season_year = ?" +
                " ORDER BY team_name desc";
        return jdbcTemplate.query(sql, teamRM(), year);
    }

    @Override
    public Team getById(int id) {
        String sql = "SELECT * FROM team " +
                "WHERE idTeam = ?";
        return jdbcTemplate.queryForObject(sql, teamRM(), id);
    }

    @Override
    public List<Team> getTeamsbyDriver(int idDriver) {
        String sql = "SELECT DISTINCT t.* FROM team t " +
                "JOIN driver_has_team dht ON t.idTeam = dht.Team_idTeam " +
                "WHERE dht.Driver_idDriver = ?";
        return jdbcTemplate.query(sql, teamRM(), idDriver);
    }

    @Override
    public Team getTeamByName(String name, int year) {
        String sql = "SELECT * FROM team WHERE team_name = ? AND Season_year = ?";
        return jdbcTemplate.queryForObject(sql, teamRM(), name, year);
    }

    @Override
    public void deleteByName(String name, int year) {
        String sqlDht = "DELETE dht FROM driver_has_team dht " +
                "JOIN team t ON dht.Team_idTeam = t.idTeam " +
                "WHERE t.team_name = ? AND t.Season_year = ?";
        jdbcTemplate.update(sqlDht, name, year);
        String sqlTeam = "DELETE FROM team WHERE team_name = ? AND Season_year = ? limit 1";
        jdbcTemplate.update(sqlTeam, name, year);
    }

    @Override
    public int getID(String name, int year) {
        String sql = "SELECT idTeam FROM team WHERE team_name = ? AND Season_year = ?";
        return jdbcTemplate.queryForObject(sql, Integer.class, name, year);
    }

    @Override
    public void addDriversToTeam(int teamId, int driverId) {
        if (getContract(teamId, driverId) == 0) {
            String sql = "INSERT INTO driver_has_team (Driver_idDriver, Team_idTeam, active_contract) "
                    + "VALUES (?,?,?)";
            jdbcTemplate.update(sql, driverId, teamId, 1);
        } else {
            String sql = "UPDATE driver_has_team SET active_contract = 1 " +
                    "WHERE Team_idTeam = ? AND Driver_idDriver = ?";
            jdbcTemplate.update(sql, teamId, driverId);
        }
    }

    @Override
    public void deleteDriverFromTeam(int teamId, int driverId) {
        String sql = "DELETE FROM driver_has_team WHERE Team_idTeam = ? AND Driver_idDriver = ?";
        jdbcTemplate.update(sql, teamId, driverId);
    }

    @Override
    public void changeContract(int teamId, int driverId) {
        String sql = "UPDATE driver_has_team SET active_contract = 0 " +
                "WHERE Team_idTeam = ? AND Driver_idDriver = ?";
        jdbcTemplate.update(sql, teamId, driverId);
    }

    @Override
    public int getContract(int teamId, int driverId) {
        String sql = "SELECT COUNT(*) FROM driver_has_team " +
                "WHERE Team_idTeam = ? AND Driver_idDriver = ?";
        return jdbcTemplate.queryForObject(sql, Integer.class, teamId, driverId);
    }

    @Override
    public Team getTeamByDriver(int idDriver, int year) {
        String sql = "SELECT DISTINCT t.* FROM team t " +
                "JOIN driver_has_team dht ON t.idTeam = dht.Team_idTeam " +
                "WHERE dht.Driver_idDriver = ? " +
                "AND t.Season_year = ?";
        return jdbcTemplate.queryForObject(sql, teamRM(), idDriver, year);
    }

}