package dbService;

import dbService.dao.BooksDAO;
import dbService.dao.UsersDAO;
import dbService.model.Book;
import dbService.model.User;
import helpers.PasswordHelper;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by bbb1991 on 11/20/16.
 * Класс для работы с подключением к БД и работы с ДАО
 *
 * @author Bagdat Bimaganbetov
 * @author bagdat.bimaganbetov@gmail.com
 */
public class DBService {

    private BooksDAO booksDAO;
    private UsersDAO usersDAO;

    @Transactional
    public User getUser(final String username) throws CustomException {
        return usersDAO.getByName(username);
    }

    /**
     * Сохранение новой книги в БД
     *
     * @param userId
     * @param title
     * @param content
     * @param username
     * @return
     * @throws CustomException
     */
    @Transactional
    public String insertBook(long userId, String title, String content, String username) throws CustomException {

        Book book = new Book(title, userId, content);

        booksDAO.insert(book);

        return String.valueOf(1);
    }

    /**
     * Сохранение нового пользователя в БД
     *
     * @param username имя пользователя
     * @param password хэш пароля
     * @return Созданный пользователь
     * @throws CustomException ошибка при работе с БД
     */
    @Transactional
    public User insertUser(String username, String password) throws CustomException {
        String passwordHash = PasswordHelper.getHash(password);
        User user = new User(username, passwordHash);
        usersDAO.insert(user);
        return user;
    }

    /**
     * Получение книги по ID
     *
     * @param id ID книги
     * @return Книга, полученная по ID
     * @throws CustomException ошибка при работе с БД
     */
    @Transactional
    public Book findBookById(String id) throws CustomException {
        return booksDAO.getById(Long.parseLong(id));
    }

    /**
     * Получение всех доступных книг
     *
     * @return список с книгами
     * @throws CustomException ошибка при работе с БД
     */
    @Transactional
    public List<Book> getAllBooks() throws CustomException {
        return (List<Book>) booksDAO.getAll();
    }

    @Transactional
    public List<Book> getBookByTitle(String title) throws CustomException {
        return booksDAO.getBooksByName(title);
    }

    @Transactional
    public Book findBookById(int id) throws CustomException {
        return booksDAO.getById(id);
    }


    public void setBooksDAO(BooksDAO booksDAO) {
        this.booksDAO = booksDAO;
    }

    public BooksDAO getBooksDAO() {
        return booksDAO;
    }

    public void setUsersDAO(UsersDAO usersDAO) {
        this.usersDAO = usersDAO;
    }

    public UsersDAO getUsersDAO() {
        return usersDAO;
    }
}