package dbService.dao;

import dbService.CustomException;
import dbService.DBService;
import dbService.dataSets.BookDataSet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static helpers.Constants.ERROR_MESSAGE_GENERAL;
import static helpers.Constants.ERROR_MESSAGE_STATEMENT;

/**
 * Created by bbb1991 on 11/21/16.
 *
 * @author Bagdat Bimaganbetov
 * @author bagdat.bimaganbetov@gmail.com
 */
public class BooksDAO extends AbstractDAO<BookDataSet> {

    /**
     * Логгер
     */
    private static final Logger logger = LoggerFactory.getLogger(BooksDAO.class);

    /**
     * Конструктор
     * @param dbService
     */
    public BooksDAO(DBService dbService) {
        super(dbService);
    }

    /**
     * Обновление существующей книги
     * @param model модель, которую необходимо обновить
     * @throws CustomException
     */
    @Override
    public void update(BookDataSet model) throws CustomException {
        throw new UnsupportedOperationException();
    }

    /**
     * Удаление книги из БД
     * @param model модель, которую необходимо удалить
     * @throws CustomException
     */
    @Override
    public void delete(BookDataSet model) throws CustomException {
        throw new UnsupportedOperationException();
    }

    /**
     * Получение книги по названию
     * @param name имя (название, тема)
     * @return книга полученная по названию или null
     * @throws CustomException
     */
    @Override
    public BookDataSet getByName(String name) throws CustomException {
        throw new UnsupportedOperationException();
    }

    /**
     * Сохранение книги в БД
     * @param model модель, которую нужно вставить в БД
     * @throws CustomException
     */
    @Override
    public void insert(BookDataSet model) throws CustomException {

        Connection connection = dbService.retrieveConnection();

        try (PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO books (content, author_id, title) VALUES (?, ?, ?)");
        Statement statement = connection.createStatement()) {

            // todo change to prepared statment
            statement.executeQuery("SELECT id from users where username='" + model.getAuthor() + "'");

            ResultSet rs = statement.getResultSet();
            rs.next();
            long author_id  = rs.getLong(1);
            rs.close();


            preparedStatement.setString(1, model.getContent());
            preparedStatement.setLong(2, author_id);
            preparedStatement.setString(3, model.getTitle());

            int i = preparedStatement.executeUpdate();
            logger.info("Book inserted. Inserted books: {}", i);
        } catch (SQLException e) {
            logger.error(ERROR_MESSAGE_GENERAL, e);
            throw new CustomException(ERROR_MESSAGE_STATEMENT, e);
        } finally {
            dbService.putBackConnection(connection);
        }
    }

    /**
     * Создаение таблицы в БД
     * @throws CustomException
     */
    @Override
    public void createTable() throws CustomException {
        Connection connection = dbService.retrieveConnection();

        logger.info("Creating table books with sequence.");
        try (Statement statement = connection.createStatement()) {
            statement.execute("CREATE SEQUENCE IF NOT EXISTS book_id_seq");

            statement.execute("CREATE TABLE IF NOT EXISTS books (" +
                    "id INTEGER NOT NULL DEFAULT nextval('book_id_seq'), " +
                    "title VARCHAR(255) NOT NULL , " +
                    "author_id INTEGER NOT NULL," +
                    "content TEXT" +
                    ")");

            statement.execute("ALTER SEQUENCE book_id_seq OWNED BY users.id");
            logger.info("Table in db created!");
        } catch (SQLException e) {
            logger.error(ERROR_MESSAGE_STATEMENT, e);
            throw new CustomException(ERROR_MESSAGE_STATEMENT, e);
        } finally {
            dbService.putBackConnection(connection);
        }
    }

    /**
     * Удаляет всю таблицу целиком.
     *
     * @throws CustomException
     */
    @Override
    public void dropTable() throws CustomException {

        Connection connection = dbService.retrieveConnection();

        logger.info("Trying to drop table books with sequence");
        try (Statement statement = connection.createStatement()) {
            statement.execute("DROP TABLE IF EXISTS books CASCADE");
            statement.execute("DROP SEQUENCE IF EXISTS book_id_seq CASCADE");
            logger.info("Tables dropped!");
        } catch (SQLException e) {
            logger.error(ERROR_MESSAGE_GENERAL, e);
            throw new CustomException(ERROR_MESSAGE_STATEMENT, e);
        } finally {
            dbService.putBackConnection(connection);
        }
    }

    /**
     * Получение всей книги из БД
     * @return список всех доступных книг
     * @throws CustomException
     */
    @Override
    public List<BookDataSet> getAll() throws CustomException {
        Connection connection = dbService.retrieveConnection();
        List<BookDataSet> list = new ArrayList<>();

        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("SELECT b.id, b.title, u.username, b.content FROM books b join users u on b.author_id=u.id");
            while (resultSet.next()) {
                long id = resultSet.getLong(1);
                String title = resultSet.getString(2);
                String author = resultSet.getString(3);
                String content = resultSet.getString(4);

                BookDataSet bookDataSet = new BookDataSet(id, title, author, content);
                list.add(bookDataSet);
            }
        } catch (SQLException e) {
            logger.error(ERROR_MESSAGE_STATEMENT, e);
            throw new CustomException(ERROR_MESSAGE_STATEMENT, e);
        } finally {
            dbService.putBackConnection(connection);
        }

        return list;
    }

    /**
     * Получение книги по ID
     * @param id ID по которому необходимо вытащить модель
     * @return
     * @throws CustomException
     */
    @Override
    public BookDataSet getById(long id) throws CustomException {

        Connection connection = dbService.retrieveConnection();

        BookDataSet bookDataSet = null;
        try (PreparedStatement statement = connection.prepareStatement("SELECT b.title, u.username, b.content FROM books b join users u on b.author_id=u.id WHERE b.id=? LIMIT 1")) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String title = resultSet.getString(1);
                String authorId = resultSet.getString(2);
                String content = resultSet.getString(3);

                bookDataSet = new BookDataSet(id, title, authorId, content);
            }

        } catch (SQLException e) {
            logger.error(ERROR_MESSAGE_GENERAL, e);
            throw new CustomException(ERROR_MESSAGE_STATEMENT, e);
        } finally {
            dbService.putBackConnection(connection);
        }
        return bookDataSet;
    }

    /**
     * Получение списка книг по названию
     * @param query текст, которую должен содержать название
     * @return список книг илюо пустой список
     * @throws CustomException ошиюка при работе с БД
     */
    public List<BookDataSet> getByTitle(String query) throws CustomException {
        Connection connection = dbService.retrieveConnection();

        List<BookDataSet> books = new ArrayList<>();

        try (PreparedStatement statement = connection.prepareStatement("select * from books where title LIKE ?")) {
            statement.setString(1, "%" + query + "%");

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String title = resultSet.getString("title");
                String author = resultSet.getString("author_id");
                Long id = resultSet.getLong("id");
                String content = resultSet.getString("content");

                books.add(new BookDataSet(id, title, author, content));
            }
        } catch (SQLException e) {
            logger.error(ERROR_MESSAGE_STATEMENT, e);
            throw new CustomException(ERROR_MESSAGE_STATEMENT, e);
        }

        return books;

    }
}
