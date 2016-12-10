package me.bbb1991.controller;

import me.bbb1991.model.Book;
import me.bbb1991.model.User;
import me.bbb1991.service.CustomUserDetail;
import me.bbb1991.service.DBService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Date;

/**
 * Created by bbb1991 on 12/9/16.
 *
 * @author Bagdat Bimaganbetov
 * @author bagdat.bimaganbetov@gmail.com
 */

@Controller
public class AddBookPageController {

    private DBService dbService;

    @RequestMapping(value = "/add_book", method = RequestMethod.GET)
    public String getPage(Model model) {

        model.addAttribute("title", "Add book");
        model.addAttribute("book", new Book());

        // TODO: 12/9/16 add edit book logic

        return "add_book";
    }

    @RequestMapping(value = "/add_book", method = RequestMethod.POST)
    public String addBook(@ModelAttribute("book") Book book, BindingResult bindingResult) {

        // TODO: 12/9/16 add validation logic here

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        CustomUserDetail customUser = (CustomUserDetail) authentication.getPrincipal();
        Long userId = customUser.getId();
        User user = dbService.getUserById(userId);

        book.setAuthor(user);
        book.setDate(new Date());

        Long bookId = dbService.saveOrUpdateBook(book);

        return String.format("redirect:/book/%d", bookId);
    }

    @Autowired
    public void setDbService(DBService dbService) {
        this.dbService = dbService;
    }
}
