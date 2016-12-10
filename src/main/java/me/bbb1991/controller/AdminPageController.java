package me.bbb1991.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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

    @RequestMapping(value = "/admin", method = RequestMethod.GET)
    public String getPage(Model model) {

        // TODO: 12/10/16 add logic here

        return "admin";
    }
}