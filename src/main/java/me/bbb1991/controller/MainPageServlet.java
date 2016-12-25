package me.bbb1991.controller;

import me.bbb1991.model.Book;
import me.bbb1991.service.BookService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * Created by bbb1991 on 12/8/16.
 * Класс контроллер для главной страницы
 *
 * @author Bagdat Bimaganbetov
 * @author bagdat.bimaganbetov@gmail.com
 */

@Controller
public class MainPageServlet {

    /**
     * Логгер уровня класса
     */
    private static final Logger logger = LoggerFactory.getLogger(MainPageServlet.class);

    /**
     * Сервис для работы с БД
     */
    private BookService bookService;

    @RequestMapping(value = {"/"}, method = RequestMethod.GET)
    public String getPage(Model model) {

        logger.info("Incoming request for get index page");

        List<Book> books = bookService.getAllBooks();

        model.addAttribute("title", "Главная страница");
        model.addAttribute("books", books);

        return "main_page";
    }

    @RequestMapping(value = "/books", method = RequestMethod.GET)
    public String getBooks(@RequestParam("by_author") String username, Model model) {
        List<Book> books = bookService.getBooksByAuthor(username);

        model.addAttribute("title", "Главная страница");
        model.addAttribute("books", books);

        return "main_page";
    }

    @Autowired
    public void setBookService(BookService bookService) {
        this.bookService = bookService;
    }
}
