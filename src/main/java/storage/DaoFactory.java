package storage;


import com.mysql.cj.jdbc.MysqlDataSource;
import org.springframework.jdbc.core.JdbcTemplate;

public enum DaoFactory {
    INSTANCE;
    private UserDao userDao;
    private RaceDao raceDao;
    private SeasonDao seasonDao;
    private TeamDao teamDao;
    private JdbcTemplate jdbcTemplate;

    private JdbcTemplate getJdbcTemplate() {
        if (jdbcTemplate == null) {
            MysqlDataSource dataSource = new MysqlDataSource();
            dataSource.setUser("admin");
            dataSource.setPassword("F1insider123");
            dataSource.setUrl("jdbc:mysql://localhost:3306/f1insider");
            jdbcTemplate = new JdbcTemplate(dataSource);
        }
        return jdbcTemplate;
    }
    public UserDao getUserDao() {
        if (userDao == null)
            userDao = new MysqlUserDao(getJdbcTemplate());
        return userDao;
    }
    public TeamDao getTeamDao() {
        if (teamDao == null)
            teamDao = new MySqlTeamDao(getJdbcTemplate());
        return teamDao;
    }

    public RaceDao getRaceDao(){
        if (raceDao == null)
            raceDao = new MysqlRaceDao(getJdbcTemplate());
        return raceDao;
    }
    public SeasonDao getSeasonDao(){
        if (seasonDao == null)
            seasonDao = new MySqlSeasonDao(getJdbcTemplate());
        return seasonDao;
    }

}
