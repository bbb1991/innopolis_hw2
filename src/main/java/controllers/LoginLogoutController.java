package controllers;

import dbService.CustomException;
import dbService.DBService;
import dbService.model.User;
import helpers.PasswordHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

/**
 * Created by bbb1991 on 11/30/16.
 * Контроллер для работы со страницой логина и выхода из системы
 *
 * @author Bagdat Bimaganbetov
 * @author bagdat.bimaganbetov@gmail.com
 */
@Controller
public class LoginLogoutController {

    private DBService dbService;

    @Autowired
    public void setDbService(DBService dbService) {
        this.dbService = dbService;
    }

    private static final Logger logger = LoggerFactory.getLogger(LoginLogoutController.class);

    /**
     * Получение страницы логина
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String getLoginPage(Model model) {
        model.addAttribute("title", "Login page");
        return "login";
    }


    /**
     * Принятие и работа с запросом на вход в систему
     *
     * @param username логин пользователя
     * @param password пароль пользователя
     * @param request
     * @return
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String doLogin(@RequestParam("username") String username,
                          @RequestParam("password") String password,
                          HttpServletRequest request) {

        logger.info("Trying to log in. Username is: {}", username);

        User user;
        try {
            user = dbService.getUser(username); // пробуем найти пользователя по логину
        } catch (CustomException e) {
            logger.error("Error occurred while getting user by username", e);
            throw new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }

        if (user == null) { // если нет такого пользвателя в БД
            logger.warn("Username not found in DB! Tried {}", username);
            throw new HttpClientErrorException(HttpStatus.UNAUTHORIZED, "Username not found!");
        }

        String passwordHash;
        try {
            passwordHash = PasswordHelper.getHash(password); // получаем хэш от пароля
        } catch (CustomException e) {
            logger.error("Error occurred while generating hash from password!", e);
            throw new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }

        // сверяем хэши паролей
        if (!Objects.equals(passwordHash, user.getPasswordHash())) { // если не совпало
            logger.warn("Password is incorrect!");
            throw new HttpClientErrorException(HttpStatus.UNAUTHORIZED, "Username/password incorrect!");
        }

        request.getSession().setAttribute("username", username);

        logger.info("Success! User {} logged in.", username);

        return "redirect:/";

    }


    /**
     * Получение и работа с запросом на выход из системы
     * @param request
     * @return
     */
    @RequestMapping("/logout")
    public String logout(HttpServletRequest request) {

        String username = (String) request.getSession().getAttribute("username");

        logger.info("User {} logged out", username);

        request.getSession().invalidate();

        return "redirect:/";

    }

}
