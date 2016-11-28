package dbService.dao;

import dbService.CustomException;
import dbService.DBService;
import dbService.dataSets.BookDataSet;

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

    public BooksDAO(DBService dbService) {
        super(dbService);
    }

    @Override
    public void update(BookDataSet bookDataSet) throws CustomException {

    }

    @Override
    public void delete(BookDataSet bookDataSet) throws CustomException {

    }

    @Override
    public BookDataSet getById(long id) {
        return null;
    }

    @Override
    public BookDataSet getByName(String name) throws CustomException {
        return null;
    }


    @Override
    public void insert(BookDataSet bookDataSet) throws CustomException {

        Connection connection = dbService.retrieveConnection();

        try (PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO books (content, author_id, title) VALUES (?, ?, ?)");
        Statement statement = connection.createStatement()) {

            // todo change to prepared statment
            statement.executeQuery("SELECT id from users where username='" + bookDataSet.getAuthor() + "'");

            ResultSet rs = statement.getResultSet();
            rs.next();
            long author_id  = rs.getLong(1);
            rs.close();


            preparedStatement.setString(1, bookDataSet.getContent());
            preparedStatement.setLong(2, author_id);
            preparedStatement.setString(3, bookDataSet.getTitle());

            int i = preparedStatement.executeUpdate();
            logger.info("Book inserted. Inserted books: {}", i);
        } catch (SQLException e) {
            logger.error(ERROR_MESSAGE_GENERAL, e);
            throw new CustomException(ERROR_MESSAGE_STATEMENT, e);
        } finally {
            dbService.putBackConnection(connection);
        }
    }

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

    public BookDataSet findBookById(long id) throws CustomException {

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

}
