package me.bbb1991.service;

import me.bbb1991.dao.BookDAO;
import me.bbb1991.dao.UserDAO;
import me.bbb1991.model.Book;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

/**
 * Created by bbb1991 on 12/9/16.
 *
 * @author Bagdat Bimaganbetov
 * @author bagdat.bimaganbetov@gmail.com
 */

@Service
public class DBService implements InitializingBean, DisposableBean {

    private static final Logger logger = LoggerFactory.getLogger(DBService.class);

    private EntityManagerFactory entityManagerFactory;

    private UserDAO userDAO;

    private BookDAO bookDAO;

    @Autowired
    public void setUserDAO(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Autowired
    public void setBookDAO(BookDAO bookDAO) {
        this.bookDAO = bookDAO;
    }


    public List<Book> getAllBooks() {
        return bookDAO.getAllBooks();
    }

    @Override
    public void afterPropertiesSet() {
        entityManagerFactory = Persistence.createEntityManagerFactory( "h2" );
    }

    public EntityManagerFactory getEntityManagerFactory() {
        return entityManagerFactory;
    }

    @Override
    public void destroy() {
        entityManagerFactory.close();
    }

    public Book getBookById(Long id) {
        return bookDAO.getById(id);
    }
}