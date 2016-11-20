package dbService;

import dbService.dao.UsersDAO;
import dbService.dataSets.UserDataSet;
import helpers.PropertyReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import static helpers.Constants.ERROR_MESSAGE;

/**
 * Created by bbb1991 on 11/20/16.
 *
 * @author Bagdat Bimaganbetov
 * @author bagdat.bimaganbetov@gmail.com
 */
public class DBService {
    private final Connection connection;
    private static final Logger logger = LoggerFactory.getLogger(DBService.class);
    private volatile static DBService instance;
    private static final Object obj = new Object();
    private static UsersDAO usersDAO;

    private DBService() {
        this.connection = getConnection();
//        DataSource
        warmUp();

    }

    private void warmUp() {
        usersDAO = new UsersDAO(connection);

        try {
            usersDAO.dropTable();
            usersDAO.createTable();
        } catch (SQLException e) {
            logger.error(ERROR_MESSAGE, e);
        }

        UserDataSet userDataSet = new UserDataSet("admin", "admin");
        usersDAO.insertUser(userDataSet);
    }

    private Connection getConnection() {
        Connection connection = null;
        try {
//            DriverManager.registerDriver((Driver) Class.forName("com.mysql.jdbc.Driver").newInstance());

            Properties properties = PropertyReader.readProperty("settings.properties");

            String url = properties.getProperty("db_url");
            String user = properties.getProperty("db_username");
            String password = properties.getProperty("db_password");

//            DataSource
//            connection = ds.getConnection();

            connection =  DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            logger.error(ERROR_MESSAGE, e);
        }

        return connection;
    }

    public UserDataSet getUser(final String username) {
        return usersDAO.getUserByUsername(username);
    }

    public static DBService getInstance() {
        if (instance == null) {
            synchronized (obj) {
                if (instance == null) {
                    instance = new DBService();
                }
            }
        }

        return instance;
    }

    public void insertUser(UserDataSet userDataSet) {
        usersDAO.insertUser(userDataSet);
    }
}