package me.bbb1991.dao;

import me.bbb1991.model.User;

import java.util.List;

/**
 * Created by bbb1991 on 12/9/16.
 * DAO для работы с пользователями
 *
 * @author Bagdat Bimaganbetov
 * @author bagdat.bimaganbetov@gmail.com
 */
public interface UserDAO {

    /**
     * Сохранение нового пользователя в БД
     *
     * @param user пользователь, которую необходимо сохранить
     * @return ID, по которому сохранен пользователь
     */
    Long save(User user);

    /**
     * Обновление существующего пользователя
     *
     * @param user пользователь, которую необходимо сохранить
     */
    void update(User user);

    /**
     * Удаление пользователя по ID
     *
     * @param id ID книги, которую необходимо удалить
     */
    void delete(Long id);

    /**
     * Получение книги по ID.
     *
     * @param id ID книги, которую необходимо получить
     * @return книга, найденная по ID, или <code>null</code>
     */
    User getById(Long id);

    /**
     * Получение всех пользователей
     *
     * @return список всех пользователей
     */
    List<User> getAllUsers();

    /**
     * Поиск пользователей по имени пользователей
     *
     * @param username имя пользователей, по которому ведется поиск
     * @return найденный пользователь, либо <code>null</code>
     */
    User findByUsername(String username);
}