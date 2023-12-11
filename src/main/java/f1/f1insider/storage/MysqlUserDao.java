package f1.f1insider.storage;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;

import java.sql.*;
import java.util.Objects;

public class MysqlUserDao implements UserDao {
    private JdbcTemplate jdbcTemplate;

    public MysqlUserDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private RowMapper<User> userRM() {
        return new RowMapper<User>() {

            @Override
            public User mapRow(ResultSet rs, int rowNum) throws SQLException {
                User user = new User();
                user.setId(rs.getInt("idUser"));
                user.setUsername(rs.getString("username"));
                user.setAdmin(rs.getBoolean("admin"));
                return user;
            }
        };
    }

    @Override
    public User getById(long id) throws EntityNotFoundException {
        String sql = "SELECT * FROM User WHERE idUser = ?";
        return jdbcTemplate.queryForObject(sql, userRM(), id);
    }

    @Override
    public int getIdbyUsername(String username) {
        String sql = "SELECT idUser FROM User WHERE username = ?";
        return jdbcTemplate.queryForObject(sql, Integer.class, username);
    }

    @Override
    public String givePassword(String username) throws EntityNotFoundException {
        Objects.requireNonNull(username, "username cannot be null");
        String query = "SELECT password FROM User WHERE username = ?";
        try {
            String password = jdbcTemplate.queryForObject(query, String.class, username);
            return password;
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
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
                        String password = PasswordHashing.doHashing(user.getPass());
                        preparedStatement.setString(2, password);
                        return preparedStatement;
                    }
                };

                GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
                jdbcTemplate.update(preparedStatementCreator, keyHolder);
                user.setId(keyHolder.getKey().intValue());
                return true;
            } else {
                System.out.println("Username is taken");
                return false;
            }
        } catch (EmptyResultDataAccessException e) {
            return false;
        }
    }

    @Override
    public boolean isAdmin(String username) {
        Objects.requireNonNull(username, "username cannot be null");
        String query = "SELECT admin FROM User WHERE username = ?";
        return jdbcTemplate.queryForObject(query, Integer.class, username) == 1;
    }


}
