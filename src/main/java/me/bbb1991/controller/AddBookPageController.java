package me.bbb1991.controller;

import me.bbb1991.model.Book;
import me.bbb1991.service.CustomUserDetail;
import me.bbb1991.service.DBService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Date;

/**
 * Created by bbb1991 on 12/9/16.
 * Класс контроллер, для страницы добавления книги в БД.
 * @author Bagdat Bimaganbetov
 * @author bagdat.bimaganbetov@gmail.com
 */

@Controller
public class AddBookPageController {

    /**
     * Логгер для класса
     */
    private static final Logger logger = LoggerFactory.getLogger(AddBookPageController.class);

    /**
     * Сервис по работе с БД
     */
    private DBService dbService;

    /**
     * Метод для получения страницы с формой
     * @param model
     * @return имя файла
     */
    @RequestMapping(value = "/add_book", method = RequestMethod.GET)
    public String getPage(Model model) {

        logger.info("Getting ADD_BOOK page by GET request.");

        // Добавляем данные для JSTL
        model.addAttribute("title", "Add book");
        model.addAttribute("book", new Book());

        return "add_book";
    }

    /**
     * Метод для обработки POST запросов по добавлению книги
     */
    @RequestMapping(value = "/add_book", method = RequestMethod.POST)
    public String addBook(@ModelAttribute("book") Book book, BindingResult bindingResult) {

        logger.info("Incoming POST request for adding book: {}", book);

        // TODO: 12/9/16 add validation logic here

        // получаем параметры безорасности
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // берем информацию о залогиненном пользователе
        CustomUserDetail customUser = (CustomUserDetail) authentication.getPrincipal();

        // Сохраняем информацию об авторе и дате
        book.setAuthor(customUser.getUser());
        book.setDate(new Date());

        Long bookId = dbService.saveOrUpdateBook(book);

        // перенаправляем
        return String.format("redirect:/book/%d", bookId);
    }


    @RequestMapping("/edit_book/{id}")
    public String editBook(@PathVariable("id") Long id, Model model) {

        logger.info("Incoming request for edit book by ID: {}", id);

        Book book = dbService.getBookById(id);

        model.addAttribute("title", "Редактирование: " + book.getTitle());
        model.addAttribute("book", book);

        return "add_book";
    }

    @Autowired
    public void setDbService(DBService dbService) {
        this.dbService = dbService;
    }
}
