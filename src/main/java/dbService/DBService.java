package dbService;

import dbService.dao.BooksDAO;
import dbService.dao.UsersDAO;
import dbService.dataSets.BookDataSet;
import dbService.dataSets.UserDataSet;
import helpers.PropertyReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

import static helpers.Constants.ERROR_MESSAGE_GENERAL;
import static helpers.Constants.SETTINGS_FILE;

/**
 * Created by bbb1991 on 11/20/16.
 *
 * @author Bagdat Bimaganbetov
 * @author bagdat.bimaganbetov@gmail.com
 */
public class DBService {
    private static final Logger logger = LoggerFactory.getLogger(DBService.class);
    private volatile static DBService instance;
    private static final Object obj = new Object();
    private static UsersDAO usersDAO;
    private static BooksDAO booksDAO;
    private ConnectionPool connectionPool;


    private DBService() throws CustomException {
        try {
            initConnectionPool();
            usersDAO = new UsersDAO(this);
            booksDAO = new BooksDAO(this);

            warmUp();
        } catch (CustomException e) {
            logger.error(ERROR_MESSAGE_GENERAL, e);
            throw e;
        }
    }

    public Connection retrieveConnection() throws CustomException {
        return connectionPool.retrieve();
    }

    public void putBackConnection(Connection connection) {
        connectionPool.putBack(connection);
    }

    private void warmUp() throws CustomException {
        usersDAO.dropTable();
        usersDAO.createTable();

        booksDAO.dropTable();
        booksDAO.createTable();

        UserDataSet admin = new UserDataSet("admin", "admin");
        admin.setAdmin(true);
        usersDAO.insert(admin);

        UserDataSet guest = new UserDataSet("guest", "guest");
        usersDAO.insert(guest);

        booksDAO.insert(new BookDataSet("title1", "admin", "Content1"));
        booksDAO.insert(new BookDataSet("title2", "admin", "Content2"));
        booksDAO.insert(new BookDataSet("title3", "admin", "Content3"));
    }

    private void initConnectionPool() throws CustomException {
        Properties properties = PropertyReader.readProperty(SETTINGS_FILE);

        String url = properties.getProperty("db_url");
        String user = properties.getProperty("db_username");
        String password = properties.getProperty("db_password");
        int capacity = Integer.parseInt(properties.getProperty("db_conn_pool_size"));

        connectionPool = new ConnectionPool(url, user, password, capacity);
    }

    public UserDataSet getUser(final String username) throws CustomException {
        return usersDAO.getByName(username);
    }

    public static DBService getInstance() throws CustomException {
        if (instance == null) {
            synchronized (obj) {
                if (instance == null) {
                    instance = new DBService();
                }
            }
        }

        return instance;
    }

    public String insertBook(long userId, String title, String content, String username) throws CustomException {

        BookDataSet bookDataSet = new BookDataSet(title, username, content);

        bookDataSet.setAuthor(username);

        booksDAO.insert(bookDataSet);

        return String.valueOf(1);
    }

    public UserDataSet insertUser(String username, String password) throws CustomException {
        UserDataSet userDataSet = new UserDataSet(username, password);
        usersDAO.insert(userDataSet);
        return userDataSet;
    }

    public BookDataSet findBookById(String id) throws CustomException {

        return booksDAO.findBookById(Long.parseLong(id));
    }

    public List<BookDataSet> getAllBooks() throws CustomException {
        return booksDAO.getAll();
    }
}