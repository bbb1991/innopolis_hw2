package me.bbb1991.service;

/**
 * Created by bbb1991 on 12/9/16.
 *
 * @author Bagdat Bimaganbetov
 * @author bagdat.bimaganbetov@gmail.com
 */

public interface SecurityService {

    String findLoggedInUsername();

    /**
     * Метод по обработке запроса на вхрод в систему
     * @param username логин, введенный пользователем
     * @param password пароль, введенный пользователем
     */
    void autoLogin(String username, String password);
}
