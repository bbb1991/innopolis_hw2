package dbService.dao;

import dbService.CustomException;
import dbService.DBService;
import dbService.dataSets.UserDataSet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.Collection;

import static helpers.Constants.ERROR_MESSAGE_GENERAL;
import static helpers.Constants.ERROR_MESSAGE_STATEMENT;

/**
 * Created by bbb1991 on 11/20/16.
 *
 * @author Bagdat Bimaganbetov
 * @author bagdat.bimaganbetov@gmail.com
 */
public class UsersDAO extends AbstractDAO<UserDataSet> {

    public UsersDAO(final DBService dbService) {
        super(dbService);
    }

    public UserDataSet getUserById(final long id) throws CustomException {
        UserDataSet userDataSet = null;
        Connection connection;

        try {
            connection = dbService.retrieveConnection();
        } catch (Exception e) {
            logger.error(ERROR_MESSAGE_GENERAL, e);
            throw e;
        }

        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "SELECT username, password FROM users WHERE id=?")) {
//            String.format("select * from test where id=%d", id)

            preparedStatement.setLong(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                String user = resultSet.getString("username");
                String password = resultSet.getString("password");

                userDataSet = new UserDataSet(id, user, password);
            }

        } catch (SQLException e) {
            logger.error(ERROR_MESSAGE_GENERAL, e);
        } finally {
            dbService.putBackConnection(connection);
        }
        return userDataSet;
    }

    @Override
    public void createTable() throws CustomException {
        logger.info("Creating tables users with sequences.");

        Connection connection;

        try {
            connection = dbService.retrieveConnection();
        } catch (CustomException e) {
            logger.error(ERROR_MESSAGE_GENERAL, e);
            throw e;
        }

        try (Statement statement = connection.createStatement()) {
            statement.execute("CREATE SEQUENCE IF NOT EXISTS user_id_seq");

            statement.execute("CREATE TABLE IF NOT EXISTS users (" +
                    "id INTEGER NOT NULL DEFAULT nextval('user_id_seq'), " +
                    "username VARCHAR(20) NOT NULL UNIQUE , " +
                    "password VARCHAR(50) NOT NULL," +
                    "is_blocked BOOLEAN DEFAULT FALSE," +
                    "is_admin BOOLEAN DEFAULT TRUE " +
                    ")");

            statement.execute("ALTER SEQUENCE user_id_seq OWNED BY users.id");
            logger.info("Table in db created!");
        } catch (SQLException e) {
            logger.error(ERROR_MESSAGE_STATEMENT, e);
            throw new CustomException(ERROR_MESSAGE_STATEMENT, e);
        } finally {
            dbService.putBackConnection(connection);
        }
    }

    @Override
    public void dropTable() throws CustomException {
        logger.info("Trying to drop table users");

        Connection connection;

        try {
            connection = dbService.retrieveConnection();
        } catch (CustomException e) {
            logger.error(ERROR_MESSAGE_GENERAL, e);
            throw e;
        }

        try (Statement statement = connection.createStatement()) {
            statement.execute("DROP TABLE IF EXISTS users CASCADE");
            statement.execute("DROP SEQUENCE IF EXISTS id_seq CASCADE");
            logger.info("Tables dropped!");
        } catch (SQLException e) {
            logger.error(ERROR_MESSAGE_STATEMENT, e);
            throw new CustomException(ERROR_MESSAGE_STATEMENT, e);
        } finally {
            dbService.putBackConnection(connection);
        }
    }

    @Override
    public <X extends Collection<UserDataSet>> X getAll() throws CustomException {
        return null;
    }

    // todo usernames may duplicates.
    @Override
    public void insert(final UserDataSet userDataSet) throws CustomException {
        logger.info("Inserting new user: {}", userDataSet);

        Connection connection = dbService.retrieveConnection();

        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "INSERT INTO users (username, password, is_admin, is_blocked) VALUES (?, ?, ?, ?)")) {

            preparedStatement.setString(1, userDataSet.getUsername());
            preparedStatement.setString(2, userDataSet.getPassword());
            preparedStatement.setBoolean(3, userDataSet.isAdmin());
            preparedStatement.setBoolean(4, userDataSet.isBlocked());

            preparedStatement.execute();

        } catch (SQLException e) {
            logger.error(ERROR_MESSAGE_STATEMENT, e);
            throw new CustomException(ERROR_MESSAGE_STATEMENT, e);
        } finally {
            dbService.putBackConnection(connection);
        }
    }


    @Override
    public UserDataSet getById(long id) {
        return null;
    }

    public long getUserIdByUsername(final String username) throws CustomException {
        logger.info("Getting user ID  by username: {}", username);
        long id = -1L;

        Connection connection = dbService.retrieveConnection();

        try (PreparedStatement statement = connection.prepareStatement("SELECT id FROM users WHERE username=?")) {

            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                id = resultSet.getLong("id");
            }
        } catch (SQLException e) {
            logger.error(ERROR_MESSAGE_GENERAL, e);
            throw new CustomException(ERROR_MESSAGE_STATEMENT, e);
        } finally {
            dbService.putBackConnection(connection);
        }
        return id;
    }

    @Override
    public UserDataSet getByName(final String username) throws CustomException {
        logger.info("Getting info about user by username: {}", username);

        Connection connection = dbService.retrieveConnection();

        UserDataSet userDataSet = null;
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "SELECT id, username, password, is_admin, is_blocked FROM users WHERE username=?")) {

            preparedStatement.setString(1, username);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {

                String u = resultSet.getString("username");
                String p = resultSet.getString("password");
                boolean isAdmin = resultSet.getBoolean("is_admin");
                boolean isBlocked = resultSet.getBoolean("is_blocked");
                long id = resultSet.getLong("id");

                userDataSet = new UserDataSet(u, p);
                userDataSet.setAdmin(isAdmin);
                userDataSet.setBlocked(isBlocked);
                userDataSet.setId(id);
            }
        } catch (SQLException e) {
            logger.error(ERROR_MESSAGE_GENERAL, e);
            throw new CustomException(ERROR_MESSAGE_STATEMENT, e);
        } finally {
            dbService.putBackConnection(connection);
        }
        return userDataSet;
    }

    @Override
    public void update(final UserDataSet userDataSet) throws CustomException {

        Connection connection = dbService.retrieveConnection();

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
            logger.error(ERROR_MESSAGE_GENERAL, e);
            throw new CustomException(ERROR_MESSAGE_STATEMENT, e);
        } finally {
            dbService.putBackConnection(connection);
        }
    }

    @Override
    public void delete(final UserDataSet userDataSet) throws CustomException {

        Connection connection = dbService.retrieveConnection();

        String username = userDataSet.getUsername();

        try (PreparedStatement statement = connection.prepareStatement("delete from users where username=?")) {
            statement.setString(1, username);
            statement.execute();
        } catch (SQLException e) {
            logger.error(ERROR_MESSAGE_GENERAL, e);
            throw new CustomException(ERROR_MESSAGE_STATEMENT, e);
        } finally {
            dbService.putBackConnection(connection);
        }
    }
}
