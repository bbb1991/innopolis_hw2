package me.bbb1991.service;

import me.bbb1991.dao.BookDAO;
import me.bbb1991.model.Book;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by bbb1991 on 12/26/16.
 *
 * @author Bagdat Bimaganbetov
 * @author bagdat.bimaganbetov@gmail.com
 */
@Service
public class BookServiceImpl implements BookService {

    private BookDAO bookDAO;

    private static final Logger logger = LoggerFactory.getLogger(BookServiceImpl.class);

    /**
     * Получение всех книг в системе.
     *
     * @return список всех книг
     */
    @Override
    public List<Book> getAllBooks() {
        return bookDAO.findAll();
    }

    /**
     * Получение книги по ID.
     *
     * @param id ID книги
     * @return найденная книга, либо <code>null</code>
     */
    @Override
    public Book getBookById(Long id) {
        return bookDAO.findOne(id);
    }

    /**
     * Сохранение новой книги или обновление существующего
     *
     * @param book книга, с которым нужно химмичить
     * @return ID, по которому сохранена данная книга
     */
    @Override
    public Long saveOrUpdateBook(Book book) {

        bookDAO.save(book);

        return book.getId();
    }

    /**
     * Удаление книги по ID
     * @param id ID книги
     */
    @Override
    public void deleteBookById(Long id) {
        bookDAO.delete(id);
    }

    @Override
    public List<Book> getBooksByAuthor(String username) {
        return bookDAO.findByUsername(username);
    }

    @Autowired
    public void setBookDAO(BookDAO bookDAO) {
        this.bookDAO = bookDAO;
    }
}
