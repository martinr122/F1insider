package storage;

import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.JdbcTemplate;
import java.sql.*;
import java.util.List;

public class MySqlSeasonDao implements SeasonDao{
    private JdbcTemplate jdbcTemplate;
    public MySqlSeasonDao(JdbcTemplate jdbcTemplate){this.jdbcTemplate = jdbcTemplate;}
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
        if(count > 0){
            return true;
        }else {
            return false;
        }
    }

    @Override
    public List<String> getAllSeason() {
        String sql = "SELECT DISTINCT year FROM season ORDER BY year DESC";
        return jdbcTemplate.queryForList(sql, String.class);
    }
}
