package dbService;

import dbService.dao.BooksDAO;
import dbService.dao.UsersDAO;
import dbService.dataSets.BookDataSet;
import dbService.dataSets.UserDataSet;
import helpers.PropertyReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import static helpers.Constants.ERROR_MESSAGE;
import static helpers.Constants.SETTINGS_FILE;

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
    private static BooksDAO booksDAO;

    private DBService() {
        this.connection = getConnection();
        warmUp();
    }

    private void warmUp() {
        usersDAO = new UsersDAO(connection);
        booksDAO = new BooksDAO(connection);


        try {
            usersDAO.dropTable();
            usersDAO.createTable();

            booksDAO.dropTable();
            booksDAO.createTable();
        } catch (SQLException e) {
            logger.error(ERROR_MESSAGE, e);
        }

        UserDataSet admin = new UserDataSet("admin", "admin");
        admin.setAdmin(true);
        insertUser(admin);

        UserDataSet guest = new UserDataSet("guest", "guest");
        insertUser(guest);
    }

    private Connection getConnection() {
        Connection connection = null;
        try {
            Properties properties = PropertyReader.readProperty(SETTINGS_FILE);

            String url = properties.getProperty("db_url");
            String user = properties.getProperty("db_username");
            String password = properties.getProperty("db_password");

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

    @Deprecated
    public long insertUser(UserDataSet userDataSet) {
        long id  = usersDAO.insertUser(userDataSet);
        logger.info("User inserted. ID is: {}", id);
        return id;
    }

    public String insertBook(long userId, String title, String content, String username) {

        BookDataSet bookDataSet = new BookDataSet(title, userId, content);

        return booksDAO.addBook(bookDataSet, username);
    }

    public UserDataSet insertUser(String username, String password) {
        UserDataSet userDataSet = new UserDataSet(username, password);
        usersDAO.insertUser(userDataSet);
        return userDataSet;
    }

    public BookDataSet findBookById(String id) {
        return booksDAO.findBookById(id);
    }
}