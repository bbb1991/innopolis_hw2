package dbService.dao;

import dbService.CustomException;
import dbService.DBService;
import dbService.dataSets.UserDataSet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.List;

import static helpers.Constants.ERROR_MESSAGE_GENERAL;
import static helpers.Constants.ERROR_MESSAGE_STATEMENT;

/**
 * Created by bbb1991 on 11/20/16.
 *
 * @author Bagdat Bimaganbetov
 * @author bagdat.bimaganbetov@gmail.com
 */
public class UsersDAO extends AbstractDAO<UserDataSet> {

    /**
     * Логгер
     */
    private static final Logger logger = LoggerFactory.getLogger(UsersDAO.class);

    /**
     * Конструктор
     * @param dbService
     */
    public UsersDAO(final DBService dbService) {
        super(dbService);
    }

    /**
     * Получение пользователя по ID
     * @param id ID пользователя
     * @return модель пользователя
     * @throws CustomException
     */
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

    /**
     * Создание таблицы в БД
     * @throws CustomException
     */
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

    /**
     * Удаление таблицы в БД
     * @throws CustomException
     */
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

    /**
     * Получение всех пользователей из БД
     * @return список всех пользователей
     * @throws CustomException
     */
    @Override
    public List<UserDataSet> getAll() throws CustomException {
        throw new UnsupportedOperationException();
    }

    /**
     * Сохранение пользователя в БД
     * @param model модель, которую нужно вставить в БД
     * @throws CustomException
     */
    @Override
    public void insert(final UserDataSet model) throws CustomException {
        logger.info("Inserting new user: {}", model);

        Connection connection = dbService.retrieveConnection();

        try (PreparedStatement addUserStatement = connection.prepareStatement(
                "INSERT INTO users (username, password, is_admin, is_blocked) VALUES (?, ?, ?, ?)");
        PreparedStatement checkUsernameStatement = connection.prepareStatement("select username from users where username=?")
        ) {

            checkUsernameStatement.setString(1, model.getUsername());
            ResultSet resultSet = checkUsernameStatement.executeQuery();

            while (resultSet.next()) {
                String username = resultSet.getString("username");
                if (model.getUsername().equalsIgnoreCase(username)) { // if username already in system
                    throw new CustomException("User already in system!");
                }
            }

            addUserStatement.setString(1, model.getUsername());
            addUserStatement.setString(2, model.getPasswordHash());
            addUserStatement.setBoolean(3, model.isAdmin());
            addUserStatement.setBoolean(4, model.isBlocked());

            addUserStatement.execute();

        } catch (SQLException e) {
            logger.error(ERROR_MESSAGE_STATEMENT, e);
            throw new CustomException(ERROR_MESSAGE_STATEMENT, e);
        } finally {
            dbService.putBackConnection(connection);
        }
    }

    /**
     * Получение пользователя по ID
     * @param id ID по которому необходимо вытащить модель
     * @return пользовтаель
     */
    @Override
    public UserDataSet getById(long id) {
        throw new UnsupportedOperationException();
    }

    /**
     * Получение ID пользователя по логину
     * @param username логин пользователя
     * @return ID пользователя из БД или -1 если такой логин не найден в системе
     * @throws CustomException
     */
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

    /**
     * Получение пользователя из БД по логину
     * @param username логин пользователя
     * @return пользователь или null если логин не существует в системе
     * @throws CustomException
     */
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

    /**
     * Обновление существующего пользователя
     * @param model модель, которую необходимо обновить
     * @throws CustomException
     */
    @Override
    public void update(final UserDataSet model) throws CustomException {

        Connection connection = dbService.retrieveConnection();

        String username = model.getUsername();
        String password = model.getPasswordHash();
        boolean isBlocked = model.isBlocked();
        boolean isAdmin = model.isAdmin();

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

    /**
     * Удаление пользователя из системы
     * @param model модель, которую необходимо удалить
     * @throws CustomException
     */
    @Override
    public void delete(final UserDataSet model) throws CustomException {

        Connection connection = dbService.retrieveConnection();

        String username = model.getUsername();

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
