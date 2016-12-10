package me.bbb1991.dao;

import me.bbb1991.model.User;

import java.util.List;

/**
 * Created by bbb1991 on 12/9/16.
 *
 * @author Bagdat Bimaganbetov
 * @author bagdat.bimaganbetov@gmail.com
 */
public interface UserDAO {

    Long save(User user);

    void update(User user);

    void delete(Long id);

    User getById(Long id);

    List<User> getAllUsers();

    User findByUsername(String username);
}