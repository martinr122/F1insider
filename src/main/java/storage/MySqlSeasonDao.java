package storage;

import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.JdbcTemplate;
import java.sql.*;

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
}
