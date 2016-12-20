package me.bbb1991.dao;

import me.bbb1991.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by bbb1991 on 12/9/16.
 * DAO для работы с книгой
 *
 * @author Bagdat Bimaganbetov
 * @author bagdat.bimaganbetov@gmail.com
 */
public interface BookDAO extends JpaRepository<Book, Long> {

    /**
     * Получение всех книг автора, исключая книги, которые еще не закончены, то есть помечены как черновик
     *
     * @param username логин автора
     * @return список книг автора
     */
    @Query("SELECT b FROM Book b where b.author.username=:username and b.draft=false")
    List<Book> findByUsername(@Param("username") String username);

}