package me.bbb1991.dao;

import me.bbb1991.model.Book;

import java.util.List;

/**
 * Created by bbb1991 on 12/9/16.
 *
 * @author Bagdat Bimaganbetov
 * @author bagdat.bimaganbetov@gmail.com
 */
public interface BookDAO {

    Long save(Book book);

    void update(Book book);

    void delete(Long id);

    Book getById(Long id);

    List<Book> getAllBooks();
}