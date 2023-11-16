package storage;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class MysqlUserDao implements UserDao {
    private JdbcTemplate jdbcTemplate;

    public MysqlUserDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public User getById(long id) throws EntityNotFoundException {
        return null;
    }

    @Override
    public User getByUsername(String username) throws EntityNotFoundException {
        return null;
    }

    @Override
    public Boolean add(User user) {
        try {
            String query = "SELECT COUNT(*) FROM User WHERE username = ?";
            int count = jdbcTemplate.queryForObject(query, Integer.class, user.getUsername());
            if (count == 0) {
                String finalQuery = "INSERT INTO User (username, password, admin) VALUES (?, ?, 0)";

                PreparedStatementCreator preparedStatementCreator = new PreparedStatementCreator() {
                    @Override
                    public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                        PreparedStatement preparedStatement = connection.prepareStatement(finalQuery, Statement.RETURN_GENERATED_KEYS);
                        preparedStatement.setString(1, user.getUsername());
                        preparedStatement.setString(2, user.getPass());
                        return preparedStatement;
                    }
                };

                GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
                jdbcTemplate.update(preparedStatementCreator, keyHolder);

                // Set the generated id on the User object
                user.setId(keyHolder.getKey().intValue());

                return true;
            } else {
                return false;
            }
        } catch (EmptyResultDataAccessException e) {
            return false;
        }
    }

    @Override
    public void delete(long id) throws EntityNotFoundException {

    }




}
