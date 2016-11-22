package dbService.dao;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import dbService.dataSets.BookDataSet;
import helpers.PropertyReader;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Pattern;

import static helpers.Constants.ERROR_MESSAGE;
import static helpers.Constants.SETTINGS_FILE;

/**
 * Created by bbb1991 on 11/21/16.
 *
 * @author Bagdat Bimaganbetov
 * @author bagdat.bimaganbetov@gmail.com
 */
public class BooksDAO {

    private static Logger logger = LoggerFactory.getLogger(BooksDAO.class);

    private MongoClient client = null;
    private MongoDatabase db;
    private MongoCollection<Document> collection;
    private Connection connection;

    public BooksDAO(Connection connection) {
        this.connection = connection;
        setUp();
    }

    private void setUp() {
        Properties properties = PropertyReader.readProperty(SETTINGS_FILE);

        String host = properties.getProperty("mongo_host");
        int port = Integer.parseInt(properties.getProperty("mongo_port"));
        String mongo_db = properties.getProperty("mongo_db");
        String mongoCollection = properties.getProperty("mongo_collection");

        client = new MongoClient(host, port);
        db = client.getDatabase(mongo_db);

        collection = db.getCollection(mongoCollection);
    }

    public String addBook(BookDataSet bookDataSet, String username) {
        Document document = new Document("content", bookDataSet.getContent());
        document.put("author", username);
        System.out.println(document);

        collection.insertOne(document);
        ObjectId id =  (ObjectId) document.get("_id");
        try (PreparedStatement statement = connection.prepareStatement("insert into books (mongo_id, author_id, title) values (?, ?, ?)")) {
            statement.setString(1, id.toString());
            statement.setLong(2, bookDataSet.getAuthorId());
            statement.setString(3, bookDataSet.getTitle());

            int i = statement.executeUpdate();
            logger.info("Book inserted. Inserted books: {}", i);
        } catch (SQLException e) {
            logger.error(ERROR_MESSAGE, e);
        }

        return id.toString();
    }

    public List<Document> searchBook(Map<String, String> map) {
        logger.info("Searching book. Query is: {}", map.keySet());
        Document query = new Document();
        List<Document> result = new ArrayList<>();

        for (Map.Entry<String, String> entry : map.entrySet()) {
            query.put(entry.getKey(), Pattern.compile(entry.getValue()));
        }

        for (Document document : collection.find(query)) {
            result.add(document);
        }

        logger.info("Search completed. Found document count: {}", result.size());
        return result;

    }

    public void dropCollection() {
        logger.error("Alert! Dropping collection!");
//        collection.drop();
    }

    public void createTable() throws SQLException {
        logger.info("Creating table books with sequence.");
        try (Statement statement = connection.createStatement()) {
            statement.execute("CREATE SEQUENCE IF NOT EXISTS book_id_seq");

            statement.execute("CREATE TABLE IF NOT EXISTS books (" +
                    "id INTEGER NOT NULL DEFAULT nextval('book_id_seq'), " +
                    "title VARCHAR(255) NOT NULL , " +
                    "author_id VARCHAR(60) NOT NULL," +
                    "mongo_id VARCHAR(24)" +
                    ")");

            statement.execute("ALTER SEQUENCE book_id_seq OWNED BY users.id");
            logger.info("Table in db created!");
        }
    }

    /**
     * Удаляет всю таблицу целиком.
     * @throws SQLException
     */
    public void dropTable() throws SQLException {
        logger.info("Trying to drop table books with sequence");
        try (Statement statement = connection.createStatement()) {
            statement.execute("DROP TABLE IF EXISTS books CASCADE");
            statement.execute("DROP SEQUENCE IF EXISTS book_id_seq CASCADE" );
            logger.info("Tables dropped!");
        }
        dropCollection();
    }

    public BookDataSet findBookById(final String mongoId) {

        BookDataSet bookDataSet = null;

        try (PreparedStatement statement = connection.prepareStatement("select * from books where mongo_id=? limit 1")) {
            statement.setString(1, mongoId);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String title = resultSet.getString("title");
                long authorId = resultSet.getLong("author_id");
                long id = resultSet.getLong("id");

                bookDataSet = new BookDataSet(id, title, authorId, null);
            }

        } catch (SQLException e) {
            logger.error(ERROR_MESSAGE, e);
        }

        if (bookDataSet == null) {
            return null;
        }

        Document query = new Document();
        query.put("_id", new ObjectId(mongoId));
        Document dbObj = collection.find(query).first();

        String content = (String) dbObj.get("content");
        String authorName = (String) dbObj.get("author");

        bookDataSet.setContent(content);
        bookDataSet.setAuthor(authorName);

        return bookDataSet;
    }

}
