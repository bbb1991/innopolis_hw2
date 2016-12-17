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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManagerFactory;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by bbb1991 on 12/9/16.
 * Класс для работы с БД
 *
 * @author Bagdat Bimaganbetov
 * @author bagdat.bimaganbetov@gmail.com
 */

@Service
public class DBService implements UserService, BookService {

    // TODO: 12/9/16 Separate to UserService and BookService

    /**
     * Логгер уровня класса
     */
    private static final Logger logger = LoggerFactory.getLogger(DBService.class);

    /**
     * Подлючение к БД
     */
    private EntityManagerFactory entityManagerFactory;

    /**
     * ДАО для работы с сущностями {@link User}
     */
    private UserDAO userDAO;

    /**
     * ДАО для работы с сущностями {@link Book}
     */
    private BookDAO bookDAO;

    /**
     * ДАО для работы с сущностями {@link Role}
     */
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

    @Autowired
    public void setEntityManagerFactory(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    /**
     * Получение всех книг в системе.
     *
     * @return список всех книг
     */
    @Override
    public List<Book> getAllBooks() {
        return bookDAO.findAll();
    }

    public EntityManagerFactory getEntityManagerFactory() {
        return entityManagerFactory;
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
     * Метод для сохранения нового пользователя или обновления существующего
     *
     * @param user пользователь
     */
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

    /**
     * Поиск пользователя по логину
     *
     * @param username логин предпологаемого пользователя
     * @return Пользователь, нвйденный по логину, либо null
     */
    @Override
    public User findByUsername(String username) {
        return userDAO.findByUsername(username);
    }

    /**
     * Получение пользователя по ID
     *
     * @param id ID пользователя
     * @return Пользователь, найденный по ID, либо <code>null</code>
     */
    @Override
    public User getUserById(Long id) {
        return userDAO.getById(id);
    }

    /**
     * Заблокирование пользователя
     *
     * @param id ID пользователя, которого необходимо заблокировать
     */
    public void blockUserById(Long id) {
        userDAO.blockUser(id);
    }

    /**
     * Получение всех пользователей системы для дальнейшей работы
     *
     * @return список всех пользователей
     */
    public List<User> getAllUsers() {
        return userDAO.getAllUsers();
    }

    /**
     * Разблокирование пользователя
     *
     * @param id ID пользователя, которого необходимо разблокировать
     */
    public void unblockUserById(Long id) {
        userDAO.unblockUser(id);
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
}