package me.bbb1991.dao;

import me.bbb1991.model.Book;
import me.bbb1991.service.DBService;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import java.util.List;

/**
 * Created by bbb1991 on 12/9/16.
 *
 * @author Bagdat Bimaganbetov
 * @author bagdat.bimaganbetov@gmail.com
 */
@Repository
public class BookDAOImpl implements BookDAO {

    private DBService dbService;

    private static final Logger logger = LoggerFactory.getLogger(BookDAOImpl.class);

    @Autowired
    public void setDbService(DBService dbService) {
        this.dbService = dbService;
    }

    @Override
    public Long save(Book book) {
        logger.info("Saving new book. Book info: {}");

        EntityManager entityManager = dbService.getEntityManagerFactory().createEntityManager();

        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        entityManager.persist(book);

        transaction.commit();

        entityManager.close();

        logger.info("Book saved! ID is: {}", book.getId());

        return book.getId();
    }

    @Override
    public void update(Book book) {
        logger.info("Updating book. Info before: {}", book);
        EntityManager entityManager = dbService.getEntityManagerFactory().createEntityManager();

        entityManager.refresh(book);

        entityManager.close();

        logger.info("Book updated!");
    }

    @Override
    public void delete(Long id) {

        logger.info("Deleting book by ID: {}", id);

        EntityManager entityManager = dbService.getEntityManagerFactory().createEntityManager();


        Book book = getById(id);

        entityManager.remove(book);

        entityManager.close();

        logger.info("Book deleted.");
    }

    @Override
    public Book getById(Long id) {
        logger.info("Getting Book by ID: {}", id);

        EntityManager entityManager = dbService.getEntityManagerFactory().createEntityManager();
        Book book = entityManager.find(Book.class, id);

        entityManager.close();
        logger.info("Got book: {}", book);

        return book;

    }

    @Override
    public List<Book> getAllBooks() {
        logger.info("Getting all books");

        EntityManager entityManager = dbService.getEntityManagerFactory().createEntityManager();
        Query query = entityManager.createQuery("from Book ");

        List<Book> books = query.getResultList();
        entityManager.close();
        logger.info("Got books list. Size is: {}", books.size());
        return books;
    }
}