package me.bbb1991.service;

import me.bbb1991.model.User;

/**
 * Created by bbb1991 on 12/9/16.
 *
 * @author Bagdat Bimaganbetov
 * @author bagdat.bimaganbetov@gmail.com
 */
public interface UserService {

    void saveOrUpdate(User user);

    User findByUsername(String username);

    User getUserById(Long id);

}
