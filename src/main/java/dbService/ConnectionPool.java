package dbService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static helpers.Constants.ERROR_MESSAGE_GENERAL;

/**
 * Created by bbb1991 on 11/23/16.
 * Класс, описывающая пул коннектов.
 * @author Bagdat Bimaganbetov
 * @author bagdat.bimaganbetov@gmail.com
 */
public class ConnectionPool {

    /**
     * Логгер для данного класса
     */
    private static final Logger logger = LoggerFactory.getLogger(ConnectionPool.class);

    /**
     * URL для подключения к БД
     */
    private String url;

    /**
     * Логин для подключения к БД
     */
    private String login;

    /**
     * Пароль для подключения к БД
     */
    private String password;

    /**
     * Сколько коннектов должно находиться в пулле
     */
    private int length;

    /**
     * Список с коннектами, которые в данный момент свободны и можно использовать
     */
    private static final List<Connection> availableConnections = new ArrayList<>();

    /**
     * Список с коннектами, которые в данный момент используется
     */
    private static final List<Connection> engagedConnections = new ArrayList<>();

    /**
     * Конструктор для пулла
     * @param url урл для подключения к БД
     * @param login логин для подключения к БД
     * @param password пароль для подключения к БД
     * @param capacity сколько обьектов должно храниться в пулле
     * @throws CustomException при проблеме с созданием коннектов
     */
    public ConnectionPool(String url, String login, String password, int capacity) throws CustomException {
        this.url = url;
        this.login = login;
        this.password = password;
        this.length = capacity;

        for (int i =0; i < capacity; i++) {
            availableConnections.add(getConnection());
        }

        logger.info("Connection pool is ready!");
    }

    /**
     * Получение нового коннекта
     * @return новый коннект к БД
     * @throws CustomException ошибка при попытке подключения к БД
     */
    private Connection getConnection() throws CustomException {
        try {
            return DriverManager.getConnection(url, login, password);
        } catch (SQLException e) {
            logger.error(ERROR_MESSAGE_GENERAL, e);
            throw new CustomException("Can't get new Connection!", e);
        }
    }

    /**
     * Метод для получение коннекта из пула. Если все доступные израхсодованы, возвращаем новый.
     * @return коннект из пулла
     * @throws CustomException
     */
    public synchronized Connection retrieve() throws CustomException {
        Connection newConn = null;
        if (availableConnections.size() == 0) {
            logger.warn("Available connection poo is empty! Creating new connection.");
            newConn = getConnection();
        } else {
            newConn = availableConnections.get(availableConnections.size()-1);
            availableConnections.remove(newConn);
        }
        engagedConnections.add(newConn);
        logger.info("New connection retrieved.");
        return newConn;
    }

    /**
     * Добавление коннекта обратно в пул
     * @param connection использованный коннект к БД
     */
    public synchronized void putBack(Connection connection) {

        if (connection == null) {
            logger.error("Tried put back null connection!");
            return;
        }

        if (engagedConnections.remove(connection)) {
            availableConnections.add(connection);
            return;
        }
        logger.warn("Connection not in the used Connections array");
    }

    public int getAvailableConnectionsCount() {
        return availableConnections.size();
    }

}
