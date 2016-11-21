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

    public UsersDAO(final Connection connection) {
        this.connection = connection;
    }

    public UserDataSet getUserById(final long id) {
        UserDataSet userDataSet = null;

        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "SELECT username, password FROM users WHERE id=?")) {

            preparedStatement.setLong(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                String user = resultSet.getString("username");
                String password = resultSet.getString("password");

                userDataSet = new UserDataSet(id, user, password);
            }

        } catch (SQLException e) {
            logger.error(ERROR_MESSAGE, e);
        }
        return userDataSet;
    }

    public void createTable() throws SQLException {
        logger.info("Creating tables in db.");
        try (Statement statement = connection.createStatement()) {
            statement.execute("CREATE SEQUENCE IF NOT EXISTS id_seq");

            statement.execute("CREATE TABLE IF NOT EXISTS users (" +
                    "id INTEGER NOT NULL DEFAULT nextval('id_seq'), " +
                    "username VARCHAR(20) NOT NULL UNIQUE , " +
                    "password VARCHAR(50) NOT NULL," +
                    "is_blocked BOOLEAN DEFAULT FALSE," +
                    "is_admin BOOLEAN DEFAULT FALSE " +
                    ")");

            statement.execute("ALTER SEQUENCE id_seq OWNED BY users.id");
            logger.info("Table in db created!");
        }
    }

    public void dropTable() throws SQLException {
        logger.info("Trying to drop db");
        try (Statement statement = connection.createStatement()) {
            statement.execute("DROP TABLE IF EXISTS users");
            statement.execute("DROP SEQUENCE IF EXISTS id_seq");
            logger.info("Tables dropped!");
        }
    }

    // todo usernames may duplicates.
    public long insertUser(final UserDataSet userDataSet) {
        logger.info("Inserting new user: {}", userDataSet);

        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "INSERT INTO users (username, password) VALUES (?, ?)")) {

            preparedStatement.setString(1, userDataSet.getUsername());
            preparedStatement.setString(2, userDataSet.getPassword());

            preparedStatement.execute();

        } catch (SQLException e) {
            logger.error(ERROR_MESSAGE, e);
        }
        // TODO do something this mess
        return getUserIdByUsername(userDataSet.getUsername());
//        return -1;
    }

    public long getUserIdByUsername(final String username) {
        logger.info("Getting user ID  by username: {}", username);
        long id = -1L;

        try (PreparedStatement statement = connection.prepareStatement("SELECT id FROM users WHERE username=?")) {

            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                id = resultSet.getLong("id");
            }
        } catch (SQLException e) {
            logger.error(ERROR_MESSAGE, e);
        }

        return id;
    }

    public UserDataSet getUserByUsername(final String username) {
        logger.info("Getting info about user by username: {}", username);

        UserDataSet userDataSet = null;
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "SELECT username, password FROM users WHERE username=?")) {

            preparedStatement.setString(1, username);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {

                String u = resultSet.getString("username");
                String p = resultSet.getString("password");

                userDataSet = new UserDataSet(u, p);
            }
        } catch (SQLException e) {
            logger.error(ERROR_MESSAGE, e);
        }

        return userDataSet;
    }

    public void updateUser(final UserDataSet userDataSet) {
        String username = userDataSet.getUsername();
        String password = userDataSet.getPassword();
        boolean isBlocked = userDataSet.isBlocked();
        boolean isAdmin = userDataSet.isAdmin();

        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "UPDATE users set password=?, is_blocked=?, is_admin=? where username=?"
        )) {
            preparedStatement.setString(1, password);
            preparedStatement.setBoolean(2, isBlocked);
            preparedStatement.setBoolean(3, isAdmin);
            preparedStatement.setString(4, username);

            preparedStatement.execute();

        } catch (SQLException e) {
            logger.error(ERROR_MESSAGE, e);
        }
    }
}
