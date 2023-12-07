package storage;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;

import java.io.InputStream;
import java.sql.*;
import java.util.List;

public class MySqlDriverDao implements  DriverDao{

    private JdbcTemplate jdbcTemplate;

    public MySqlDriverDao(JdbcTemplate jdbcTemplate){this.jdbcTemplate = jdbcTemplate;}

    private RowMapper<Driver> driverRM(){
        return new RowMapper<Driver>() {

            @Override
            public Driver mapRow(ResultSet rs, int rowNum) throws SQLException {
                Driver driver = new Driver();
                driver.setId(rs.getInt("idDriver"));
                driver.setFirstName(rs.getString("first_name_driver"));
                driver.setSurname(rs.getString("surname_driver"));
                driver.setCountry(rs.getString("country"));
                driver.setBirthday(rs.getDate("birthday").toLocalDate());
                driver.setRaceNumber(rs.getInt("race_number"));
                return driver;
            }
        };
    }
    @Override
    public InputStream getImage(String firstName, String surname) {
        return null;
    }

    @Override
    public List<Driver> getAllDrivers() {
        String sql = "SELECT d.* FROM driver d " +
                "JOIN driver_has_team dht ON d.idDriver = dht.Driver_idDriver " +
                "JOIN team t ON dht.Team_idTeam = t.idTeam " +
                "GROUP BY d.idDriver " +
                "ORDER BY MAX(t.Season_year) DESC, surname_driver ASC";
        return jdbcTemplate.query(sql, driverRM());
    }

    @Override
    public List<Driver> getDriversbyTeam(int idTeam) {
        String sql = "SELECT d.* FROM driver d " +
                "JOIN driver_has_team dht ON d.idDriver = dht.Driver_idDriver " +
                "WHERE dht.Team_idTeam = ?";

        return jdbcTemplate.query(sql, driverRM(), idTeam);
    }

    @Override
    public void add(Driver driver) {
        String query = "INSERT INTO driver (first_name_driver, surname_driver, country, birthday" +
                ", race_number) "
                + "VALUES (?,?,?,?,?)";
        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement statement = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
                statement.setString(1, driver.getFirstName());
                statement.setString(2, driver.getSurname());
                statement.setString(3, driver.getCountry());
                statement.setDate(4, Date.valueOf(String.valueOf(driver.getBirthday())));
                statement.setInt(5, driver.getRaceNumber());
                return statement;
            }
        });
    }

    @Override
    public Driver getByName(String firstName, String surname) {
        String sql = "select * from driver where first_name_driver = ? and surname_driver = ?";
        return jdbcTemplate.queryForObject(sql,driverRM(),firstName,surname);
    }

    @Override
    public List<Driver> getByNameLike(String nameLike) {
        String sql = "SELECT * FROM driver WHERE first_name_driver LIKE ? OR surname_driver LIKE ?";
        String editedNameLike = "%" + nameLike + "%";
        return jdbcTemplate.query(sql, driverRM(), editedNameLike, editedNameLike);
    }

    @Override
    public boolean contains(Driver driver) {
        String sql = "SELECT COUNT(*) FROM driver WHERE first_name_driver = ? and "+
                "surname_driver = ? and birthday = ?";
        int count = jdbcTemplate.queryForObject(sql, Integer.class, driver.getFirstName(),driver.getSurname(),driver.getBirthday());
        if (count == 0) {
            return false;
        }else {
            return true;
        }

    }

    @Override
    public int getID(String firstName, String surname) {
        String sql = "select idDriver from driver where first_name_driver = ? and surname_driver = ?";
        return jdbcTemplate.queryForObject(sql,Integer.class,firstName,surname);
    }
}
