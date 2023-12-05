package storage;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;

import java.sql.*;
import java.util.List;

public class MySqlTeamDao implements TeamDao{
    private JdbcTemplate jdbcTemplate;
    public MySqlTeamDao(JdbcTemplate jdbcTemplate){this.jdbcTemplate = jdbcTemplate;}

    private RowMapper<Team> teamRM(){
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
                return team;
            }
        };
    }

    @Override
    public void add(Team team) {
        String query = "INSERT INTO team (team_name, name_engine, name_principal, name_founder, country, name_monopost" +
                ", Season_year) "
                + "VALUES (?,?,?,?,?,?,?)";
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
                return statement;
            }
        });
    }

    @Override
    public List<Team> getTeamsByYear(int year) {

            String sql = "SELECT * from team " +
                    "WHERE Season_year = ?" +
                    " ORDER BY team_name desc";
            return jdbcTemplate.query(sql, teamRM(), year);

    }

    @Override
    public Team getTeamByName(String name, int year) {
        String sql = "select * from team where team_name = ? and Season_year = ?";
        return jdbcTemplate.queryForObject(sql,teamRM(),name,year);
    }

    @Override
    public void deleteByName(String name, int year) {
        String sql = "delete from team where team_name = ? and Season_year = ? limit 1";
        jdbcTemplate.update(sql, name, year);
    }
}