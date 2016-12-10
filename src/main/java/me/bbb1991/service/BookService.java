package me.bbb1991.service;

import me.bbb1991.model.Book;

import java.util.List;

/**
 * Created by bbb1991 on 12/9/16.
 *
 * @author Bagdat Bimaganbetov
 * @author bagdat.bimaganbetov@gmail.com
 */
public interface BookService {

    Book getBookById(Long id);

    Long saveOrUpdateBook(Book book);

    List<Book> getAllBooks();

}
