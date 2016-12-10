package me.bbb1991.service;

import me.bbb1991.dao.BookDAO;
import me.bbb1991.dao.RoleDAO;
import me.bbb1991.dao.UserDAO;
import me.bbb1991.model.Book;
import me.bbb1991.model.Role;
import me.bbb1991.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by bbb1991 on 12/9/16.
 *
 * @author Bagdat Bimaganbetov
 * @author bagdat.bimaganbetov@gmail.com
 */

@Service
public class DBService implements InitializingBean, DisposableBean, UserService, BookService{

    // TODO: 12/9/16 Separate to SecurityService and BookService

    private static final Logger logger = LoggerFactory.getLogger(DBService.class);

    private EntityManagerFactory entityManagerFactory;

    private UserDAO userDAO;

    private BookDAO bookDAO;

    private RoleDAO roleDAO;

    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public void setUserDAO(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Autowired
    public void setBookDAO(BookDAO bookDAO) {
        this.bookDAO = bookDAO;
    }

    @Autowired
    public void setPasswordEncoder(BCryptPasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Autowired
    public void setRoleDAO(RoleDAO roleDAO) {
        this.roleDAO = roleDAO;
    }

    @Override
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

    @Override
    public Book getBookById(Long id) {
        return bookDAO.getById(id);
    }

    @Override
    public Long saveOrUpdateBook(Book book) {
        if (book.getId() == null) {
            return bookDAO.save(book);
        }
        bookDAO.update(book);

        return book.getId();
    }

    @Override
    public void saveOrUpdate(User user) {
        if (user.getId() == null) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            Set<Role> roles = new HashSet<>();
            roles.add(roleDAO.getOne(1L));
            user.setRoles(roles);
            userDAO.save(user);
            return;
        }
        userDAO.update(user);
    }

    @Override
    public User findByUsername(String username) {
        return userDAO.findByUsername(username);
    }

    @Override
    public User getUserById(Long id) {
        return userDAO.getById(id);
    }
}