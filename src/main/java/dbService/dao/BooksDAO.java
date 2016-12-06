package dbService.dao;

import dbService.CustomException;
import dbService.model.Book;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;
import java.util.List;

/**
 * Created by bbb1991 on 11/21/16.
 *
 * @author Bagdat Bimaganbetov
 * @author bagdat.bimaganbetov@gmail.com
 */
public class BooksDAO extends AbstractDAO<Book> {

    private SessionFactory sessionFactory;

    @Autowired
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    /**
     * Логгер
     */
    private static final Logger logger = LoggerFactory.getLogger(BooksDAO.class);


    @Override
    public void insert(Book model) throws CustomException {
        Session session = sessionFactory.getCurrentSession();
        session.persist(model);
        logger.info("Book added: {}", model);
    }

    @Override
    public void update(Book model) throws CustomException {

        Session session = sessionFactory.getCurrentSession();
        session.update(model);
        logger.info("Book updated: {}", model);
    }

    @Override
    public void delete(Book model) throws CustomException {
        Session session = sessionFactory.getCurrentSession();
        session.delete(model);

        logger.info("Book deleted: {}", model);
    }

    @Override
    public Book getById(long id) throws CustomException {
        Session session = sessionFactory.getCurrentSession();

        Book book = session.load(Book.class, id);

        logger.info("Got book by id: {}", book);

        return book;
    }

    @Override
    public Book getByName(String name) throws CustomException {
        return null; // todo add logic here
    }

    @Override
    public void createTable() throws CustomException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void dropTable() throws CustomException {
        throw new UnsupportedOperationException();
    }

    @Override
    public Collection<Book> getAll() throws CustomException {
        Session session = sessionFactory.getCurrentSession();
        List<Book> books = session.createQuery("select b from dbService.model.Book b").list();

        logger.info("Got books: {}", books.size());
        return books;
    }

    public List<Book> getBooksByName(String name) throws CustomException {
        return null; // todo add logic here
    }



}
