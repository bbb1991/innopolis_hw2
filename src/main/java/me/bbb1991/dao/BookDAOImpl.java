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
 * Реализация интерфейса {@link BookDAO }
 *
 * @author Bagdat Bimaganbetov
 * @author bagdat.bimaganbetov@gmail.com
 */
@Repository
public class BookDAOImpl implements BookDAO {

    /**
     * Сервис для работы с БД
     */
    private DBService dbService;

    /**
     * Логгер класса
     */
    private static final Logger logger = LoggerFactory.getLogger(BookDAOImpl.class);

    @Autowired
    public void setDbService(DBService dbService) {
        this.dbService = dbService;
    }

    /**
     * Сохранение книги в БД
     *
     * @param book книга, которую требуется сохранить
     * @return ID, по которому сохранение книги
     */
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

    /**
     * Обновление существующей книги
     *
     * @param book книга, которую необходимо обновить
     */
    @Override
    public void update(Book book) {
        logger.info("Updating book. Info before: {}", book);
        EntityManager entityManager = dbService.getEntityManagerFactory().createEntityManager();

        entityManager.refresh(book);

        entityManager.close();

        logger.info("Book updated!");
    }

    /**
     * Удаление существующей книги
     *
     * @param id ID книги, которую необходимо удалить
     */
    @Override
    public void delete(Long id) {

        logger.info("Deleting book by ID: {}", id);

        EntityManager entityManager = dbService.getEntityManagerFactory().createEntityManager();


        Book book = getById(id);

        entityManager.remove(book);

        entityManager.close();

        logger.info("Book deleted.");
    }

    /**
     * Получение книги по ID
     *
     * @param id ID книги по которому ищем
     * @return книга, которая была найдена, либо <code>null</code>
     */
    @Override
    public Book getById(Long id) {
        logger.info("Getting Book by ID: {}", id);

        EntityManager entityManager = dbService.getEntityManagerFactory().createEntityManager();
        Book book = entityManager.find(Book.class, id);

        entityManager.close();
        logger.info("Got book: {}", book);

        return book;

    }

    /**
     * Получение всех книг
     *
     * @return список всех книг
     */
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