package me.bbb1991.service;

import me.bbb1991.dao.RoleDAO;
import me.bbb1991.dao.UserDAO;
import me.bbb1991.helpers.Helper;
import me.bbb1991.model.Role;
import me.bbb1991.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by bbb1991 on 12/26/16.
 *
 * @author Bagdat Bimaganbetov
 * @author bagdat.bimaganbetov@gmail.com
 */
@Service
public class UserServiceImpl implements UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    private UserDAO userDAO;

    private RoleDAO roleDAO;

    private Helper helper;

    /**
     * Метод для сохранения нового пользователя или обновления существующего
     *
     * @param user пользователь
     */
    @Override
    public void saveOrUpdateUser(User user) {
        if (user.getId() == null) {

            user.setPassword(helper.getPasswordHash(user.getPassword()));
            user.setAvatar("resources" + File.separator + "images" + File.separator +"default-avatar.png");

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
     * Разблокирование пользователя
     *
     * @param id ID пользователя, которого необходимо разблокировать
     */
    public void unblockUserById(Long id) {
        userDAO.unblockUser(id);
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
     * Заблокирование пользователя
     *
     * @param id ID пользователя, которого необходимо заблокировать
     */
    public void blockUserById(Long id) {
        userDAO.blockUser(id);
    }

    @Autowired
    public void setUserDAO(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Autowired
    public void setHelper(Helper helper) {
        this.helper = helper;
    }

    @Autowired
    public void setRoleDAO(RoleDAO roleDAO) {
        this.roleDAO = roleDAO;
    }
}
