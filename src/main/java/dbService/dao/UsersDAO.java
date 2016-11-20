package dbService.dao;

import dbService.dataSets.UserDataSet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;

import static helpers.Constants.ERROR_MESSAGE;

/**
 * Created by bbb1991 on 11/20/16.
 *
 * @author Bagdat Bimaganbetov
 * @author bagdat.bimaganbetov@gmail.com
 */
public class UsersDAO {
    private Connection connection;
    private static final Logger logger = LoggerFactory.getLogger(UsersDAO.class);

    public UsersDAO(Connection connection) {
        this.connection = connection;
    }

    public UserDataSet getUserById(long id) {
        UserDataSet userDataSet = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            preparedStatement = connection.prepareStatement("SELECT username, password FROM users WHERE id=?");
            preparedStatement.setLong(1, id);

            resultSet = preparedStatement.executeQuery();
            resultSet.next();
            String user = resultSet.getString("username");
            String password = resultSet.getString("password");

            userDataSet = new UserDataSet(id, user, password);

        } catch (SQLException e) {
            logger.error(ERROR_MESSAGE, e);
        } finally {

            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    logger.error(ERROR_MESSAGE, e);
                }
            }

            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    logger.error(ERROR_MESSAGE, e);
                }
            }
        }
        return userDataSet;
    }


    public void createTable() throws SQLException {
        logger.info("Creating tables in db.");
        try (Statement statement = connection.createStatement()) {
            statement.execute("CREATE SEQUENCE if NOT EXISTS id_seq");

            statement.execute("CREATE TABLE if NOT EXISTS users (" +
                    "id INTEGER NOT NULL DEFAULT nextval('id_seq'), " +
                    "username VARCHAR(20) NOT NULL, " +
                    "password VARCHAR(50) NOT NULL)");

            statement.execute("ALTER SEQUENCE id_seq owned by users.id");
            logger.info("Table in db created!");
        }
    }

    public void dropTable() throws SQLException {
        logger.info("Trying to drop db");
        try (Statement statement = connection.createStatement()) {
            statement.execute("DROP TABLE IF EXISTS users");
            statement.execute("drop SEQUENCE if EXISTS id_seq");
            logger.info("Tables dropped!");
        }
    }

    public long insertUser(UserDataSet userDataSet) {
        logger.info("Inserting new user: {}", userDataSet);
        PreparedStatement preparedStatement = null;

        try {
            preparedStatement = connection.prepareStatement("INSERT INTO users (username, password) VALUES (?, ?)");

            preparedStatement.setString(1, userDataSet.getUsername());
            preparedStatement.setString(2, userDataSet.getPassword());

            preparedStatement.execute();

        } catch (SQLException e) {
            logger.error(ERROR_MESSAGE, e);
        } finally {

            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    logger.error(ERROR_MESSAGE, e);
                }
            }
        }

//        return getUserIdByUsername(userDataSet.getUsername());
        return -1;
    }

    public long getUserIdByUsername(final String username) {
        logger.info("Getting user ID  by username: {}", username);
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        long id = -1L;

        try {
            statement = connection.prepareStatement("SELECT id FROM users WHERE username=?");
            statement.setString(1, username);
            resultSet = statement.executeQuery();

            resultSet.next();
            id = resultSet.getLong("id");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    logger.error(ERROR_MESSAGE, e);
                }
            }

            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    logger.error(ERROR_MESSAGE, e);
                }
            }
        }

        return id;
    }

    public UserDataSet getUserByUsername(String username) {
        logger.info("Getting info about user by username: {}", username);
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        UserDataSet userDataSet = null;
        try {
            preparedStatement = connection.prepareStatement("SELECT username, password FROM users WHERE username=?");
            preparedStatement.setString(1, username);

            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {

                String u = resultSet.getString("username");
                String p = resultSet.getString("password");

                userDataSet = new UserDataSet(u, p);
            }
        } catch (SQLException e) {
            logger.error(ERROR_MESSAGE, e);
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    logger.error(ERROR_MESSAGE, e);
                }
            }

            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    logger.error(ERROR_MESSAGE, e);
                }
            }
        }

        return userDataSet;
    }
}
