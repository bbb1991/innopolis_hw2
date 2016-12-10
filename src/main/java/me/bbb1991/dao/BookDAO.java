package me.bbb1991.dao;

import me.bbb1991.model.Book;

import java.util.List;

/**
 * Created by bbb1991 on 12/9/16.
 * DAO для работы с книгой
 *
 * @author Bagdat Bimaganbetov
 * @author bagdat.bimaganbetov@gmail.com
 */
public interface BookDAO {

    /**
     * Сохранение книги в БД
     *
     * @param book книга, которую требуется сохранить
     * @return ID, по которому была сохранена книга
     */
    Long save(Book book);

    /**
     * Обновление существующей книги
     *
     * @param book книга, которую необходимо обновить
     */
    void update(Book book);

    /**
     * Удаление сууществующей книги по ID
     *
     * @param id ID книги, которую необходимо удалить
     */
    void delete(Long id);

    /**
     * Получение книги по ID
     *
     * @param id ID книги по которому ищем
     * @return найденная книга, или <code>null</code>
     */
    Book getById(Long id);

    /**
     * Получение всех книг.
     *
     * @return список всех книг
     */
    List<Book> getAllBooks();
}