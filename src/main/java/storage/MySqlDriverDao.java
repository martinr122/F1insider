package storage;

import com.mysql.cj.jdbc.Blob;
import org.springframework.jdbc.core.JdbcTemplate;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MySqlDriverDao implements  DriverDao{

    private JdbcTemplate jdbcTemplate;

    public MySqlDriverDao(JdbcTemplate jdbcTemplate){this.jdbcTemplate = jdbcTemplate;}


    @Override
    public InputStream getImage(String firstName, String surname) {
        return null;
    }
}
