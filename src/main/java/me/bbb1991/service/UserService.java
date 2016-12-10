package me.bbb1991.service;

import me.bbb1991.model.User;

/**
 * Created by bbb1991 on 12/9/16.
 *
 * @author Bagdat Bimaganbetov
 * @author bagdat.bimaganbetov@gmail.com
 */
public interface UserService {

    /**
     * Сохранение или обновление пользователя
     *
     * @param user пользователь
     */
    void saveOrUpdate(User user);

    /**
     * Поиск пользователя по логину
     *
     * @param username логин предпологаемого пользователя
     * @return пользователь, найденному логину, или <code>null</code>
     */
    User findByUsername(String username);

    /**
     * Получение пользователя по ID.
     *
     * @param id ID пользователя
     * @return пользователь, найденный по ID, или <code>null</code>
     */
    User getUserById(Long id);

}
