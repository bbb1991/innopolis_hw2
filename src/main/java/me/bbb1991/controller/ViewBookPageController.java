package me.bbb1991.controller;

import me.bbb1991.helpers.CustomException;
import me.bbb1991.model.Book;
import me.bbb1991.service.DBService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.client.HttpClientErrorException;

/**
 * Created by bbb1991 on 12/9/16.
 * Класс контроллер для просмотра конкретной книги
 *
 * @author Bagdat Bimaganbetov
 * @author bagdat.bimaganbetov@gmail.com
 */
@ResponseStatus(value = HttpStatus.NOT_FOUND)
@Controller
public class ViewBookPageController {

    /**
     * Сервис для работы с БД
     */
    private DBService dbService;

    /**
     * Получение книги по ID
     *
     * @param id    ID книги, которую необходимо получить
     * @param model
     * @return книга, найденная по ID, либо 404
     */
    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    @RequestMapping(value = "/book/{id}", method = RequestMethod.GET)
    public String getBook(@PathVariable("id") Long id, Model model) {
        Book book = dbService.getBookById(id);

        if (book == null) {
            throw new CustomException(HttpStatus.NOT_FOUND, String.format("No book found with ID: %d", id));
        }

        model.addAttribute("title", book.getTitle());
        model.addAttribute("book", book);

        return "book";
    }

    @Autowired
    public void setDbService(DBService dbService) {
        this.dbService = dbService;
    }
}
