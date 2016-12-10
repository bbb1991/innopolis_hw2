package me.bbb1991.service;

import me.bbb1991.model.Book;

import java.util.List;

/**
 * Created by bbb1991 on 12/9/16.
 * Сервис для работы с моделью книги
 *
 * @author Bagdat Bimaganbetov
 * @author bagdat.bimaganbetov@gmail.com
 */
public interface BookService {

    /**
     * Получение книги по ID
     *
     * @param id ID книги по ID
     * @return книга, найденный по ID, либо <code>null</code>
     */
    Book getBookById(Long id);

    /**
     * Сохранение или обновление книги
     *
     * @param book книга, с которым нужно химмичить
     * @return ID по книге
     */
    Long saveOrUpdateBook(Book book);

    /**
     * Получение всех книг в БД
     *
     * @return список всех книг
     */
    List<Book> getAllBooks();

}
