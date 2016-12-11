package me.bbb1991.dao;

import me.bbb1991.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by bbb1991 on 12/9/16.
 * DAO для работы с книгой
 *
 * @author Bagdat Bimaganbetov
 * @author bagdat.bimaganbetov@gmail.com
 */
public interface BookDAO extends JpaRepository<Book, Long> {}