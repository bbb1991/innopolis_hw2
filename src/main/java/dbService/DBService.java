package dbService;

import dbService.dao.BooksDAO;
import dbService.dao.UsersDAO;
import dbService.dataSets.BookDataSet;
import dbService.dataSets.UserDataSet;
import helpers.PasswordHelper;
import helpers.PropertyReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.util.Collection;
import java.util.List;
import java.util.Properties;

import static helpers.Constants.ERROR_MESSAGE_GENERAL;
import static helpers.Constants.SETTINGS_FILE;

/**
 * Created by bbb1991 on 11/20/16.
 * Класс для работы с подключением к БД и работы с ДАО
 * @author Bagdat Bimaganbetov
 * @author bagdat.bimaganbetov@gmail.com
 */
public class DBService {

    /**
     * Логгер
     */
    private static final Logger logger = LoggerFactory.getLogger(DBService.class);

    /**
     * Инстанс класса
     */
    private volatile static DBService instance;

    /**
     * Монитор
     */
    private static final Object obj = new Object();

    /**
     * ДАО для работы с пользователями
     */
    private static UsersDAO usersDAO;

    /**
     * ДАО для работы с книгами
     */
    private static BooksDAO booksDAO;

    /**
     * Пул коннектов к БД
     */
    private ConnectionPool connectionPool;


    private DBService() throws CustomException {
        try {
            initConnectionPool(); // инициализируем пул коннектов

            // Создаем необходимое ДАО
            usersDAO = new UsersDAO(this);
            booksDAO = new BooksDAO(this);

            // Приводим БД к первоночальное состояние
            warmUp();
        } catch (CustomException e) {
            logger.error(ERROR_MESSAGE_GENERAL, e);
            throw e;
        }
    }

    /**
     * Метод для получение коннекта к БД из пулла
     * @return коннект к БД
     * @throws CustomException проблема при созданий/получения коннекта к БД
     */
    public Connection retrieveConnection() throws CustomException {
        return connectionPool.retrieve();
    }

    /**
     * Метод для добавления использованного подключения к БД
     * @param connection использованное подключение к БД, которую необходимо добавить обратно
     */
    public void putBackConnection(Connection connection) {
        connectionPool.putBack(connection);
    }

    /**
     * Метод, который приводит БД в первоначальное состояние. Сбраываем БД до первоначального состояния для того, чтобы
     * прошлые тесты или изменения не отразилась
     * @throws CustomException проблема при работе с коннектом
     */
    private void warmUp() throws CustomException {

        // дропаем все базы
        usersDAO.dropTable();
        booksDAO.dropTable();

        // создаем их заново
        usersDAO.createTable();
        booksDAO.createTable();

        // заполняем их тестовыми данными
        UserDataSet admin = new UserDataSet("admin", PasswordHelper.getHash("admin"));
        admin.setAdmin(true);
        usersDAO.insert(admin);

        UserDataSet guest = new UserDataSet("guest", PasswordHelper.getHash("guest"));
        usersDAO.insert(guest);

        booksDAO.insert(new BookDataSet("title1", "admin", "Content1"));
        booksDAO.insert(new BookDataSet("title2", "admin", "Content2"));
        booksDAO.insert(new BookDataSet("title3", "admin", "Content3"));
    }

    /**
     * метод для инициализаций пулла потоков к БД
     * @throws CustomException ошибка при созданий подключения к БД
     */
    private void initConnectionPool() throws CustomException {
        Properties properties = PropertyReader.readProperty(SETTINGS_FILE); // считываем настройки из файла

        String url = properties.getProperty("db_url");
        String user = properties.getProperty("db_username");
        String password = properties.getProperty("db_password");
        int capacity = Integer.parseInt(properties.getProperty("db_conn_pool_size"));

        // инициализируем пул коннектов согласно настройке
        connectionPool = new ConnectionPool(url, user, password, capacity);
    }

    public UserDataSet getUser(final String username) throws CustomException {
        return usersDAO.getByName(username);
    }

    /**
     * Получение инстанса
     * @return инстант БД сервиса
     * @throws CustomException ошибка при работа с БД
     */
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

    /**
     * Сохранение новой книги в БД
     * @param userId
     * @param title
     * @param content
     * @param username
     * @return
     * @throws CustomException
     */
    public String insertBook(long userId, String title, String content, String username) throws CustomException {

        BookDataSet bookDataSet = new BookDataSet(title, username, content);

        bookDataSet.setAuthor(username);

        booksDAO.insert(bookDataSet);

        return String.valueOf(1);
    }

    /**
     * Сохранение нового пользователя в БД
     * @param username имя пользователя
     * @param password хэш пароля
     * @return Созданный пользователь
     * @throws CustomException ошибка при работе с БД
     */
    public UserDataSet insertUser(String username, String password) throws CustomException {
        String passwordHash = PasswordHelper.getHash(password);
        UserDataSet userDataSet = new UserDataSet(username, passwordHash);
        usersDAO.insert(userDataSet);
        return userDataSet;
    }

    /**
     * Получение книги по ID
     * @param id ID книги
     * @return Книга, полученная по ID
     * @throws CustomException ошибка при работе с БД
     */
    public BookDataSet findBookById(String id) throws CustomException {
        return booksDAO.getById(Long.parseLong(id));
    }

    /**
     * Получение всех доступных книг
     * @return список с книгами
     * @throws CustomException ошибка при работе с БД
     */
    public List<BookDataSet> getAllBooks() throws CustomException {
        return booksDAO.getAll();
    }

    public List<BookDataSet> getBookByTitle(String title) throws CustomException {
        return booksDAO.getByTitle(title);
    }

    public BookDataSet findBookById(int id) throws CustomException {
        return booksDAO.getById(id);
    }
}