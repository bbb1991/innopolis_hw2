package me.bbb1991.controller;

import me.bbb1991.model.User;
import me.bbb1991.service.DBService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by bbb1991 on 12/8/16.
 * класс контроллер для страницы администратора
 *
 * @author Bagdat Bimaganbetov
 * @author bagdat.bimaganbetov@gmail.com
 */

@Controller
public class AdminPageController {

    /**
     * Логгер
     */
    private static final Logger logger = LoggerFactory.getLogger(AdminPageController.class);

    private DBService dbService;

    @RequestMapping(value = "/admin", method = RequestMethod.GET)
    public String getPage(Model model) {

        logger.info("Getting admin page");

        // TODO: 12/10/16 add logic here

        List<User> users = dbService.getAllUsers();

        users.sort(Comparator.comparing(User::getId));

        model.addAttribute("title", "Admin page");
        model.addAttribute("users", users);

        return "admin";
    }

    /**
     * Блокирование пользователя по ID
     *
     * @param id ID пользователя
     * @return редирект на страницу админа
     */
    @RequestMapping(value = "/admin/block/{id}")
    public String blockUser(@PathVariable("id") Long id) {

        logger.info("Blocking user by ID: {}", id);

        dbService.blockUserById(id);

        return "redirect:/admin";
    }

    /**
     * Разблокирование пользователя по ID
     *
     * @param id ID пользователя
     * @return редирект на страницу админа
     */
    @RequestMapping(value = "/admin/unblock/{id}")
    public String unblock(@PathVariable("id") Long id) {

        logger.info("Unblocking user by ID: {}", id);

        dbService.unblockUserById(id);

        return "redirect:/admin";
    }

    @Autowired
    public void setDbService(DBService dbService) {
        this.dbService = dbService;
    }
}